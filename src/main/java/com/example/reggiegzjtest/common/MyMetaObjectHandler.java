package com.example.reggiegzjtest.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义元数据对象处理器
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert....]");
        log.info(metaObject.toString());
        if(metaObject.hasSetter("createTime")){  //是否注入
            metaObject.setValue("createTime", LocalDateTime.now());
        }
        if(metaObject.hasSetter("updateTime")){
            metaObject.setValue("updateTime", LocalDateTime.now());
        }
        if(metaObject.hasSetter("createUser")){
            metaObject.setValue("createUser", BaseContext.getCurrentId());
        }
        if(metaObject.hasSetter("updateUser")){
            metaObject.setValue("updateUser", BaseContext.getCurrentId());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update....]");
        log.info(metaObject.toString());
        long id = Thread.currentThread().getId();
        log.info("线程id:{}",id);
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}
