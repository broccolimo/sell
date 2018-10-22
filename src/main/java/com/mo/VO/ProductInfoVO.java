package com.mo.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 商品详情
 * @author 音神
 * @date 2018/10/20 22:43
 */
@Setter
//fields的声明顺序决定前端收到json中的排列顺序
public class ProductInfoVO {

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;


}
