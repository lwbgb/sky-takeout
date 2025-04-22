package pers.lwb.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pers.lwb.constant.MessageConstant;
import pers.lwb.constant.StatusConstant;
import pers.lwb.context.LocalContext;
import pers.lwb.dto.CategoryDTO;
import pers.lwb.dto.CategoryPageDTO;
import pers.lwb.entity.Category;
import pers.lwb.exception.*;
import pers.lwb.mapper.CategoryMapper;
import pers.lwb.mapper.DishMapper;
import pers.lwb.mapper.SetmealMapper;
import pers.lwb.service.CategoryService;
import pers.lwb.vo.PageVO;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final DishMapper dishMapper;

    private final SetmealMapper setmealMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper, DishMapper dishMapper, SetmealMapper setmealMapper) {
        this.categoryMapper = categoryMapper;
        this.dishMapper = dishMapper;
        this.setmealMapper = setmealMapper;
    }

    /**
     * 新增分类
     *
     * @param categoryDTO 分类信息
     */
    public void insert(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        // 分类状态默认为禁用状态 0
        category.setStatus(StatusConstant.DISABLE);

        // 补充信息
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(LocalContext.getCurrentId());
        category.setUpdateUser(LocalContext.getCurrentId());

        int n = categoryMapper.insert(category);
        if (n <= 0)
            throw new InsertException(MessageConstant.CATEGORY_INSERT_ERROR);
    }

    /**
     * 分页查询
     *
     * @param categoryPageDTO 分页信息
     * @return 分页结果
     */
    public PageVO<Category> page(CategoryPageDTO categoryPageDTO) {
        PageHelper.startPage(categoryPageDTO.getPage(), categoryPageDTO.getPageSize());

        // 实现分页查询
        List<Category> categories = categoryMapper.page(categoryPageDTO);
        Page<Category> page = (Page<Category>) categories;

        return new PageVO<>(page.getTotal(), page.getResult());
    }

    /**
     * 根据 id 删除分类
     *
     * @param id 分类 id
     */
    public void deleteById(Long id) {
        // Category 作为菜品和套餐的关联表，删除分类前先要检索是否有关联数据，如果有则拒绝删除
        Integer dishNum = dishMapper.countByCategoryId(id);
        Integer setMealNum = setmealMapper.countByCategoryId(id);

        if (dishNum > 0)
            throw new DeleteNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);

        if (setMealNum > 0)
            throw new DeleteNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);

        int n = categoryMapper.deleteById(id);
        if (n <= 0)
            throw new DeleteErrorException(MessageConstant.CATEGORY_DELETE_ERROR);
    }

    /**
     * 修改分类
     *
     * @param categoryDTO 分类信息
     */
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        // 更新修改信息
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(LocalContext.getCurrentId());

        int n = categoryMapper.update(category);
        if (n <= 0)
            throw new UpdateException(MessageConstant.CATEGORY_UPDATE_ERROR);
    }

    /**
     * 启用/禁用分类
     *
     * @param id 分类 id
     * @param status 分类状态
     */
    public void setStatus(Long id, Integer status) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(LocalContext.getCurrentId())
                .build();

        int n = categoryMapper.update(category);
        if (n <= 0)
            throw new SetStatusErrorException(MessageConstant.CATEGORY_SET_STATUS_ERROR);
    }

    /**
     * 根据类型查询分类
     *
     * @param type 分类类型
     * @return 查询结果
     */
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
