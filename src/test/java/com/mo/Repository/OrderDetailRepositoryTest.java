package com.mo.Repository;

import com.mo.Entity.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("jy_od_002");
        orderDetail.setOrderId("jy_os_001");
        orderDetail.setProductIcon("xxxdd.jpg");
        orderDetail.setProductId("jy_pi_002");
        orderDetail.setProductName("火腿");
        orderDetail.setProductPrice(new BigDecimal(7.46));
        orderDetail.setProductQuantity(3);
        OrderDetail res = repository.save(orderDetail);
        Assert.assertNotNull(res);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> res = repository.findByOrderId("jy_os_001");
        Assert.assertNotEquals(0, res.size());
    }
}