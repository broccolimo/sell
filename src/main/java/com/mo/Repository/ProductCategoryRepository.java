package com.mo.Repository;

import com.mo.Entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 音神
 * @date 2018/10/17 16:10
 */

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    //给定类目名字，获取相应的对象
    ProductCategory findByCategoryName(String categoryName);

    //给定类目编号的List，获取相应的对象
    List<ProductCategory> findByCategoryTypeIn(List<Integer> ProductCategoryTypeList);
}
