package com.rk.xnes.util;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class QiNiuFileUtil {

    private static String accessKey;
    private static String secretKey;
    private static String dns;

    /**
     * 加载七牛云的配置文件
     */
    static{
        Properties properties = new Properties();
        InputStream inputStream = QiNiuFileUtil.class.getClassLoader()
                .getResourceAsStream("qiniuyun.properties");
        try {
            properties.load(inputStream);
            accessKey = properties.getProperty("accessKey");
            secretKey = properties.getProperty("secretKey");
            dns = properties.getProperty("dns");
        } catch (IOException e) {
            System.err.println("qiniu cloud load properties[name:qiniuyun.properties] in classpath failed.");
            e.printStackTrace();
        }
    }

    /**
     *
     * 上传文件到七牛云的工具
     * @param inputStream
     * @return
     */
    public static String uploadToQiNiu(InputStream inputStream,String key){

        //zone0表示华东区域，该bucket在华东
        Configuration configuration = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(configuration);
        String bucket = "public";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        //System.out.println(upToken);
        try {
            Response response = uploadManager.put(inputStream,key,upToken,null,null);
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return putRet.key;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getDns(){
        return dns;
    }
}
