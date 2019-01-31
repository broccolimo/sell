package com.mo.Service;

import com.mo.DTO.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    //创建
    OrderDTO create(OrderDTO orderDTO);

    //根据orderId查询一个订单
    OrderDTO findOne(String orderId);

    //根据openid查询所有订单
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    //取消
    OrderDTO cancel(OrderDTO orderDTO);

    //完结
    OrderDTO finish(OrderDTO orderDTO);

    //支付
    OrderDTO paid(OrderDTO orderDTO);


}
