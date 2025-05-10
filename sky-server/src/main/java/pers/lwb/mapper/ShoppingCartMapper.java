package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.annotation.AutoFill;
import pers.lwb.entity.ShoppingCart;
import pers.lwb.enumeration.OperationType;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    @AutoFill(OperationType.INSERT)
    int insert(ShoppingCart shoppingCart);

    List<ShoppingCart> list(ShoppingCart shoppingCart);

    int update(ShoppingCart shoppingCart);

    int delete(ShoppingCart shoppingCart);
}
