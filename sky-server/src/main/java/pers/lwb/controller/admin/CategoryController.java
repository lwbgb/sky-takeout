package pers.lwb.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import pers.lwb.constant.MessageConstant;
import pers.lwb.dto.CategoryDTO;
import pers.lwb.dto.CategoryPageDTO;
import pers.lwb.entity.Category;
import pers.lwb.result.Result;
import pers.lwb.service.CategoryService;
import pers.lwb.vo.PageVO;

import java.util.List;

@Tag(name = "CategoryController")
@Slf4j
@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "新增分类")
    @PostMapping
    public Result<String> insert(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        categoryService.insert(categoryDTO);
        return Result.success(MessageConstant.CATEGORY_INSERT_SUCCESS);
    }

    @Operation(summary = "分类分页查询")
    @GetMapping("/page")
    public Result<PageVO<Category>> page(@ParameterObject CategoryPageDTO categoryPageDTO) {
        log.info("分页查询：{}", categoryPageDTO);
        PageVO<Category> pageResult = categoryService.page(categoryPageDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @Operation(summary = "删除分类")
    public Result<String> deleteById(Long id) {
        log.info("删除分类：{}", id);
        categoryService.deleteById(id);
        return Result.success(MessageConstant.CATEGORY_DELETE_SUCCESS);
    }

    @PutMapping
    @Operation(summary = "编辑分类")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return Result.success(MessageConstant.CATEGORY_UPDATE_SUCCESS);
    }

    @PostMapping("/status/{status}")
    @Operation(summary = "启用/禁用分类")
    public Result<String> setStatus(Long id, @PathVariable("status") Integer status) {
        categoryService.setStatus(id, status);
        return Result.success(MessageConstant.CATEGORY_SET_STATUS_SUCCESS);
    }

    @GetMapping("/list")
    @Operation(summary = "根据类型查询分类")
    public Result<List<Category>> list(Integer type) {
        List<Category> categories = categoryService.list(type);
        return Result.success(categories);
    }
}
