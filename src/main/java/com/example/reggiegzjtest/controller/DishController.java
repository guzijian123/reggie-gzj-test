package com.example.reggiegzjtest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggiegzjtest.common.R;
import com.example.reggiegzjtest.dto.DishDto;
import com.example.reggiegzjtest.entities.Category;
import com.example.reggiegzjtest.entities.Dish;
import com.example.reggiegzjtest.entities.DishFlavor;
import com.example.reggiegzjtest.service.CategoryService;
import com.example.reggiegzjtest.service.DishFlavorService;
import com.example.reggiegzjtest.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;  // 菜品表

    @Autowired
    private DishFlavorService dishFlavorService;  // 菜品口味表

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page> page(Integer page,Integer pageSize,String name){
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Dish::getName,name);
        dishLambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,dishLambdaQueryWrapper);
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = null;
        list = records.stream().map(item ->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if(category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable("id") Long id){
        DishDto byIdWithFlavor = dishService.getByIdWithFlavor(id);
        return R.success(byIdWithFlavor);
    }
    @PostMapping("/status/{state}")
    public R<String> status(@PathVariable("state")int status,@RequestParam List<String> ids){
        log.info("参数位：ids{},status:{}",ids,status);
        dishService.updateWithStatus(status,ids);
        return R.success("修改成功");
    }

    /**
     * 修改菜品信息
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
        String key = "dish:" + dishDto.getCategoryId() + ":1";
        redisTemplate.delete(key);
        return R.success("修改菜品成功");
    }

    /**
     * 根据条件查询对应的菜品数据
     * @return
     */
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        dishLambdaQueryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
//        dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
////        查询起售的菜品
//        dishLambdaQueryWrapper.eq(Dish::getStatus,1);
//        List<Dish> list = dishService.list(dishLambdaQueryWrapper);
//        return R.success(list);
//    }
    /**
     * 根据条件查询对应的菜品数据
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
//        从redis缓存中获取数据
        List<DishDto> dishDtoList = null;
        String key = "dish:" + dish.getCategoryId() +":"+dish.getStatus();
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
        if(dishDtoList != null){
            return R.success(dishDtoList);
        }
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        查询起售的菜品
        dishLambdaQueryWrapper.eq(Dish::getStatus,1);
        dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(dishLambdaQueryWrapper);
        dishDtoList = list.stream()
                .map(item -> {
                    DishDto dishDto = new DishDto();
                    BeanUtils.copyProperties(item, dishDto);
                    LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
                    dishDto.setFlavors(dishFlavorService.list(dishFlavorLambdaQueryWrapper));
                    return dishDto;
                })
                .collect(Collectors.toList());
//        不存在就缓存
        redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);
        return R.success(dishDtoList);
    }

}
