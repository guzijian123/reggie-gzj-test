package com.example.reggiegzjtest.dto;

import com.example.reggiegzjtest.entities.Dish;
import com.example.reggiegzjtest.entities.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
