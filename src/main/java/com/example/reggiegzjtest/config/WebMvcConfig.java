package com.example.reggiegzjtest.config;

import com.example.reggiegzjtest.Interceptor.MyInterceptor;
import com.example.reggiegzjtest.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {

//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new MyInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/employee/login","/employee/logout","/backend/**","/front/**");  // 排除拦截的请求
//        super.addInterceptors(registry);
//    }

    /**
     * 扩展mvc框架的消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
//        设置对象转换器，底层使用jackson将java转换成json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
//        将上面的消息转换器对象最佳到mvc框架的转换器集合中  优先级位0
        converters.add(0,messageConverter);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("静态资源开始映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        log.info("静态资源映射完毕");
    }
}
