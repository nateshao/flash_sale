package com.flashsale.mapper;

import com.flashsale.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {
    @Insert("INSERT INTO seckill_order(user_id, sku_id, create_time) VALUES(#{userId}, #{skuId}, #{createTime})")
    int insert(Order order);

    @Update("UPDATE seckill_order SET stock = stock - 1, version = version + 1 WHERE id = #{id} AND version = #{version} AND stock > 0")
    int deductStock(@Param("id") Long id, @Param("version") Integer version);
} 