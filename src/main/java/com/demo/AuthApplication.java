package com.demo;

import com.demo.datasource.DruidConfiguration;
import com.demo.token.filter.TokenAuthorFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@ServletComponentScan
@SpringBootApplication
@AutoConfigureAfter({DruidConfiguration.class})
@MapperScan("com.demo.**.mapper")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    // 注册filter
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        TokenAuthorFilter tokenAuthorFilter = new TokenAuthorFilter();
        registrationBean.setFilter(tokenAuthorFilter);
        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/api/*");
        registrationBean.setUrlPatterns(urlPatterns);
        return registrationBean;
    }
}
