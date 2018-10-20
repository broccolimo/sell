package com.mo.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author 音神
 * @date 2018/10/19 15:35
 */
@Entity
@Table(name = "product_info")
@Data
public class ProductInfo {

    @Id
    private  String productId;

    private String productName;

    private BigDecimal productPrice;

    private String productDescription;

    private String productIcon;

    //库存
    private Integer productStock;

    //商品状态 0正常 1下架
    private Integer productStatus;

    private Integer categoryType;
}
