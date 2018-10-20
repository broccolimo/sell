package com.mo.VO;

import lombok.Data;

/**
 * http请求的最外层对象
 * @author 音神
 * @date 2018/10/20 0:10
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;

}
