package com.mo.Entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 *  订单详情表
 */
@Entity
@Table(name = "order_detail")
@DynamicUpdate
@Data
public class OrderDetail {

    @Id
    private String detailId;

    //订单总表id
    private String orderId;

    //商品id
    private String productId;

    //商品名字
    private String productName;

    //商品价格
    private BigDecimal productPrice;

    //商品图标链接地址
    private String productIcon;

    //商品数量
    private Integer productQuantity;
}
