package com.rk.xnes;

import com.rk.xnes.dao.UserDao;
import com.rk.xnes.dao.impl.UserDaoImpl;
import com.sun.media.jfxmedia.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XnesApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void m(){
        System.out.println("测试dao开始");
        UserDao userDao = new UserDaoImpl();
        userDao.selectById(200031);
        System.out.println("测试dao结束");
    }

}
