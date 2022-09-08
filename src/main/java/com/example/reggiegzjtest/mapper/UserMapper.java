package com.example.reggiegzjtest.mapper;

import com.example.reggiegzjtest.entities.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 20412
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2022-09-06 14:21:47
* @Entity com.example.reggiegzjtest.entities.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




