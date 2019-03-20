
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
         佛祖保佑       永无BUG 
*/
/**
 * <p>一个用户实名认证的controller
 * <p>内容在于:用户可以通过提交实名认证信息,然后等待管理员审核
 * <p>审核通过则可以进行task相关的操作,否则不能.
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
		System.out.println("开始上传实名照片");
		
		String fileName = img.getOriginalFilename();
		fileName = fileName.hashCode() + fileName;
		
		String realPath = "/idcardimg";
		String imgPath = session.getServletContext().getRealPath(realPath);
		System.out.println("路径3:" + imgPath);
		File file = new File(imgPath,fileName);
		img.transferTo(file);
		System.out.println("上传成功23");
		
		//然后保存到数据库.
		if(taskService.usercertification(user.getId(), fileName))
			return JsonResult.RS_TRUE;
		return JsonResult.RS_TRUE;
	}
	
	
	
	
}










