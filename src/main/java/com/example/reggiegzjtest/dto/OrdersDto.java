package com.example.reggiegzjtest.dto;

import com.example.reggiegzjtest.entities.OrderDetail;
import com.example.reggiegzjtest.entities.Orders;
import lombok.Data;

import java.util.List;

@Data
public class OrdersDto extends Orders {
    public List<OrderDetail> orderDetails;
}
