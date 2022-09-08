package com.example.reggiegzjtest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggiegzjtest.common.R;
import com.example.reggiegzjtest.dto.SetmealDto;
import com.example.reggiegzjtest.entities.Category;
import com.example.reggiegzjtest.entities.Setmeal;
import com.example.reggiegzjtest.entities.SetmealDish;
import com.example.reggiegzjtest.service.CategoryService;
import com.example.reggiegzjtest.service.SetmealDishService;
import com.example.reggiegzjtest.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        log.info(setmealDto.toString());
        setmealService.saveWithDish(setmealDto);
        return R.success("添加套餐成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Setmeal> pageInfo = new Page<Setmeal>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(name != null,Setmeal::getName,name);
        setmealService.page(pageInfo, setmealLambdaQueryWrapper);
//        对象拷贝
        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();
        List<SetmealDto> collect = records.stream()
                .map(item -> {
                    SetmealDto setmealDto = new SetmealDto();
                    BeanUtils.copyProperties(item,setmealDto);
                    Long categoryId = item.getCategoryId();
                    Category category = categoryService.getById(categoryId);
                    String name1 = category.getName();
                    if(category.getName() != null){
                        setmealDto.setCategoryName(name1);
                    }
                    return setmealDto;
                }).collect(Collectors.toList());
        setmealDtoPage.setRecords(collect);
        return R.success(setmealDtoPage);
    }
    @DeleteMapping
    public R<String> delete(@RequestParam List<String> ids){
        log.info("ids:{}",ids);
        setmealService.removeWithDish(ids);
        return R.success("删除成功");
    }
    /**
     * 根据条件查询套餐数据
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId + ':' + #setmeal.status ")
    public R<List<SetmealDto>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(setmeal.getCategoryId()!=null, Setmeal::getCategoryId,setmeal.getCategoryId());
        setmealLambdaQueryWrapper.eq(setmeal.getStatus() !=null,Setmeal::getStatus,setmeal.getStatus());
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(setmealLambdaQueryWrapper);
        List<SetmealDto> setmealDtos = null;
        setmealDtos = list.stream()
                .map(item -> {
                    SetmealDto setmealDto1 = new SetmealDto();
                    BeanUtils.copyProperties(item,setmealDto1);
                    LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId,item.getId());
                    List<SetmealDish> list1 = setmealDishService.list(setmealDishLambdaQueryWrapper);
                    setmealDto1.setSetmealDishes(list1);
                    return setmealDto1;
                })
                .collect(Collectors.toList());
        return R.success(setmealDtos);
//        return null;
    }
}
