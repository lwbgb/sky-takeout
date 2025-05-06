package pers.lwb.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.lwb.result.Result;
import pers.lwb.service.DishService;
import pers.lwb.vo.DishVO;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @Operation(summary = "根据分类 id 查询菜品")
    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        List<DishVO> list = dishService.list(categoryId);
        return Result.success(list);
    }
}
