package com.example.reggiegzjtest.service;

import com.example.reggiegzjtest.entities.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 20412
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2022-09-07 15:36:21
*/
public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
