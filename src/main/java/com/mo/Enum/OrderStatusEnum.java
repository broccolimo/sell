package com.mo.Enum;

import lombok.Getter;

/**
 * @author 音神
 * @date 2018/10/22 23:37
 *
 * 订单总表状态Enum
 */
@Getter
public enum OrderStatusEnum {

    NEW(0, "新订单"),
    FINISH(1, "完结"),
    CANCEL(2, "已取消"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
