package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.entity.DishFlavor;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    int insertBatch(List<DishFlavor> flavors);

    int delete(List<Long> dishIds);

    int update(DishFlavor flavor);

    List<DishFlavor> getByDishId(Long dishId);
}
