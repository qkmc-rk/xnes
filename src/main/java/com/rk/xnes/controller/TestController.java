package com.rk.xnes.controller;

import com.rk.xnes.dao.impl.UserDaoImpl;
import com.rk.xnes.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    UserDaoImpl userDaoImpl;

    @GetMapping("/hello")
    public String hello(){
        User user = new User();
        user.setAccount("111");
        user.setOldpassword("22");
        user.setPassword("xxss");
        user.setStuid(1234);
        userDaoImpl.add(user);
        System.out.print("xxxx" + "::" + userDaoImpl);
        return "hello";

    }

}
