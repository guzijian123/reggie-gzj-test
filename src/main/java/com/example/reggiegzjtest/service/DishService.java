package com.example.reggiegzjtest.service;

import com.example.reggiegzjtest.dto.DishDto;
import com.example.reggiegzjtest.entities.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 20412
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-08-31 00:18:37
*/
public interface DishService extends IService<Dish> {
//    设置彩屏和口味
    public void saveWithFlavor(DishDto dishDto);

//    获取菜品合口味
    public DishDto getByIdWithFlavor(Long id);

//    修改菜品信息
    public void updateWithFlavor(DishDto dishDto);
}
