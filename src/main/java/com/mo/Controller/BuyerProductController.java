package com.mo.Controller;

import com.mo.Entity.ProductCategory;
import com.mo.Entity.ProductInfo;
import com.mo.Service.ProductCategoryService;
import com.mo.Service.ProductService;
import com.mo.Utils.ResultVOUtil;
import com.mo.VO.ProductInfoVO;
import com.mo.VO.ProductVO;
import com.mo.VO.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 * @author 音神
 * @date 2018/10/20 0:06
 */

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVO list(){
        //查询出所有上架产品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //用lambda表达式取出所有上架产品的categoryType组成的list,里边可能会有重复的值
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());

        //根据这个list拿出相应的ProductCategory list,无需考虑重复的值
        List<ProductCategory> productCategoryList = productCategoryService.findByCategoryTypeIn(categoryTypeList);

        //数据拼装
        //最外层的叫ResultVO,其Data里有多个ProductVO,即多个类目
        //一个ProductVO里的productInfoVOList里有多个ProductInfoVO,即一个类目下有多个产品
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory : productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //target.setXXX(source.getXXX())的简便写法
                    //source必须有getter() target必须有setter()
                    //二者所需copy的field名字必须一模一样
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }

            productVO.setProductInfoVOList(productInfoVOList);

            productVOList.add(productVO);
        }


        return ResultVOUtil.success(productVOList);
    }
}
