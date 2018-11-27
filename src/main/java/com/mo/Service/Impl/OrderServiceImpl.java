package com.mo.Service.Impl;

import com.mo.DTO.CartDTO;
import com.mo.DTO.OrderDTO;
import com.mo.Entity.OrderDetail;
import com.mo.Entity.OrderMaster;
import com.mo.Entity.ProductInfo;
import com.mo.Enum.ResultEnum;
import com.mo.Exception.SellException;
import com.mo.Repository.OrderDetailRepository;
import com.mo.Repository.OrderMasterRepository;
import com.mo.Service.OrderService;
import com.mo.Service.ProductInfoService;
import com.mo.Utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    //service层对象
    private ProductInfoService productInfoService;

    @Autowired
    //dao层对象
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    //dao层对象
    private OrderMasterRepository orderMasterRepository;

    @Override
    //一旦抛异常就全部回滚
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        //订单主表id
        String orderId = KeyUtil.getUniqueKey();

        //订单总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1. 查询商品（数量， 单价）
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            //1.1 获取商品信息
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //1.2 计算总价
            //BigDecimal不能用一般的加号和乘号
            //乘法用multiply(要求调用者是BigDecimal)
            //加法用add
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //1.3 订单详情入库(OrderDetail)
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetailRepository.save(orderDetail);
        }


        //2. 订单总表入库
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMasterRepository.save(orderMaster);

        //3. 扣库存
        List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOS);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findById(orderId).get();
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> list = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(list)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(list);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
