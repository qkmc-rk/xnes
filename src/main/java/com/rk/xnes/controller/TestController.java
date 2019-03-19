package com.rk.xnes.controller;

import com.rk.xnes.dao.UserDao;
import com.rk.xnes.dao.impl.UserDaoImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/hello")
    public String hello(){

        UserDao userDao = new UserDaoImpl();
        System.out.println(userDao.selectById(200031));

        return "hello";

    }

}
