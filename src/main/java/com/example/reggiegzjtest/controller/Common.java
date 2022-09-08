package com.example.reggiegzjtest.controller;

import com.example.reggiegzjtest.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class Common {

    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    public R<String> upload(@RequestPart("file") MultipartFile file){
        log.info(file.toString());
//        file是临时文件  存在tomcat服务器上
        String originalFilename = file.getOriginalFilename();
//        从最后一个.开始截取字符
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;
//        防止文件目录不存在
        File dir = new File(basePath);
//        存在不需要创建
        if(!dir.exists()){
//            目录不存在  需要创建
            dir.mkdirs();
        }
        try {
            file.transferTo(new File(basePath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        FileInputStream fileInputStream = null;
        ServletOutputStream outputStream = null;
        File fileName = new File(basePath + name);
        //  输入流 ，通过输入流读取文件内容
        try {
            fileInputStream = new FileInputStream(fileName);
            // 获取输出流 ， 通过输出流将文件写回浏览器，在浏览器展示图片
            outputStream = response.getOutputStream();
//        设置返回类型 图片固定写法 image/jpeg
            response.setContentType("image/jpeg");
            byte [] bytes = new byte[1024];
            int len = 0;
            while((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //  输出流 ， 通过输出流将文件写回浏览器 ， 在浏览器展示图片
    }
}
