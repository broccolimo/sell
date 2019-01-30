package com.mo.Converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mo.DTO.OrderDTO;
import com.mo.Entity.OrderDetail;
import com.mo.Enum.ResultEnum;
import com.mo.Exception.SellException;
import com.mo.Form.Buyer_Order_Create;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class BuyerOrder2OrderDTO {

    public static OrderDTO convertForCreate(Buyer_Order_Create obj){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(obj.getName());
        orderDTO.setBuyerAddress(obj.getAddress());
        orderDTO.setBuyerPhone(obj.getPhone());
        orderDTO.setBuyerOpenid(obj.getOpenid());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        Gson gson = new Gson();
        try{
            orderDetailList = gson.fromJson(obj.getItems(),
                    new TypeToken<List<OrderDetail>>(){}.getType());
        }
        catch (Exception e){
            log.error("【对象转换】错误, string={}", obj.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
