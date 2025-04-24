package pers.lwb.controller.admin;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.lwb.constant.MessageConstant;
import pers.lwb.dto.DishDTO;
import pers.lwb.result.Result;
import pers.lwb.service.DishService;

@Slf4j
@Tag(name = "Admin DishController")
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @Operation(summary = "新增菜品")
    @PostMapping
    public Result<String> insert(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.insert(dishDTO);
        return Result.success(MessageConstant.DISH_INSERT_SUCCESS);
    }
}
