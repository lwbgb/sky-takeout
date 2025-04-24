package pers.lwb.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.lwb.constant.MessageConstant;
import pers.lwb.dto.DishDTO;
import pers.lwb.entity.Dish;
import pers.lwb.entity.DishFlavor;
import pers.lwb.exception.BaseException;
import pers.lwb.exception.InsertException;
import pers.lwb.mapper.DishFlavorMapper;
import pers.lwb.mapper.DishMapper;
import pers.lwb.service.DishService;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;

    private final DishFlavorMapper dishFlavorMapper;

    public DishServiceImpl(DishMapper dishMapper, DishFlavorMapper dishFlavorMapper) {
        this.dishMapper = dishMapper;
        this.dishFlavorMapper = dishFlavorMapper;
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
}
