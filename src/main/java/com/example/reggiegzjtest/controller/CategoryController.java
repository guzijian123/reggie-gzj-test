package com.example.reggiegzjtest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.reggiegzjtest.common.R;
import com.example.reggiegzjtest.entities.Category;
import com.example.reggiegzjtest.entities.Employee;
import com.example.reggiegzjtest.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        boolean save = categoryService.save(category);
        return R.success("新增分类成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        Page<Category> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        按sort排序
        categoryLambdaQueryWrapper.orderByAsc(Category::getSort);
//        执行sql
        categoryService.page(pageInfo,categoryLambdaQueryWrapper);
        return R.success(pageInfo);
    }
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除分类，id为:{}",ids);
        // 是否管理菜品 ，如果已经关联不能删除

        categoryService.remove(ids);
        return R.success("分类信息删除成功");
    }
    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }

    @GetMapping("/list")
    public R<List<Category>> getListCategory(Category category){
        log.info("查询菜品类型:{}",category.getType());
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        查询对应类型的菜品
        categoryLambdaQueryWrapper.eq(category.getType() != null,Category::getType,category.getType());
        categoryLambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getCreateTime); // 排序
//        获取相同类型的菜品
        List<Category> list = categoryService.list(categoryLambdaQueryWrapper);
        return R.success(list);
    }

}
