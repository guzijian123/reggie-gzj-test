package com.example.reggiegzjtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggiegzjtest.entities.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 20412
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2022-09-07 10:21:58
* @Entity com.example.reggiegzjtest.entities.ShoppingCart
*/
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




