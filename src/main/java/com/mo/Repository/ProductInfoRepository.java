package com.mo.Repository;

import com.mo.Entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 音神
 * @date 2018/10/19 15:55
 */

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    //通过商品状态查找
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
