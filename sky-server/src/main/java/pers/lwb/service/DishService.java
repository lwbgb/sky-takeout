package pers.lwb.service;

import pers.lwb.dto.DishDTO;
import pers.lwb.dto.DishPageDTO;
import pers.lwb.entity.Dish;
import pers.lwb.vo.DishVO;
import pers.lwb.vo.PageVO;

import java.util.List;

public interface DishService {

    void insert(DishDTO dishDTO);

    PageVO<DishVO> page(DishPageDTO dishPageDTO);

    void delete(List<Long> ids);

    void update(DishDTO dishDTO);

    DishVO getVOById(Long id);

    void setStatus(Long id, Integer status);

    List<DishVO> list(Long categoryId);

    List<Dish> getByCategoryId(Long categoryId);
}
