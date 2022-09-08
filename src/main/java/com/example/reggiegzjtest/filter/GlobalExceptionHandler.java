package com.example.reggiegzjtest.filter;

import com.example.reggiegzjtest.common.CustomException;
import com.example.reggiegzjtest.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 拦截标注了RestController注解的类 和 标注了Controller的注解的类
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@Slf4j
@ResponseBody
public class GlobalExceptionHandler {
    /**
     * 异常处理方法
     * @param exception
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception){
        log.info(exception.getMessage());
//        包含Duplicate entry 标数据库已存在key
        if(exception.getMessage().contains("Duplicate entry")){
            String[] s = exception.getMessage().split(" ");
            String msg = s[2] + "已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }
    @ExceptionHandler(CustomException.class)
    public R<String> customException(CustomException exception){
        log.error(exception.getMessage());
        return R.error(exception.getMessage());
    }
}
