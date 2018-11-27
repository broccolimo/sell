package com.mo.Service.Impl;

import com.mo.DTO.OrderDTO;
import com.mo.Entity.OrderDetail;
import com.mo.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderServiceImplTest {

    //测试自己首先要声明自己
    @Autowired
    private OrderServiceImpl orderService;

    private final String buyerOpenid = "110";

    private final String orderId = "1543307891625571337";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("刘超");
        orderDTO.setBuyerAddress("陕西潼关");
        orderDTO.setBuyerPhone("13509278090");
        orderDTO.setBuyerOpenid(buyerOpenid);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("888888");
        o1.setProductQuantity(2);
        orderDetailList.add(o1);
        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);

        log.info("【创建订单】 result={}", result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne(orderId);
        log.info("【查询一个订单】 {}", orderDTO);
        Assert.assertEquals(orderId, orderDTO.getOrderId());
    }

    @Test
    public void findList() {
    }

    @Test
    public void cancel() {
    }

    @Test
    public void finish() {
    }

    @Test
    public void paid() {
    }

    @Test
    public void test(){
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 4, 1);
        List<Integer> res1 = list.stream().map(e -> e * e).distinct().collect(Collectors.toList());
        res1.forEach(System.out :: println);
    }
}