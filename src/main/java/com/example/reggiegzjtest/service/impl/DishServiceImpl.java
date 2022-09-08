package com.example.reggiegzjtest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggiegzjtest.dto.DishDto;
import com.example.reggiegzjtest.entities.Dish;
import com.example.reggiegzjtest.entities.DishFlavor;
import com.example.reggiegzjtest.service.DishFlavorService;
import com.example.reggiegzjtest.service.DishService;
import com.example.reggiegzjtest.mapper.DishMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 20412
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-08-31 00:18:37
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
    implements DishService{
    @Autowired
    private DishFlavorService dishFlavorService;
    /**
     * 保存菜品 和菜品口味
     * @param dishDto
     */
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);  // mybatis - plus 先把对象中的数据id赋值 在写入数据库
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream()  // 把流转换成集合在返回给 flavors
                .map(item -> {
                    item.setDishId(id);
                    return item;  // 遍历每个集合中的子项  在把dishId设置后返回
                }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);  // 保存的是一个集合数据  一个菜品可以选者多种口味
    }

    /**
     * 获取菜品信息
     * @param id
     * @return
     */
    public DishDto getByIdWithFlavor(Long id) {
        DishDto dishDto = new DishDto();
        Dish dish = this.getById(id);
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,id);
        List<DishFlavor> list = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
        dishDto.setFlavors(list);

        return dishDto;
    }

    /**
     * 修改菜品信息
     * @param dishDto
     */
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        //  更新dish表
        this.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(dishFlavorLambdaQueryWrapper);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().
                map(item ->{
                    item.setDishId(dishDto.getId());
                    return item;
                })
                .collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
}




