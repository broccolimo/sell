package com.mo.Service;

import com.mo.DTO.OrderDTO;

//处理针对买家的一些方法
public interface BuyerService {

    //区别于OrderService中的两个方法
    //那个只是基础的功能
    //这个在上边加上了openid的校验
    OrderDTO findOneOrderWithOpenid(String openid, String orderId);

    void cancelOrderWithOpenid(String openid, String orderId);
}
