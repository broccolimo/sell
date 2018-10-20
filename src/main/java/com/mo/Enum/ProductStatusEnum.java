package com.mo.Enum;

import lombok.Getter;

/**
 * 商品状态 0正常 1下架
 * @author 音神
 * @date 2018/10/19 17:43
 */
@Getter
public enum ProductStatusEnum {

    UP(0, "上架"),
    DOWN(1, "下架")
    ;

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
