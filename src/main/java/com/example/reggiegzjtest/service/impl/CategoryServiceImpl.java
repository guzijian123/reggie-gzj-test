package com.example.reggiegzjtest.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggiegzjtest.common.CustomException;
import com.example.reggiegzjtest.entities.Dish;
import com.example.reggiegzjtest.entities.Setmeal;
import com.example.reggiegzjtest.mapper.CategoryMapper;
import com.example.reggiegzjtest.service.CategoryService;
import com.example.reggiegzjtest.service.DishService;
import com.example.reggiegzjtest.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.reggiegzjtest.entities.Category;
/**
* @author 20412
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-08-30 21:13:06
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {

    @Autowired
    public DishService dishService;

    @Autowired
    public SetmealService setmealService;
    @Autowired
    public CategoryService categoryService;
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if(count1 > 0){
            throw new CustomException("菜品已关联，不能删除");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 = setmealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0){
            throw new CustomException("已关联套餐，不能删除");
        }
        categoryService.removeById(id);

    }
}




