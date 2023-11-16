package com.example.bigevent.config;

import com.example.bigevent.interceptors.LoginInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    LoginInterceptors loginInterceptors;

    //拦截器，检查是否已经登录，如果未登录，拦截除了注册和登录之外的请求
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器，并且要放行登录和注册请求
        registry.addInterceptor(loginInterceptors).excludePathPatterns("/user/login", "/user/register");
    }
}
