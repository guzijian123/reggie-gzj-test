package com.example.reggiegzjtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggiegzjtest.entities.Employee;
import com.example.reggiegzjtest.mapper.EmployeeMapper;
import com.example.reggiegzjtest.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
* @author 20412
* @description 针对表【employee(员工信息)】的数据库操作Service实现
* @createDate 2022-08-28 08:55:52
*/
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}




