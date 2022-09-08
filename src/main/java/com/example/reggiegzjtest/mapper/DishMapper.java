package com.example.reggiegzjtest.mapper;

import com.example.reggiegzjtest.dto.DishDto;
import com.example.reggiegzjtest.entities.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 20412
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-08-31 00:18:37
* @Entity com.example.reggiegzjtest.entities.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}




