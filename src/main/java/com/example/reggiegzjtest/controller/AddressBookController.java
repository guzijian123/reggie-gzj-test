package com.example.reggiegzjtest.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.example.reggiegzjtest.common.BaseContext;
import com.example.reggiegzjtest.common.R;
import com.example.reggiegzjtest.entities.AddressBook;
import com.example.reggiegzjtest.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新曾地址
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}",addressBook);
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }


    /**
     * 设置默认地址
     */
    @PutMapping("/default")
    @Transactional
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        log.info("addressBook:{}",addressBook);
        LambdaUpdateWrapper<AddressBook> addressBookLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        addressBookLambdaUpdateWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        addressBookLambdaUpdateWrapper.set(AddressBook::getIsDefault,0);
        addressBookService.update(addressBookLambdaUpdateWrapper);
        addressBook.setIsDefault(1);
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }
    /**
     * 查询默认地址
     */
    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> addressBookLambdaQueryWrapper = new LambdaQueryWrapper<>();
        addressBookLambdaQueryWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        addressBookLambdaQueryWrapper.eq(AddressBook::getIsDefault,1);
        AddressBook addressBook = addressBookService.getOne(addressBookLambdaQueryWrapper);
        if(addressBook == null){
            return R.error("没有找到对应的默认地址");
        }
        return R.success(addressBook);
    }
    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook){
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId,userId);
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> userAddressList = addressBookService.list(queryWrapper);

        return R.success(userAddressList);
    }
    /**
     * 根据地址id查询地址
     */
    @GetMapping("/{id}")
    public R<AddressBook> getAddressBook(@PathVariable("id") Long id){
        AddressBook addressBook = addressBookService.getById(id);

        if(addressBook != null){
            return R.success(addressBook);
        }
        return R.error("查询失败");
    }

    /**
     * 修改地址
     */
    @PutMapping
    public R<AddressBook> updateAddressBook(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }
    /**
     * 删除地址
     */

}
