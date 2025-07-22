package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许的域名
        config.addAllowedOrigin("http://localhost:8081");
        
        // 允许的请求头
        config.addAllowedHeader("*");
        
        // 允许的HTTP方法
        config.addAllowedMethod("*");
        
        // 允许发送Cookie
        config.setAllowCredentials(true);
        
        // 对所有接口应用CORS配置
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
} 