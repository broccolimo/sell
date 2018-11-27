package com.mo.Enum;

import lombok.Getter;

/**
 * @author 音神
 * @date 2018/10/22 23:42
 *
 * 订单总表支付状态Enum */
@Getter
public enum PayStatusEnum {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
