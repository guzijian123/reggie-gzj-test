package com.example.reggiegzjtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggiegzjtest.entities.OrderDetail;
import com.example.reggiegzjtest.service.OrderDetailService;
import com.example.reggiegzjtest.mapper.OrderDetailMapper;
import org.springframework.stereotype.Service;

/**
* @author 20412
* @description 针对表【order_detail(订单明细表)】的数据库操作Service实现
* @createDate 2022-09-07 15:36:22
*/
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail>
    implements OrderDetailService{

}




