package com.example.reggiegzjtest.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggiegzjtest.common.CustomException;
import com.example.reggiegzjtest.dto.SetmealDto;
import com.example.reggiegzjtest.entities.Setmeal;
import com.example.reggiegzjtest.entities.SetmealDish;
import com.example.reggiegzjtest.service.SetmealDishService;
import com.example.reggiegzjtest.service.SetmealService;
import com.example.reggiegzjtest.mapper.SetmealMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 20412
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2022-09-05 17:50:14
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal>
    implements SetmealService{

    @Autowired
    private SetmealDishService setmealDishService;
    /**
     * 保存套餐 还有菜品
     * @param setmealDto
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        Long id = setmealDto.getId();
        List<SetmealDish> collect = setmealDishes.stream()
                .map(item -> {
                    item.setSetmealId(id);
                    return item;
                })
                .collect(Collectors.toList());
        setmealDishService.saveBatch(collect);

    }

    /*
    更具集合id删除套餐
     */
    @Transactional
    public void removeWithDish(List<String> ids) {
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,ids);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,1); // 1 标识在售状态  不能删除
        int count = this.count(setmealLambdaQueryWrapper);
        if(count > 0){
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        this.removeByIds(ids);
//        setmealDishService.
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        setmealDishService.remove(setmealDishLambdaQueryWrapper);
    }

    @Override
    public void updateWithStatus(int status, List<String> ids) {
        LambdaUpdateWrapper<Setmeal> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Setmeal::getId,ids);
        updateWrapper.set(Setmeal::getStatus,status);
        this.update(updateWrapper);
    }
}




