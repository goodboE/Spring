package com.ko.shop.web;

import com.ko.shop.interceptor.LoginCheckInterceptor;
import com.ko.shop.interceptor.RoleCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/", "/login", "logout", "/user/add", "/sign", "/introduction",
                        "/css/**", "/*.ico", "/error"
                );

        registry.addInterceptor(new RoleCheckInterceptor())
                .order(2)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/noPermission", "/introduction");
    }



}
