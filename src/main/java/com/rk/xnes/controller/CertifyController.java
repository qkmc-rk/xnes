
package com.rk.xnes.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.rk.xnes.entity.User;
import com.rk.xnes.service.TaskService;
import com.rk.xnes.util.JsonResult;

/** 
                   _ooOoo_ 
                  o8888888o 
                  88" . "88 
                  (| -_- |) 
                  O\  =  /O 
               ____/`---'\____ 
             .'  \\|     |//  `. 
            /  \\|||  :  |||//  \ 
           /  _||||| -:- |||||-  \ 
           |   | \\\  -  /// |   | 
           | \_|  ''\---/''  |   | 
           \  .-\__  `-`  ___/-. / 
         ___`. .'  /--.--\  `. . __ 
      ."" '<  `.___\_<|>_/___.'  >'"". 
     | | :  `- \`.;`\ _ /`;.`/ - ` : | | 
     \  \ `-.   \_ __\ /__ _/   .-` /  / 
======`-.____`-.___\_____/___.-`____.-'====== 
                   `=---=' 
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 
         ���汣��       ����BUG 
*/
/**
 * <p>һ���û�ʵ����֤��controller
 * <p>��������:�û�����ͨ���ύʵ����֤��Ϣ,Ȼ��ȴ�����Ա���
 * <p>���ͨ������Խ���task��صĲ���,������.
 * @author Mrruan
 *
 */
@Controller
@RequestMapping("/certify")
public class CertifyController {

	@Autowired
	TaskService taskService;
	
	@RequestMapping("/usercertify")
	@ResponseBody
	public String certify(@RequestParam("file") MultipartFile img,HttpSession session) throws IllegalStateException, IOException {
		User user = (User)session.getAttribute("user");
		System.out.println("��ʼ�ϴ�ʵ����Ƭ");
		
		String fileName = img.getOriginalFilename();
		fileName = fileName.hashCode() + fileName;
		
		String realPath = "/idcardimg";
		String imgPath = session.getServletContext().getRealPath(realPath);
		System.out.println("·��3:" + imgPath);
		File file = new File(imgPath,fileName);
		img.transferTo(file);
		System.out.println("�ϴ��ɹ�23");
		
		//Ȼ�󱣴浽���ݿ�.
		if(taskService.usercertification(user.getId(), fileName))
			return JsonResult.RS_TRUE;
		return JsonResult.RS_TRUE;
	}
	
	
	
	
}










