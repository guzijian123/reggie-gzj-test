package com.example.reggiegzjtest.dto;

import com.example.reggiegzjtest.entities.Setmeal;
import com.example.reggiegzjtest.entities.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;
//      菜品类型
    private String categoryName;
}
