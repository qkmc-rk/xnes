package com.rk.xnes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.rk.xnes")
//@ComponentScan(basePackages = "com.rk.xnes.controller")
public class XnesApplication {

    public static void main(String[] args) {
        SpringApplication.run(XnesApplication.class, args);
    }

}
