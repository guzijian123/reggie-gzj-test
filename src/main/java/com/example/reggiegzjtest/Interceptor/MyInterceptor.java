package com.example.reggiegzjtest.Interceptor;

import com.alibaba.fastjson.JSON;
import com.example.reggiegzjtest.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("getRequestURI:{}",request.getRequestURL().toString());  // 打印请求路径
        String URL = request.getRequestURI();
        log.info("getHttpServletMapping:{}",request.getHttpServletMapping().getPattern());
        if(request.getSession().getAttribute("employee") != null){
            log.info("用户一登陆:{}",request.getSession().getAttribute("employee"));
            return true;
        }
        //下面这句话啥   就是看你的处理结果 还有是否返回响应
//        response.setStatus()  // 设置返回状态;
//        response.setHeader();
//        handler.notify(); // 拦截好像是
//        handler.wait();  // 阻塞

        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));  // 返回错误信息
        log.info("被拦截了：{}",URL);
        return false;
    }



}
