package com.example.reggiegzjtest.mapper;

import com.example.reggiegzjtest.entities.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 20412
* @description 针对表【order_detail(订单明细表)】的数据库操作Mapper
* @createDate 2022-09-07 15:36:22
* @Entity com.example.reggiegzjtest.entities.OrderDetail
*/
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

}




