package com.example.reggiegzjtest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.reggiegzjtest.entities.AddressBook;
import com.example.reggiegzjtest.service.AddressBookService;
import com.example.reggiegzjtest.mapper.AddressBookMapper;
import org.springframework.stereotype.Service;

/**
* @author 20412
* @description 针对表【address_book(地址管理)】的数据库操作Service实现
* @createDate 2022-09-06 21:43:16
*/
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook>
    implements AddressBookService{

}




