package com.mo.DTO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mo.Entity.OrderDetail;
import com.mo.Utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//data transfer object
@Data
//值为null的不返回至json
//可在配置文件中进行全局修改
//@JsonInclude(JsonInclude.Include.NON_NULL)
//后续要操作的数据一是从前端获取的,二是需要后端来生成的
//这里只存从前端获取到的属性,可以避免数据覆盖

//上边说的可能不太对 2018-12-10
//代码顺序决定返回json中的属性排列顺序
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    private List<OrderDetail> orderDetailList;



}
