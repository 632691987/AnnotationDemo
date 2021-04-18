package com.example.annotationdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AnnotationDemoApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(AnnotationDemoApplication.class, args);
    }

}
