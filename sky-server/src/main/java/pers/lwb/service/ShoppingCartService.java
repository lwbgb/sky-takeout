package pers.lwb.service;

import pers.lwb.dto.ShoppingCartDTO;
import pers.lwb.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    void add(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> list();

    void delete(ShoppingCartDTO shoppingCartDTO);

    void clean();
}
