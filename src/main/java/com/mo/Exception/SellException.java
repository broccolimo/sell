package com.mo.Exception;

import com.mo.Enum.ResultEnum;

public class SellException extends RuntimeException {

    private Integer code;
    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
}
