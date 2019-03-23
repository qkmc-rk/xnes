package com.rk.xnes.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpSession;

import com.rk.xnes.util.QiNiuFileUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 *用以处理file的controller
 *主要功能:
 *1.上传图片
 *2.上传附件(多是视频)
 * 
 * 
 * @author Mrruan
 *
 */
@Controller
@RequestMapping("/upload")
public class FileController {
	
	/**
	 * 上传图片的处理方法
	 * @param img 上传的图片
	 * @return 上传的图片的路径(相对于项目根路径)
	 * @throws IOException 
	 */
	@RequestMapping(value="/uploadimg",method=RequestMethod.POST)
	@ResponseBody
	public String uploadImage(@RequestParam("img") MultipartFile img,HttpSession session) throws IOException {
		//需要判空处理
		System.out.println("开始上传图片");
		if(!img.isEmpty() || img.getSize() > 0) {
			System.out.println("找到文件");
			//将图片存到uploadfile中去
			String fileName = img.getOriginalFilename();
			InputStream inputStream  = img.getInputStream();
			String key = QiNiuFileUtil.uploadToQiNiu(inputStream,fileName);
			String dns = QiNiuFileUtil.getDns();
			System.out.println("{\"errno\": 0,\"data\": [\" " + dns + "/" + key + "\"]}");
			return "{\"errno\": 0,\"data\": [\" " + dns + "/" + key + "\"]}";
		}else {
			return "{\"errno\": -1}";
		}
	}
}
