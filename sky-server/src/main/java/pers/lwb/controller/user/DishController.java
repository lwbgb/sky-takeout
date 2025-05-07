package pers.lwb.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.lwb.result.Result;
import pers.lwb.service.DishService;
import pers.lwb.vo.DishVO;

import java.util.List;

@Slf4j
@Tag(name = "User DishController")
@RestController("userDishController")
@RequestMapping("/user/dish")
public class DishController {

    private final DishService dishService;

    private final RedisTemplate<String, Object> redisTemplate;

    public DishController(DishService dishService, StringRedisTemplate stringRedisTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.dishService = dishService;
        this.redisTemplate = redisTemplate;
    }

    @Operation(summary = "根据分类 id 查询菜品")
    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        log.info("根据分类 id 查询菜品：{}", categoryId);

        // 1. 查询 Redis 中是否含有缓存信息
        String key = "dish_" + categoryId;
        List<DishVO> list= (List<DishVO>) redisTemplate.opsForValue().get(key);
        // 2. 有缓存则直接返回查询结果
        if (list != null && !list.isEmpty()) {
            return Result.success(list);
        }
        // 3. 没有缓存信息则查询数据库
        list = dishService.list(categoryId);
        redisTemplate.opsForValue().set(key, list);
        return Result.success(list);
    }


}
