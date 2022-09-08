package com.example.reggiegzjtest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.reggiegzjtest.entities.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 20412
* @description 针对表【employee(员工信息)】的数据库操作Mapper
* @createDate 2022-08-28 08:55:52
* @Entity generator.entities.Employee
*/
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}




