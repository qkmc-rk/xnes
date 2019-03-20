package com.rk.xnes;

import com.rk.xnes.dao.UserDao;
import com.rk.xnes.dao.impl.UserDaoImpl;
import com.rk.xnes.service.PasswordAssistant;
import com.sun.media.jfxmedia.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XnesApplicationTests {

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordAssistant passwordAssistant;

    @Test
    public void contextLoads() {
    }


    @Test
    public void m(){
        System.out.println("hahahha:::"  + userDao.selectById(200031));
    }

    @Test
    public void testMailComponent(){

        System.out.println(passwordAssistant);

    }

}
