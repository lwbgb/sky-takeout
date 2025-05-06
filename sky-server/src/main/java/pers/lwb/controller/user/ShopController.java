package pers.lwb.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import pers.lwb.constant.MessageConstant;
import pers.lwb.constant.RedisKeyConstant;
import pers.lwb.exception.RedisOperationException;
import pers.lwb.result.Result;

@Tag(name = "User ShopController")
@Slf4j
@RestController("userShopController")
@RequestMapping("/user/shop")
public class ShopController {

    private final StringRedisTemplate redisTemplate;

    public ShopController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Operation(summary = "获取店铺营业状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        log.info("正在获取店铺营业状态...");
        String status = null;
        try {
            status = redisTemplate.opsForValue().get(RedisKeyConstant.SHOP_STATUS);
        } catch (Exception e) {
            throw new RedisOperationException(MessageConstant.SHOP_GET_STATUS_ERROR);
        }
        if (status != null)
            return Result.success(Integer.parseInt(status));
        return Result.error(MessageConstant.SHOP_GET_STATUS_ERROR);
    }
}
