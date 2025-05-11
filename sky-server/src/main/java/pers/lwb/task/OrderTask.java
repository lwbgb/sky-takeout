package pers.lwb.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.lwb.constant.MessageConstant;
import pers.lwb.entity.Orders;
import pers.lwb.mapper.OrderMapper;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class OrderTask {

    private final OrderMapper orderMapper;

    public OrderTask(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Scheduled(cron = "0 * * * * *")
    public void processTimeoutOrder() {
        log.info("定时处理超时订单：{}", LocalDateTime.now());
        List<Orders> orders = orderMapper.getByStatusAndOrderTimeLT(Orders.PENDING_PAYMENT, LocalDateTime.now().minusMinutes(15));
        if (!orders.isEmpty()) {
            orders.forEach(order -> {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason(MessageConstant.ORDER_TIME_OUT);
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            });
        }
    }

    @Scheduled(cron = "0 * * * * *")
    public void processDeliveryOrder() {
        log.info("定时处理未确认的订单：{}", LocalDateTime.now());
        List<Orders> orders = orderMapper.getByStatusAndOrderTimeLT(Orders.DELIVERY_IN_PROGRESS, LocalDateTime.now().minusHours(2));
        if (!orders.isEmpty()) {
            orders.forEach(order -> {
                order.setStatus(Orders.CANCELLED);
                order.setCancelReason(MessageConstant.ORDER_TIME_OUT);
                order.setCancelTime(LocalDateTime.now());
                orderMapper.update(order);
            });
        }
    }
}
