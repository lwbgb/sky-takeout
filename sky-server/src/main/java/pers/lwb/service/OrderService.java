package pers.lwb.service;

import pers.lwb.dto.OrderSubmitDTO;
import pers.lwb.vo.OrderSubmitVO;

public interface OrderService {

    OrderSubmitVO submit(OrderSubmitDTO orderSubmitDTO);
}
