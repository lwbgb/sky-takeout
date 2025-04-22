package pers.lwb.service;

import pers.lwb.dto.CategoryDTO;
import pers.lwb.dto.CategoryPageDTO;
import pers.lwb.entity.Category;
import pers.lwb.vo.PageVO;

import java.util.List;

public interface CategoryService {

    void insert(CategoryDTO categoryDTO);

    PageVO<Category> page(CategoryPageDTO categoryPageDTO);

    void deleteById(Long id);

    void update(CategoryDTO categoryDTO);

    void setStatus(Long id, Integer status);

    List<Category> list(Integer type);
}
