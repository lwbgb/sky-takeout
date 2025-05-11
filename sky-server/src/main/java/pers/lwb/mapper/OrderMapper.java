package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.entity.Orders;

@Mapper
public interface OrderMapper {

    int insert(Orders orders);

    Orders getByNumber(String orderNumber);

    int update(Orders orders);
}
