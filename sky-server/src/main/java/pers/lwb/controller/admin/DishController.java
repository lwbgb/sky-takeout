package pers.lwb.controller.admin;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import pers.lwb.constant.MessageConstant;
import pers.lwb.dto.DishDTO;
import pers.lwb.dto.DishPageDTO;
import pers.lwb.entity.Dish;
import pers.lwb.result.Result;
import pers.lwb.service.DishService;
import pers.lwb.vo.DishVO;
import pers.lwb.vo.PageVO;

import java.util.List;
import java.util.Set;

@Slf4j
@Tag(name = "Admin DishController")
@RestController("adminDishController")
@RequestMapping("/admin/dish")
public class DishController {

    private final DishService dishService;

    private final RedisTemplate<String, Object> redisTemplate;

    public DishController(DishService dishService, RedisTemplate<String, Object> redisTemplate) {
        this.dishService = dishService;
        this.redisTemplate = redisTemplate;
    }

    @Operation(summary = "新增菜品")
    @PostMapping
    public Result<String> insert(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.insert(dishDTO);
        // 删除新增菜品的缓存数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanRedisCache(key);
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
        // 清理所有菜品数据缓存
        cleanRedisCache("dish_*");
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
        // 清理所有菜品数据缓存
        cleanRedisCache("dish_*");
        return Result.success(MessageConstant.DISH_UPDATE_SUCCESS);
    }

    @Operation(summary = "启用/禁用菜品")
    @PostMapping("/status/{status}")
    public Result<String> setStatus(Long id, @PathVariable Integer status) {
        log.info("启用/禁用菜品，id：{}，status：{}", id, status);
        dishService.setStatus(id, status);
        // 清理所有菜品数据缓存
        cleanRedisCache("dish_*");
        return Result.success(MessageConstant.DISH_SET_STATUS_SUCCESS);
    }

    @Operation(summary = "根据分类 id 查询菜品")
    @GetMapping("/list")
    public Result<List<Dish>> list(Long categoryId) {
        log.info("根据分类 id 查询菜品：{}", categoryId);
        List<Dish> dishes = dishService.getByCategoryId(categoryId);
        return Result.success(dishes);
    }

    private void cleanRedisCache(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
