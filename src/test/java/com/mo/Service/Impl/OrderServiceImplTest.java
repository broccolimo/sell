package com.mo.Service.Impl;

import com.mo.DTO.OrderDTO;
import com.mo.Entity.OrderDetail;
import com.mo.Enum.OrderStatusEnum;
import com.mo.Enum.PayStatusEnum;
import com.mo.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderServiceImplTest {

    //测试自己首先要声明自己
    @Autowired
    private OrderService orderService;

    private final String buyerOpenid = "110";

    private final String ORDER_ID = "1543307891625571337";

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
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        log.info("【查询一个订单】 {}", orderDTO);
        Assert.assertEquals(ORDER_ID, orderDTO.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest pageRequest = new PageRequest(0,10);
        Page<OrderDTO> orderDTOPage = orderService.findList(buyerOpenid, pageRequest);
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO testObj = orderService.findOne(ORDER_ID);
        OrderDTO res = orderService.cancel(testObj);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), res.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO testObj = orderService.findOne(ORDER_ID);
        OrderDTO res = orderService.finish(testObj);
        Assert.assertEquals(OrderStatusEnum.FINISH.getCode(), res.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO testObj = orderService.findOne(ORDER_ID);
        OrderDTO res = orderService.paid(testObj);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), res.getPayStatus());
    }

    @Test
    public void test(){
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 4, 1);
        List<Integer> res1 = list.stream().map(e -> e * e).distinct().collect(Collectors.toList());
        res1.forEach(System.out :: println);
    }
}