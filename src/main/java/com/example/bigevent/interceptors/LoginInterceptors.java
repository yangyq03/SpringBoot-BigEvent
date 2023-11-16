package com.example.bigevent.interceptors;

import com.example.bigevent.Utils.JwtUtil;
import com.example.bigevent.Utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

//登录拦截器，在未登录时拦截除了登录和注册以外的所有请求
@Component
public class LoginInterceptors implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //令牌验证
        String token = request.getHeader("Authorization");
        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //把业务数据存储到ThreadLocal中
            ThreadLocalUtil.set(claims);
            //没有抛异常说明token验证成功，放回true，放行
            return true;
        } catch (Exception e) {
            //设置状态码
            response.setStatus(401);
            //拦截
            return false;
        }
    }
}
