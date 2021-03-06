package com.mo.Repository;

import com.mo.Entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 音神
 * @date 2018/10/17 16:23
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory("火热", 3);
        ProductCategory res = productCategoryRepository.save(productCategory);
        Assert.assertNotNull(res);
    }

    @Test
    /**
     * 事务
     * 测试里边的事务是完全回滚的
     * service里边的事务是抛出异常了才回滚
     * 仅供测试使用，不会影响数据库
     */
    @Transactional
    public void modifyTest(){
        /*ProductCategory old = productCategoryRepository.findByCategoryName("女生最爱");
        Integer id = old.getCategoryId();
        Integer type = old.getCategoryType();
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryId(id);
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(type);
        productCategoryRepository.save(productCategory);*/
        /*ProductCategory old = productCategoryRepository.findByCategoryName("男生最爱");
        old.setCategoryType(68);
        productCategoryRepository.save(old);*/
        /*ProductCategory productCategory = new ProductCategory("女生最爱", 234);
        ProductCategory res = productCategoryRepository.save(productCategory);
        Assert.assertNotNull(res);*/
    }


    @Test
    public void findByCategoryTypeInTest(){
        /*List<Integer> list = Arrays.asList(2, 3, 4, 3, 2, 3, 4, 5, 10);
        List<ProductCategory> res = productCategoryRepository.findByCategoryTypeIn(list);
        Assert.assertEquals(2, res.size());*/
    }
}