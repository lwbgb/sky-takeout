package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    Long countByDishIds(List<Long> dishIds);
}
