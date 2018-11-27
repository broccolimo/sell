package com.mo.Repository;

import com.mo.Entity.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMasterRepositoryTest {

    private final String OPENID = "110";

    @Autowired
    private OrderMasterRepository repository;


    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("jy_os_001");
        orderMaster.setBuyerName("齐天大圣");
        orderMaster.setBuyerPhone("13412312312");
        orderMaster.setBuyerAddress("花果山");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.33));
        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest request = new PageRequest(0, 1);
        Page<OrderMaster> res = repository.findByBuyerOpenid(OPENID, request);
        Assert.assertNotEquals(0, res.getTotalElements());
    }
}