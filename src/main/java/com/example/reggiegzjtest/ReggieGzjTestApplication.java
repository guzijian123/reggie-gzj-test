package com.example.reggiegzjtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Slf4j
@ServletComponentScan  //  开启过滤器
@EnableTransactionManagement  // 开启事务的支持
@EnableCaching  // 开启spring缓存
public class ReggieGzjTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReggieGzjTestApplication.class, args);
        log.info("项目启动成功");
    }

}
