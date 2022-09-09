package com.example.reggiegzjtest.filter;

import com.alibaba.fastjson.JSON;
import com.example.reggiegzjtest.common.BaseContext;
import com.example.reggiegzjtest.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        获取本次请求
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURL = request.getRequestURI();
        log.info("拦截到的请求：{}",requestURL);
        String [] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };
        boolean check = check(urls, requestURL);
        if(check){
            log.info("本次请求不需要处理:{}",requestURL);
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("employee") != null){
            log.info("用户已登录:{}",request.getSession().getAttribute("employee"));

            long id = Thread.currentThread().getId();
            Long empId = (Long) request.getSession().getAttribute("employee");
//            用户登录id放进去
            BaseContext.setCurrentId(empId);
            log.info("线程id:{}",id);
//            放行
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("user") != null){
            log.info("用户已登录:{}",request.getSession().getAttribute("user"));

            long id = Thread.currentThread().getId();
            Long userId = (Long) request.getSession().getAttribute("user");
//            用户登录id放进去
            BaseContext.setCurrentId(userId);
            log.info("线程id:{}",id);
//            放行
            filterChain.doFilter(request,response);
            return;
        }
        log.info("用户未登录");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }
    public boolean check(String [] urls,String requestURL){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURL);
            if(match == true){
                return true;
            }
        }
        return false;
    }
}
