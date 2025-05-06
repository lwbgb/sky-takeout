package pers.lwb.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.lwb.constant.MessageConstant;
import pers.lwb.constant.StatusConstant;
import pers.lwb.dto.SetmealDTO;
import pers.lwb.dto.SetmealPageDTO;
import pers.lwb.entity.Setmeal;
import pers.lwb.entity.SetmealDish;
import pers.lwb.exception.*;
import pers.lwb.mapper.SetmealDishMapper;
import pers.lwb.mapper.SetmealMapper;
import pers.lwb.service.SetmealService;
import pers.lwb.vo.Dish4SetmealVO;
import pers.lwb.vo.PageVO;
import pers.lwb.vo.SetmealVO;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    private final SetmealMapper setmealMapper;

    private final SetmealDishMapper setmealDishMapper;

    public SetmealServiceImpl(SetmealMapper setmealMapper, SetmealDishMapper setmealDishMapper) {
        this.setmealMapper = setmealMapper;
        this.setmealDishMapper = setmealDishMapper;
    }

    @Override
    public List<Setmeal> list(Long categoryId) {
        Setmeal setmeal = Setmeal.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        List<Setmeal> setmeals = setmealMapper.list(setmeal);
        return setmeals;
    }

    @Override
    public List<Dish4SetmealVO> getDishById(Long id) {
        List<Dish4SetmealVO> dishes = setmealMapper.getDishById(id);
        return dishes;
    }

    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void insert(SetmealDTO setmealDTO) {
        Setmeal setmeal = Setmeal.builder()
                .status(StatusConstant.DISABLE)
                .build();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        // 1. 插入套餐数据
        int n = setmealMapper.insert(setmeal);
        if (n <= 0)
            throw new InsertException(MessageConstant.SETMEAL_INSERT_ERROR);

        // 2. 插入关联表数据
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(dish -> {
            dish.setSetmealId(setmeal.getId());
        });

        n = setmealDishMapper.insertBatch(setmealDishes);
        if (n <= 0)
            throw new InsertException(MessageConstant.SETMEAL_DISH_INSERT_ERROR);
    }

    @Override
    public SetmealVO getById(Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    @Override
    public PageVO<SetmealVO> page(SetmealPageDTO setmealPageDTO) {
        PageHelper.startPage(setmealPageDTO.getPage(), setmealPageDTO.getPageSize());
        List<SetmealVO> setmealVOs = setmealMapper.page(setmealPageDTO);
        Page<SetmealVO> page = (Page<SetmealVO>) setmealVOs;
        return new PageVO<>(page.getTotal(), page.getResult());
    }

    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void delete(List<Long> ids) {
        // 1. 启售中的套餐不能删除
        ids.forEach(id -> {
            Setmeal setmeal = setmealMapper.getById(id);
            if (setmeal.getStatus() == 1)
                throw new DeleteNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
        });

        // 2. 删除套餐和菜品的关联信息
        int n = setmealDishMapper.delete(ids);
        if (n <= 0)
            throw new DeleteErrorException(MessageConstant.SETMEAL_DISH_DELETE_ERROR);

        // 3. 删除套餐
        n = setmealMapper.delete(ids);
        if (n <= 0)
            throw new DeleteErrorException(MessageConstant.SETMEAL_DELETE_ERROR);
    }

    @Override
    @Transactional(rollbackFor = BaseException.class)
    public void update(SetmealDTO setmealDTO) {
        // 1. 删除套餐菜品关联信息
        setmealDishMapper.delete(List.of(setmealDTO.getId()));

        // 2. 添加新的关联信息
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealDTO.getId()));
        setmealDishMapper.insertBatch(setmealDishes);

        // 3. 更新套餐信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        int n = setmealMapper.update(setmeal);
        if (n <= 0)
            throw new UpdateException(MessageConstant.SETMEAL_UPDATE_ERROR);
    }

    @Override
    public void setStatus(Long id, Integer status) {
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        int n = setmealMapper.update(setmeal);
        if (n <= 0)
            throw new SetStatusErrorException(MessageConstant.SETMEAL_SET_STATUS_ERROR);
    }
}
