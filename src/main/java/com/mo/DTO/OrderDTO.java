package com.mo.DTO;

import com.mo.Entity.OrderDetail;
import com.mo.Enum.OrderStatusEnum;
import com.mo.Enum.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

//data transfer object
@Data
//后续要操作的数据一是从前端获取的,二是需要后端来生成的
//这里只存从前端获取到的属性,可以避免数据覆盖

//上边说的可能不太对 2018-12-10
public class OrderDTO {
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private List<OrderDetail> orderDetailList;
}
