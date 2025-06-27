package com.flashsale.mapper;

import com.flashsale.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO seckill_order(user_id, sku_id, create_time) VALUES(#{userId}, #{skuId}, #{createTime})")
    int insert(Order order);
} 