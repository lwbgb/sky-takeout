package pers.lwb.service;

import pers.lwb.dto.SetmealDTO;
import pers.lwb.dto.SetmealPageDTO;
import pers.lwb.entity.Setmeal;
import pers.lwb.vo.Dish4SetmealVO;
import pers.lwb.vo.PageVO;
import pers.lwb.vo.SetmealVO;

import java.util.List;

public interface SetmealService {

    List<Setmeal> list(Long categoryId);

    List<Dish4SetmealVO> getDishById(Long id);

    void insert(SetmealDTO setmealDTO);

    SetmealVO getById(Long id);

    PageVO<SetmealVO> page(SetmealPageDTO setmealPageDTO);

    void delete(List<Long> ids);

    void update(SetmealDTO setmealDTO);

    void setStatus(Long id, Integer status);
}
