package pers.lwb.controller.admin;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import pers.lwb.constant.MessageConstant;
import pers.lwb.dto.DishDTO;
import pers.lwb.dto.DishPageDTO;
import pers.lwb.result.Result;
import pers.lwb.service.DishService;
import pers.lwb.vo.DishVO;
import pers.lwb.vo.PageVO;

import java.util.List;

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

    @Operation(summary = "分页查询")
    @GetMapping("/page")
    public Result<PageVO<DishVO>> page(@ParameterObject DishPageDTO dishPageDTO) {
        log.info("分页查询：{}", dishPageDTO);
        PageVO<DishVO> pageVO = dishService.page(dishPageDTO);
        return Result.success(pageVO);
    }

    @Operation(summary = "批量删除菜品")
    @DeleteMapping
    public Result<String> delete(@RequestParam List<Long> ids) {
        log.info("批量删除菜品：{}", ids);
        dishService.delete(ids);
        return Result.success(MessageConstant.DISH_DELETE_SUCCESS);
    }

    @Operation(summary = "根据 id 查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("根据 id 查询菜品：{}", id);
        DishVO dishVO = dishService.getVOById(id);
        return Result.success(dishVO);
    }

    @Operation(summary = "修改菜品信息")
    @PutMapping
    public Result<String> update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品信息：{}", dishDTO);
        dishService.update(dishDTO);
        return Result.success(MessageConstant.DISH_UPDATE_SUCCESS);
    }
}
