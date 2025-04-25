package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealMapper {

    Long countByCategoryId(Long id);

}
