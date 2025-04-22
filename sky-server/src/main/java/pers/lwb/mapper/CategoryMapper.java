package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.dto.CategoryPageDTO;
import pers.lwb.entity.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {

    int insert(Category category);

    List<Category> page(CategoryPageDTO categoryPageDTO);

    int deleteById(Long id);

    int update(Category category);

    List<Category> list(Integer type);
}
