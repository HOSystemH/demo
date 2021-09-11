package com.atguigu.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//TODO 这里需要添加注解 设置包扫描规则
@ComponentScan(basePackages ={"com.atguigu"})
public class EduApplicaiton {

    public static void main(String[] args) {
        SpringApplication.run(EduApplicaiton.class,args);
    }
}
