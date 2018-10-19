package com.mo.Service.Impl;

import com.mo.Entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 音神
 * @date 2018/10/19 15:20
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryServiceImpl impl;

    @Test
    public void findOne() {
        ProductCategory productCategory = impl.findOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> list = impl.findAll();
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> type = Arrays.asList(1, 2, 3, 4);
        List<ProductCategory> list = impl.findByCategoryTypeIn(type);
        Assert.assertNotEquals(0, list.size());
        Assert.assertEquals(2, list.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory = new ProductCategory("热销榜", 10);
        ProductCategory res = impl.save(productCategory);
        Assert.assertNotNull(res);
    }
}