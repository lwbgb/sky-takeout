package pers.lwb.service.Impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.lwb.constant.MessageConstant;
import pers.lwb.context.LocalContext;
import pers.lwb.dto.OrderSubmitDTO;
import pers.lwb.dto.OrdersPaymentDTO;
import pers.lwb.entity.*;
import pers.lwb.exception.*;
import pers.lwb.mapper.*;
import pers.lwb.service.OrderService;
import pers.lwb.utils.WeChatPayUtil;
import pers.lwb.vo.OrderPaymentVO;
import pers.lwb.vo.OrderSubmitVO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final OrderDetailMapper orderDetailMapper;

    private final AddressBookMapper addressBookMapper;

    private final ShoppingCartMapper shoppingCartMapper;

    private final UserMapper userMapper;

    private final WeChatPayUtil weChatPayUtil;

    public OrderServiceImpl(OrderMapper orderMapper, OrderDetailMapper orderDetailMapper, AddressBookMapper addressBookMapper, ShoppingCartMapper shoppingCartMapper, UserMapper userMapper, WeChatPayUtil weChatPayUtil) {
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
        this.addressBookMapper = addressBookMapper;
        this.shoppingCartMapper = shoppingCartMapper;
        this.userMapper = userMapper;
        this.weChatPayUtil = weChatPayUtil;
    }

    @Override
    @Transactional(rollbackFor = BaseException.class)
    public OrderSubmitVO submit(OrderSubmitDTO orderSubmitDTO) {
        // 1.判断用户地址是否填写
        AddressBook addressBook = addressBookMapper.getById(orderSubmitDTO.getAddressBookId());
        if (addressBook == null) {
            throw new AddressBookBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        // 2.判断用户购物车是否有商品
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(LocalContext.getCurrentId())
                .build();
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.list(shoppingCart);
        if (shoppingCarts.isEmpty()) {
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        // 3.向订单表中插入信息
        Orders orders = Orders.builder()
                .number(String.valueOf(System.currentTimeMillis()))
                .status(Orders.PENDING_PAYMENT)
                .userId(LocalContext.getCurrentId())
                .orderTime(LocalDateTime.now())
//                .checkoutTime()
                .payStatus(Orders.UN_PAID)
                .phone(addressBook.getPhone())
                .address(addressBook.getProvinceName() + addressBook.getCityName() +
                        addressBook.getDistrictName() + addressBook.getDetail())
//                .userName(addressBook.)
                .consignee(addressBook.getConsignee())
                .build();
        BeanUtils.copyProperties(orderSubmitDTO, orders);
        int n = orderMapper.insert(orders);
        if (n <= 0) {
            throw new InsertException(MessageConstant.ORDER_INSERT_ERROR);
        }

        // 4.向订单详情表中插入信息
        List<OrderDetail> orderDetails = new ArrayList<>(shoppingCarts.size());
        shoppingCarts.forEach(cart -> {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetails.add(orderDetail);

        });
        n = orderDetailMapper.insertBatch(orderDetails);
        if (n <= 0) {
            throw new InsertException(MessageConstant.ORDER_INSERT_ERROR);
        }

        // 5. 清空购物车
        n = shoppingCartMapper.delete(shoppingCart);
        if (n <= 0) {
            throw new DeleteErrorException(MessageConstant.SHOPPING_CART_DELETE_ERROR);
        }

        // 6.封装结果
        OrderSubmitVO orderSubmitVO = OrderSubmitVO.builder()
                .id(orders.getId())
                .orderNumber(orders.getNumber())
                .orderTime(orders.getOrderTime())
                .orderAmount(orders.getAmount())
                .build();
        return orderSubmitVO;
    }

    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 当前登录用户id
        Long userId = LocalContext.getCurrentId();
        User user = userMapper.getById(userId);

        //调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getOrderNumber(),
                new BigDecimal(0.01),
                "苍穹外卖订单",
                user.getOpenid()
        );

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")) {
            throw new OrderBusinessException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));

        return vo;
    }

    public void paySuccess(String outTradeNo) {

        // 根据订单号查询订单
        Orders ordersDB = orderMapper.getByNumber(outTradeNo);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(ordersDB.getId())
                .status(Orders.TO_BE_CONFIRMED)
                .payStatus(Orders.PAID)
                .checkoutTime(LocalDateTime.now())
                .build();

        orderMapper.update(orders);
    }
}
