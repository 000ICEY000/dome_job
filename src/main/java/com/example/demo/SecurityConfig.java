package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
/**
 * 配置安全相关的Bean
 * 该类用于配置基于Spring Security的用户身份验证和授权
 */
public class SecurityConfig {

    /**
     * 配置用户服务细节实现
     * 该方法通过使用默认的密码编码器来创建一个用户，并为其设定用户名、密码和角色
     * 它主要用于在内存中管理用户详情，是Spring Security认证过程中重要的一环
     * @return UserDetailsService 实现了UserDetailsService接口的内存用户详情管理器
     */
    @Bean
    public UserDetailsService userDetailsService() {
        // 创建并配置用户账户，使用默认的密码编码器
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("test") // 设置用户名
                .password("123456") // 设置用户密码
                .roles("USER") // 为用户分配角色
                .build();

        // 返回一个实现了UserDetailsService接口的内存用户详情管理器实例
        return new InMemoryUserDetailsManager(user);
    }

    /**
     * 配置Security过滤器链
     * 通过HttpSecurity来定义哪些URL需要进行身份验证，哪些URL可以公开访问，
     * 以及登录和登出的行为
     *
     * @param http HttpSecurity对象，用于配置Web应用的安全性
     * @return 返回配置好的SecurityFilterChain对象
     * @throws Exception 如果在配置过程中发生错误会抛出异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 配置授权请求
        http.authorizeHttpRequests((authz) ->
                // 指定"/hello"路径的请求需要经过身份验证
                authz.requestMatchers("/hello").authenticated()
                // 除上述请求外的任何请求都允许所有访问
                .anyRequest().permitAll()
        );

        // 配置表单登录
        http.formLogin((form) ->
                // 指定登录页面的URL
                form.loginPage("/login")
                // 登录成功后的默认跳转URL，并且是强制重定向
                .defaultSuccessUrl("/hello", true)
                // 登录功能允许所有访问
                .permitAll()
        );

        // 配置登出功能，允许所有访问
        http.logout((logout) -> logout.permitAll());
        // 构建并返回Security过滤器链
        return http.build();
    }

}