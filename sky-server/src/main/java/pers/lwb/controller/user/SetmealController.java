package pers.lwb.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.lwb.entity.Setmeal;
import pers.lwb.result.Result;
import pers.lwb.service.SetmealService;
import pers.lwb.vo.Dish4SetmealVO;

import java.util.List;

@Slf4j
@SuppressWarnings({"all"})
@Tag(name = "User SetmealController")
@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
public class SetmealController {

    private final SetmealService setmealService;

    public SetmealController(SetmealService setmealService) {
        this.setmealService = setmealService;
    }

    @Operation(summary = "根据分类 id 查询套餐")
    @Cacheable(cacheNames = "setmealCache", key = "#categoryId")
    @GetMapping("/list")
    public Result<List<Setmeal>> list(Long categoryId) {
        List<Setmeal> setmeals = setmealService.list(categoryId);
        return Result.success(setmeals);
    }

    @Operation(summary = "根据套餐 id 查询包含的菜品")
    @GetMapping("/dish/{id}")
    public Result<List<Dish4SetmealVO>> getDishById(@PathVariable Long id) {
        log.info("根据套餐 id 查询包含的菜品：{}", id);
        List<Dish4SetmealVO> dishes = setmealService.getDishById(id);
        return Result.success(dishes);
    }
}
