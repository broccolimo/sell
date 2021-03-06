package com.mo.Service.Impl;

import com.mo.Entity.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 音神
 * @date 2018/10/19 17:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductServiceImpl impl;

    @Test
    public void findOne() {
        ProductInfo productInfo = impl.findOne("123456");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> list = impl.findUpAll();
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void findAll() {
        /*PageRequest request = new PageRequest(0, 2);
        Page<ProductInfo> page = impl.findAll(request);
        System.out.println("############### " + page.getTotalElements());*/
    }

    @Test
    public void save() {
        /*ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("888888");
        productInfo.setCategoryType(2);
        productInfo.setProductDescription("很好吃的蛋");
        productInfo.setProductIcon("http://qqqqq.jpg");
        productInfo.setProductName("卤鸡蛋");
        productInfo.setProductPrice(new BigDecimal(2.4));
        productInfo.setProductStatus(0);
        productInfo.setProductStock(20);
        ProductInfo res = impl.save(productInfo);
        Assert.assertNotNull(res);*/
    }
}