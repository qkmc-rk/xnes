package com.rk.xnes.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 *���Դ���file��controller
 *��Ҫ����:
 *1.�ϴ�ͼƬ
 *2.�ϴ�����(������Ƶ)
 * 
 * 
 * @author Mrruan
 *
 */
@Controller
@RequestMapping("/upload")
public class FileController {
	
	/**
	 * �ϴ�ͼƬ�Ĵ�����
	 * @param img �ϴ���ͼƬ
	 * @return �ϴ���ͼƬ��·��(�������Ŀ��·��)
	 * @throws IOException 
	 */
	@RequestMapping(value="/uploadimg",method=RequestMethod.POST)
	@ResponseBody
	public String uploadImage(@RequestParam("img") MultipartFile img,HttpSession session) throws IOException {
		//��Ҫ�пմ���
		System.out.println("��ʼ�ϴ�ͼƬ");
		if(!img.isEmpty() || img.getSize() > 0) {
			System.out.println("�ҵ��ļ�");
			//��ͼƬ�浽uploadfile��ȥ
			String fileName = img.getOriginalFilename();
			fileName = fileName.hashCode() + fileName;
			System.out.println("�ļ���" + fileName);
			//����һ���ļ�
			String realPath = "/static/uploadfile";
			String imgPath = session.getServletContext().getRealPath(realPath);
			System.out.println("·��3:" + imgPath);
			File file = new File(imgPath,fileName);
			img.transferTo(file);
			System.out.println("�ϴ��ɹ�23");
			return "{\"errno\": 0,\"data\": [\"/static/uploadfile/"+ fileName +"\"]}";
		}else {
			return "{\"errno\": -1}";
		}
	}
}
