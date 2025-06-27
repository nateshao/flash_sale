package com.flashsale.mapper;

import com.flashsale.entity.OrderMsg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface OrderMsgMapper {
    @Insert("INSERT INTO seckill_order_msg(user_id, sku_id, status, create_time) VALUES(#{userId}, #{skuId}, #{status}, #{createTime})")
    int insert(OrderMsg msg);

    @Select("SELECT * FROM seckill_order_msg WHERE status = 0 LIMIT 100")
    List<OrderMsg> selectUnsent();

    @Update("UPDATE seckill_order_msg SET status = 1 WHERE id = #{id}")
    int markSent(Long id);
} 