package com.mo.Service;

import com.mo.Entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author 音神
 * @since  2018/10/19 16:54
 */

public interface ProductInfoService {

    //查找一个商品
    ProductInfo findOne(String productId);

    //查询所有上架商品
    List<ProductInfo> findUpAll();

    //分页查询所有商品
    Page<ProductInfo> findAll(Pageable pageable);

    //添加商品
    ProductInfo save(ProductInfo productInfo);

    //加库存

    //减库存
}
