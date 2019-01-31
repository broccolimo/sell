package com.mo.Service.Impl;

import com.mo.DTO.OrderDTO;
import com.mo.Enum.ResultEnum;
import com.mo.Exception.SellException;
import com.mo.Service.BuyerService;
import com.mo.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOneOrderWithOpenid(String openid, String orderId) {
        return check(openid, orderId);
    }

    @Override
    public void cancelOrderWithOpenid(String openid, String orderId) {
        OrderDTO orderDTO = check(openid, orderId);
        if(orderDTO == null){
            log.error("【取消订单】该订单不存在, orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        orderService.cancel(orderDTO);
    }

    private OrderDTO check(String openid, String orderId){
        //此方法已经对空进行过处理了
        OrderDTO orderDTO = orderService.findOne(orderId);
        if(orderDTO == null) return null;
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("该订单不属于此用户, orderId={}, openid={}", orderId, openid);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
