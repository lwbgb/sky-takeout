package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.entity.SetmealDish;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    Long countByDishIds(List<Long> dishIds);

    int insertBatch(List<SetmealDish> setmealDishes);

    List<SetmealDish> getBySetmealId(Long setmealId);

    int delete(List<Long> setmealIds);
}
