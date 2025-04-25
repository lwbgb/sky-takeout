package pers.lwb.service;

import pers.lwb.dto.DishDTO;
import pers.lwb.dto.DishPageDTO;
import pers.lwb.vo.DishVO;
import pers.lwb.vo.PageVO;

import java.util.List;

public interface DishService {

    void insert(DishDTO dishDTO);

    PageVO<DishVO> page(DishPageDTO dishPageDTO);

    void delete(List<Long> ids);
}
