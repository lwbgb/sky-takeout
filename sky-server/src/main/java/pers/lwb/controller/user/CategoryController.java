package pers.lwb.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.lwb.entity.Category;
import pers.lwb.result.Result;
import pers.lwb.service.CategoryService;

import java.util.List;

@Tag(name = "User CategoryController")
@RestController("userCategoryController")
@RequestMapping("/user/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "分类查询")
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type) {
        List<Category> categories = categoryService.list(type);
        return Result.success(categories);
    }
}
