package com.example.reggiegzjtest.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.reggiegzjtest.entities.Category;
/**
* @author 20412
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-08-30 21:13:06
*/
public interface CategoryService extends IService<Category> {
    /**
     * 更具id删除分类 ，  删除之前需要进行判断
     * @param id
     */
    public void remove(Long id);
}
