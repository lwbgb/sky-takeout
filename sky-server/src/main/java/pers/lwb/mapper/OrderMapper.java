package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.entity.Orders;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    int insert(Orders orders);

    Orders getByNumber(String orderNumber);

    int update(Orders orders);

    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime orderTime);
}
