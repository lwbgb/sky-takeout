package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.annotation.AutoFill;
import pers.lwb.dto.DishPageDTO;
import pers.lwb.entity.Dish;
import pers.lwb.enumeration.OperationType;
import pers.lwb.vo.DishVO;

import java.util.List;

@Mapper
public interface DishMapper {

    @AutoFill(OperationType.INSERT)
    int insert(Dish dish);

    Integer countByCategoryId(Long categoryId);

    List<DishVO> list(DishPageDTO dishPageDTO);
}
