package com.mo.Entity;

import com.mo.Enum.OrderStatusEnum;
import com.mo.Enum.PayStatusEnum;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 音神
 * @date 2018/10/22 23:33
 */
@Entity
@Table(name = "order_master")
@DynamicUpdate
public class OrderMaster {

    private String orderId;

    //买家名字
    private String buyerName;

    //买家电话
    private String buyerPhone;

    //买家地址
    private String buyerAddress;

    //买家微信号
    private String buyerOpenid;

    //订单总额
    private BigDecimal orderAmount;

    //订单状态, 默认0, 新下单
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    //支付状态, 默认0, 未支付
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;
}
