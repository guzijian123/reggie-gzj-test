
package com.example.reggiegzjtest.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggiegzjtest.entities.Category;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 20412
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-08-30 21:13:06
* @Entity com/example/reggiegzjtest.entities.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




