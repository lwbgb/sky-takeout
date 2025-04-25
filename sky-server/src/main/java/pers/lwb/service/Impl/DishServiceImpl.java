package pers.lwb.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.lwb.constant.MessageConstant;
import pers.lwb.dto.DishDTO;
import pers.lwb.dto.DishPageDTO;
import pers.lwb.entity.Dish;
import pers.lwb.entity.DishFlavor;
import pers.lwb.exception.*;
import pers.lwb.mapper.DishFlavorMapper;
import pers.lwb.mapper.DishMapper;
import pers.lwb.mapper.SetmealDishMapper;
import pers.lwb.service.DishService;
import pers.lwb.vo.DishVO;
import pers.lwb.vo.PageVO;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;

    private final DishFlavorMapper dishFlavorMapper;

    private final SetmealDishMapper setmealDishMapper;

    public DishServiceImpl(DishMapper dishMapper, DishFlavorMapper dishFlavorMapper, SetmealDishMapper setmealDishMapper) {
        this.dishMapper = dishMapper;
        this.dishFlavorMapper = dishFlavorMapper;
        this.setmealDishMapper = setmealDishMapper;
    }

    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void insert(DishDTO dishDTO) {
        // 插入菜品信息
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dish.setStatus(0);
        int n = dishMapper.insert(dish);
        if (n <= 0)
            throw new InsertException(MessageConstant.DISH_INSERT_ERROR);

        // 插入口味信息
        List<DishFlavor> flavors = dishDTO.getFlavors();
        // 没有设置口味直接退出
        if (flavors.isEmpty())
            return;
        Long dishId = dish.getId();
        flavors.forEach(flavor -> flavor.setDishId(dishId));
        n = dishFlavorMapper.insertBatch(flavors);
        if (n <= 0)
            throw new InsertException(MessageConstant.FLAVOR_INSERT_ERROR);
    }

    @Override
    public PageVO<DishVO> page(DishPageDTO dishPageDTO) {
        PageHelper.startPage(dishPageDTO.getPage(), dishPageDTO.getPageSize());
        List<DishVO> dishes = dishMapper.list(dishPageDTO);
        Page<DishVO> page = (Page<DishVO>) dishes;
        return new PageVO<>(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void delete(List<Long> ids) {
        // 1. 启售中的菜品不能删除
        ids.forEach(id -> {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == 1)
                throw new DeleteNotAllowedException(MessageConstant.DISH_ON_SALE);
        });

        // 2. 与套餐关联的菜品不能删除
        Long count = setmealDishMapper.countByDishIds(ids);
        if (count > 0)
            throw new DeleteNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);

        // 3. 删除菜品关联的口味
        dishFlavorMapper.delete(ids);
        log.info(MessageConstant.FLAVOR_DELETE_SUCCESS);

        // 4. 删除菜品
        int n = dishMapper.delete(ids);
        if (n <= 0)
            throw new DeleteException(MessageConstant.DISH_DELETE_ERROR);
    }

    @Override
    public DishVO getVOById(Long id) {
        Dish dish = dishMapper.getById(id);
        if (dish == null)
            throw new DishNotFoundException(MessageConstant.DISH_NOT_FOUND);

        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);

        List<DishFlavor> flavors = dishFlavorMapper.getByDishId(id);
        dishVO.setFlavors(flavors);

        return dishVO;
    }


    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // 1. 修改口味
        List<DishFlavor> newFlavors = dishDTO.getFlavors();
        //  (1)删除所有口味
        dishFlavorMapper.delete(List.of(dishDTO.getId()));
        //  (2)插入新的口味列表
        if (!newFlavors.isEmpty()) {
            newFlavors.forEach(flavor -> flavor.setDishId(dishDTO.getId()));
            dishFlavorMapper.insertBatch(newFlavors);
        }
        log.info(MessageConstant.FLAVOR_UPDATE_SUCCESS);

        // 2. 修改菜品
        int n = dishMapper.update(dish);
        if (n <= 0)
            throw new UpdateException(MessageConstant.DISH_UPDATE_ERROR);
    }
}








