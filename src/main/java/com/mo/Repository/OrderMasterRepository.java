package com.mo.Repository;

import com.mo.Entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /**
     * 通过买家的微信id获取到他的订单主表
     * 如果不加pageable,查出来的所有的订单主表
     * 数量可能会比较大,所以需要分页
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
