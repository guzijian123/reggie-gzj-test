package com.example.reggiegzjtest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.reggiegzjtest.common.BaseContext;
import com.example.reggiegzjtest.common.R;
import com.example.reggiegzjtest.entities.ShoppingCart;
import com.example.reggiegzjtest.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("购物车数据:{}",shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());
//        查询当前菜品或者套餐是否在我们购物车
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
//            添加到购物车的是菜品
        if(dishId != null){

            queryWrapper.eq(dishId!=null,ShoppingCart::getDishId,dishId);
        }
//            套餐情况
        if(setmealId != null)
            queryWrapper.eq(setmealId!=null,ShoppingCart::getSetmealId,setmealId);
//            执行查询
        ShoppingCart shoopingCartOne = shoppingCartService.getOne(queryWrapper);
        if(shoopingCartOne!=null){
            Integer number = shoopingCartOne.getNumber();
            shoopingCartOne.setNumber(number+1);
            shoppingCartService.updateById(shoopingCartOne);
        }else {
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            shoopingCartOne = shoppingCart;
        }
//
        return R.success(shoopingCartOne);
    }

    /**
     * 查看购物车
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartLambdaQueryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(shoppingCartLambdaQueryWrapper);
        return R.success(list);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clen(){
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(shoppingCartLambdaQueryWrapper);
        return R.success("清空购物车成功");
    }
}
