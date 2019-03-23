package com.rk.xnes;

import com.rk.xnes.dao.UserDao;
import com.rk.xnes.dao.impl.UserDaoImpl;
import com.rk.xnes.service.PasswordAssistant;
import com.rk.xnes.util.QiNiuFileUtil;
import com.sun.media.jfxmedia.logging.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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

    @Test
    public void testQiniu(){
        try {
            InputStream inputStream = new FileInputStream(new File("/Users/ruan/11.png"));
            QiNiuFileUtil.uploadToQiNiu(inputStream,"11.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
