package com.example.reggiegzjtest.service;

import com.example.reggiegzjtest.dto.SetmealDto;
import com.example.reggiegzjtest.entities.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 20412
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-09-05 17:50:14
*/
public interface SetmealService extends IService<Setmeal> {

     void saveWithDish(SetmealDto setmealDto);
     void removeWithDish(List<String> ids);

}
