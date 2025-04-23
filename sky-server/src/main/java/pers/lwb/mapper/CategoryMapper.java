package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.annotation.AutoFill;
import pers.lwb.dto.CategoryPageDTO;
import pers.lwb.entity.Category;
import pers.lwb.enumeration.OperationType;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @AutoFill(OperationType.INSERT)
    int insert(Category category);

    List<Category> page(CategoryPageDTO categoryPageDTO);

    int deleteById(Long id);

    @AutoFill(OperationType.UPDATE)
    int update(Category category);

    List<Category> list(Integer type);
}
