package com.example.reggiegzjtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggiegzjtest.entities.User;
import com.example.reggiegzjtest.service.UserService;
import com.example.reggiegzjtest.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 20412
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-09-06 14:21:47
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




