package com.example.reggiegzjtest.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggiegzjtest.common.R;
import com.example.reggiegzjtest.dto.OrdersDto;
import com.example.reggiegzjtest.entities.AddressBook;
import com.example.reggiegzjtest.entities.OrderDetail;
import com.example.reggiegzjtest.entities.Orders;
import com.example.reggiegzjtest.service.AddressBookService;
import com.example.reggiegzjtest.service.OrderDetailService;
import com.example.reggiegzjtest.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    @Autowired
    private AddressBookService addressBookService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据:{}",orders);

        ordersService.submit(orders);
        return R.success("下单成功");
    }
    @GetMapping("/page")
    public R<Page<OrdersDto>> list(int page, int pageSize, String number, String beginTime,String endTime){
        log.info("beginTime：{},  endTime:{}",beginTime,endTime);
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>();
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(number),Orders::getNumber,number);
        if(beginTime !=null && endTime != null){
            queryWrapper.between(Orders::getOrderTime,beginTime,endTime);
            queryWrapper.between(Orders::getCheckoutTime,beginTime,endTime);
        }
        queryWrapper.orderByDesc(Orders::getOrderTime);
        ordersService.page(ordersPage,queryWrapper);
        BeanUtils.copyProperties(ordersPage,ordersDtoPage,"records");
        List<Orders> records = ordersPage.getRecords();
        List<OrdersDto> ordersDtos = records.stream()
                .map(item -> {
                    OrdersDto ordersDto = new OrdersDto();
                    Long addressBookId = item.getAddressBookId();
                    AddressBook addressBook = addressBookService.getById(addressBookId);
                    BeanUtils.copyProperties(item, ordersDto);
                    ordersDto.setUserName(addressBook.getConsignee());
                    ordersDto.setAddress(addressBook.getDetail());
                    return ordersDto;
                })
                .collect(Collectors.toList());
        ordersDtoPage.setRecords(ordersDtos);
        return R.success(ordersDtoPage);
    }

    @PutMapping
    public R<String> update(@RequestBody Map map){
        log.info("派送信息：{}",map);
        LambdaUpdateWrapper<Orders> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(map.get("id") != null,Orders::getId,map.get("id"));
        updateWrapper.set(Orders::getStatus,map.get("status"));
        ordersService.update(updateWrapper);
        return R.success("派送成功");
    }
}
