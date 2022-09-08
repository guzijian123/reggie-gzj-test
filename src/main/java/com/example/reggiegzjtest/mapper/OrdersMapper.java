package com.example.reggiegzjtest.mapper;

import com.example.reggiegzjtest.entities.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 20412
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2022-09-07 15:36:21
* @Entity com.example.reggiegzjtest.entities.Orders
*/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




