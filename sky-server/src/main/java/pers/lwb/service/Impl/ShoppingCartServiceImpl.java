package pers.lwb.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import pers.lwb.constant.MessageConstant;
import pers.lwb.context.LocalContext;
import pers.lwb.dto.ShoppingCartDTO;
import pers.lwb.entity.Dish;
import pers.lwb.entity.Setmeal;
import pers.lwb.entity.ShoppingCart;
import pers.lwb.exception.DeleteErrorException;
import pers.lwb.exception.InsertException;
import pers.lwb.mapper.DishFlavorMapper;
import pers.lwb.mapper.DishMapper;
import pers.lwb.mapper.SetmealMapper;
import pers.lwb.mapper.ShoppingCartMapper;
import pers.lwb.service.ShoppingCartService;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartMapper shoppingCartMapper;

    private final DishMapper dishMapper;

    private final SetmealMapper setmealMapper;

    public ShoppingCartServiceImpl(ShoppingCartMapper shoppingCartMapper, DishMapper dishMapper, DishFlavorMapper dishFlavorMapper, SetmealMapper setmealMapper) {
        this.shoppingCartMapper = shoppingCartMapper;
        this.dishMapper = dishMapper;
        this.setmealMapper = setmealMapper;
    }

    @Override
    public void add(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(LocalContext.getCurrentId());

        // 查询是否已添加过该套餐或菜品
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        // 添加过则数量 + 1，否则新增商品
        if (!list.isEmpty()) {
            ShoppingCart cart = list.get(0);
            shoppingCart.setNumber(cart.getNumber() + 1);
            shoppingCart.setId(cart.getId());
            shoppingCartMapper.update(shoppingCart);
        } else {
            Long dishId = shoppingCart.getDishId();
            Long seatmealId = shoppingCart.getSetmealId();
            // 判断新增的是菜品还是套餐
            if (dishId != null) {
                Dish dish = dishMapper.getById(dishId);
                BeanUtils.copyProperties(dish, shoppingCart);
                shoppingCart.setAmount(dish.getPrice());
            } else if (seatmealId != null) {
                Setmeal setmeal = setmealMapper.getById(seatmealId);
                BeanUtils.copyProperties(setmeal, shoppingCart);
                shoppingCart.setAmount(setmeal.getPrice());
            } else {
                throw new InsertException(MessageConstant.SHOPPING_CART_INSERT_ERROR);
            }
            shoppingCart.setNumber(1);
            int n = shoppingCartMapper.insert(shoppingCart);
            if (n <= 0) {
                throw new InsertException(MessageConstant.SHOPPING_CART_INSERT_ERROR);
            }
        }
    }

    @Override
    public List<ShoppingCart> list() {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(LocalContext.getCurrentId())
                .build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    @Override
    public void delete(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(LocalContext.getCurrentId())
                .build();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);

        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list.isEmpty()) {
            throw new DeleteErrorException(MessageConstant.SHOPPING_CART_DELETE_ERROR);
        } else {
            ShoppingCart cart = list.get(0);
            int n = 0;
            // 1.商品仅剩 1 个，则直接删除商品
            if (cart.getNumber() == 1) {
                n = shoppingCartMapper.delete(cart);
                // 2.商品有多个，则商品的数量 -1
            } else if (cart.getNumber() > 1) {
                cart.setNumber(cart.getNumber() - 1);
                n = shoppingCartMapper.update(cart);
            }
            if (n <= 0) {
                throw new DeleteErrorException(MessageConstant.SHOPPING_CART_DELETE_ERROR);
            }
        }
    }

    @Override
    public void clean() {
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(LocalContext.getCurrentId())
                .build();
        int n = shoppingCartMapper.delete(shoppingCart);
        if (n <= 0) {
            throw new DeleteErrorException(MessageConstant.SHOPPING_CART_CLEAN_ERROR);
        }
    }
}
