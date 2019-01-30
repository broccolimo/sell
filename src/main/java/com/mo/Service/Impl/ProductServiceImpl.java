package com.mo.Service.Impl;

import com.mo.DTO.CartDTO;
import com.mo.Entity.ProductInfo;
import com.mo.Enum.ProductStatusEnum;
import com.mo.Enum.ResultEnum;
import com.mo.Exception.SellException;
import com.mo.Repository.ProductInfoRepository;
import com.mo.Service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 音神
 * @date 2018/10/19 17:39
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        //如果数据库中没有productId对应的记录
        //这里不会返回null 而会报错
        //return repository.findById(productId).get();

        //至于这个Optional 不要试图用是否为null进行判断
        //即使它里边没值 它本身也不是null
        //应该用isPresent()判断里边是否有值
        Optional<ProductInfo> optinal = repository.findById(productId);
        return optinal.isPresent() ? optinal.get() : null;
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    //加库存
    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = findOne(cartDTO.getProductId());
            if(productInfo == null){
                log.error("【增加库存】商品不存在, productId={}", cartDTO.getProductId());
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            productInfo.setProductStock(productInfo.getProductStock() + cartDTO.getProductQuantity());
            save(productInfo);
        }
    }

    //减库存
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO : cartDTOList){
            ProductInfo productInfo = findOne(cartDTO.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() - cartDTO.getProductQuantity();

            if(result < 0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }

            productInfo.setProductStock(result);
            save(productInfo);
        }
    }
}
