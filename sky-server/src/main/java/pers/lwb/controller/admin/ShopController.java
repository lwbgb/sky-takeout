package pers.lwb.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import pers.lwb.constant.MessageConstant;
import pers.lwb.constant.RedisKeyConstant;
import pers.lwb.exception.RedisOperationException;
import pers.lwb.result.Result;

import java.util.Objects;

@Tag(name = "Admin ShopController")
@Slf4j
@RestController("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController {

    private final StringRedisTemplate redisTemplate;

    public ShopController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Operation(summary = "设置店铺营业状态")
    @PutMapping("/{status}")
    public Result<String> setStatus(@PathVariable Integer status) {
        log.info("设置店铺营业状态：{}", status == 1 ? "营业" : "歇业");
        try {
            redisTemplate.opsForValue().set(RedisKeyConstant.SHOP_STATUS, status.toString());
        } catch (Exception e) {
            throw new RedisOperationException(MessageConstant.SHOP_SET_STATUS_ERROR);
        }
        return Result.success(MessageConstant.SHOP_SET_STATUS_SUCCESS);
    }

    @Operation(summary = "获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        String status = null;
        try {
            status = redisTemplate.opsForValue().get(RedisKeyConstant.SHOP_STATUS);
        } catch (Exception e) {
            throw new RedisOperationException(MessageConstant.SHOP_GET_STATUS_ERROR);
        }
        log.info("获取店铺营业状态：{}", Objects.equals(status, "1") ? "营业" : "歇业");
        if (status != null)
            return Result.success(Integer.parseInt(status));
        return Result.error(MessageConstant.SHOP_GET_STATUS_ERROR);
    }
}
