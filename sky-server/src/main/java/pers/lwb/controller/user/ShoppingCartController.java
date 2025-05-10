package pers.lwb.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pers.lwb.constant.MessageConstant;
import pers.lwb.dto.ShoppingCartDTO;
import pers.lwb.entity.ShoppingCart;
import pers.lwb.result.Result;
import pers.lwb.service.ShoppingCartService;

import java.util.List;

@Slf4j
@Tag(name = "User ShoppingCartController")
@RestController
@RequestMapping("/user/shoppingCart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Operation(summary = "添加购物车")
    @PostMapping("/add")
    public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加商品到购物车：{}", shoppingCartDTO);
        shoppingCartService.add(shoppingCartDTO);
        return Result.success(MessageConstant.SHOPPING_CART_INSERT_SUCCESS);
    }

    @Operation(summary = "查询购物车")
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list() {
        log.info("查询购物车...");
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);
    }

    @Operation(summary = "从购物车移除一个商品")
    @PostMapping("/sub")
    public Result<String> delete(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("从购物车移除一个商品：{}", shoppingCartDTO);
        shoppingCartService.delete(shoppingCartDTO);
        return Result.success(MessageConstant.SHOPPING_CART_DELETE_SUCCESS);
    }

    @Operation(summary = "清空购物车")
    @DeleteMapping("/clean")
    public Result<String> clean() {
        log.info("清空购物车...");
        shoppingCartService.clean();
        return Result.success(MessageConstant.SHOPPING_CART_CLEAN_SUCCESS);
    }
}
