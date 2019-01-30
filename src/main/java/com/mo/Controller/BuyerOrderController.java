package com.mo.Controller;

import com.mo.Converter.BuyerOrder2OrderDTO;
import com.mo.DTO.OrderDTO;
import com.mo.Enum.ResultEnum;
import com.mo.Exception.SellException;
import com.mo.Form.Buyer_Order_Create;
import com.mo.Service.OrderService;
import com.mo.Utils.ResultVOUtil;
import com.mo.VO.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid Buyer_Order_Create obj, BindingResult bindingResult){
        //验证出现错误
        //记住这个写法
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确, form={}", obj);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        //service中创建订单用的是OrderDTO
        //所以这里要构造OrderDTO
        OrderDTO orderDTO = BuyerOrder2OrderDTO.convertForCreate(obj);

        //为了程序的健壮性
        //这里还是判断一下购物车是否为空
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空");
        }

        //调用service层方法创建OrderDTO
        OrderDTO res = orderService.create(orderDTO);

        //构造ResultVO
        Map<String, String> map = new HashMap<>();
        map.put("orderId", res.getOrderId());
        return ResultVOUtil.success(map);
    }

    //订单列表
    //参数不是特别多 不用构建Form对象 直接写
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page", defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        //openid一定不能为空
        if(StringUtils.isEmpty(openid)){
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        //用构造器创建PageRequest的方法已经deprecated
        //用PageRequest的静态方法of()
        Page<OrderDTO> pages = orderService.findList(openid, PageRequest.of(page, size));
        return ResultVOUtil.success(pages.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId){

        //TODO
        //其实获取数据只用orderId就行
        //openid要用来做校验 有安全性问题 需要改进 在此先行略过

        if(StringUtils.isEmpty(orderId)){
            log.error("【查询订单列表】orderId为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO orderDTO = orderService.findOne(orderId);
        return ResultVOUtil.success(orderDTO);
    }
    //取消订单
}
