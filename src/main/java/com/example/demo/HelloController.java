package com.example.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    // 在执行以下方法之前，预授权检查当前用户是否拥有'USER'角色
    @PreAuthorize("hasRole('USER')")
    public String helloWorld() {
        return "Hello World";
    }
}
