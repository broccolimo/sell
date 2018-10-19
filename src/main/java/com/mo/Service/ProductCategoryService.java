package com.mo.Service;

import com.mo.Entity.ProductCategory;

import java.util.List;

/**
 * @author 音神
 * @date 2018/10/19 15:11
 */

public interface ProductCategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> ProductCategoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
