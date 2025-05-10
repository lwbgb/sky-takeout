package pers.lwb.mapper;

import org.apache.ibatis.annotations.Mapper;
import pers.lwb.entity.OrderDetail;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    int insertBatch(List<OrderDetail> orderDetails);
}
