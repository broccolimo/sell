package com.mo.Service.Impl;

import com.mo.Converter.OrderMaster2OrderDTOConverter;
import com.mo.DTO.CartDTO;
import com.mo.DTO.OrderDTO;
import com.mo.Entity.OrderDetail;
import com.mo.Entity.OrderMaster;
import com.mo.Entity.ProductInfo;
import com.mo.Enum.OrderStatusEnum;
import com.mo.Enum.PayStatusEnum;
import com.mo.Enum.ResultEnum;
import com.mo.Exception.SellException;
import com.mo.Repository.OrderDetailRepository;
import com.mo.Repository.OrderMasterRepository;
import com.mo.Service.OrderService;
import com.mo.Service.ProductInfoService;
import com.mo.Utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    //service层对象
    //service就是模块的划分
    //service层使用其他模块功能用service 使用自身功能用dao
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
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        return new PageImpl<>(OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent()), pageable, orderMasterPage.getTotalElements());
    }

    //#6-8
    //取消一个订单
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //step 1
        //首先判断订单的状态
        //如果订单已被完成或者不存在
        //则不能取消
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //step 2
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster res = orderMasterRepository.save(orderMaster);
        if(res == null){
            log.error("【取消订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //step 3
        //返还库存
        //订单应该是有订单详情的 如果没有 说明不正常
        //为了健壮性 判断一下
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单详情为空, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        //调用加库存的方法
        List<CartDTO> cartDTOList = orderDTO
                .getOrderDetailList()
                .stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        //step 4
        //如果已支付需要退款
        if(orderDTO.getOrderStatus().equals(OrderStatusEnum.FINISH.getCode())){
            //TODO
        }

        return orderDTO;
    }

    //#6-9
    //完结订单
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //step 1
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态不正确, orderStatus={}", orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //step 2
        //修改状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster res = orderMasterRepository.save(orderMaster);
        if(res == null){
            log.error("【完结订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    //#6-9
    //支付订单
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //step 1
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】订单状态不正确, orderStatus={}", orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //step 2
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【支付订单】支付状态不正确, payStatus={}", orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //step 3
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster res = orderMasterRepository.save(orderMaster);
        if(res == null){
            log.error("【支付订单】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }
}
