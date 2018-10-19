package com.mo.Entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 类目
 * @author 音神
 * @date 2018/10/17 16:03
 */
@Table(name = "product_category")
@Entity
//这个不加的话 更新时update_time不会发生变化
//@DynamicUpdate
//lombok 代替getter/setter/toString方法
@Data
public class ProductCategory {

    //如果只写@GeneratedValue
    //默认的策略是GenerationType.AUTO
    //数据库中会多出一张hibernate_sequence表
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    //类目名称
    private String categoryName;

    //类目编号
    private Integer categoryType;

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
