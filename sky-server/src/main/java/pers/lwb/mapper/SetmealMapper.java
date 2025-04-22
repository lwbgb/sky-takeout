package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealMapper {

    Integer countByCategoryId(Long id);

}
