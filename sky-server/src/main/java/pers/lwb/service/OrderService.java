package pers.lwb.service;

import pers.lwb.dto.OrderSubmitDTO;
import pers.lwb.dto.OrdersPaymentDTO;
import pers.lwb.vo.OrderPaymentVO;
import pers.lwb.vo.OrderSubmitVO;

public interface OrderService {

    OrderSubmitVO submit(OrderSubmitDTO orderSubmitDTO);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);
}
