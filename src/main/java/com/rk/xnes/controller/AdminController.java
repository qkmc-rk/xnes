package com.rk.xnes.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.rk.xnes.dao.HelpInfoDao;
import com.rk.xnes.dao.UserCertifDao;
import com.rk.xnes.entity.Admin;
import com.rk.xnes.entity.HelpInfo;
import com.rk.xnes.entity.User;
import com.rk.xnes.entity.UserCertif;
import com.rk.xnes.entity.UserPrimInfo;
import com.rk.xnes.entity.UserToken;
import com.rk.xnes.service.AdminService;
import com.rk.xnes.service.TaskService;
import com.rk.xnes.service.UserService;
import com.rk.xnes.util.JsonResult;
import com.rk.xnes.util.LayuiTableDataType;

@Controller
@RequestMapping("/admin")
public class AdminController {

	
	//����,ֱ��ʹ��dao����,�鷳����
	@Autowired
	HelpInfoDao helpInfoDao;
	
	@Autowired
	UserCertifDao userCertifDao;
	
	@Autowired
	UserService userService;
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	TaskService taskService;
	/**
	 * ����Ա��¼
	 * @return
	 */
	
	@ResponseBody
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public String adminlogin(HttpSession session,String account,String password) {
		
		User user = userService.login(account, password);
		if(user == null) return JsonResult.RS_FALSE;
		
		UserPrimInfo userPrimInfo = userService.getUserPrimInfo(user.getId());
		
		session.setAttribute("userPrimInfo", userPrimInfo);
		session.setAttribute("user", user);
		
		
		Admin admin = adminService.findAdminByUserid(user.getId());
		if(admin == null) return JsonResult.RS_FALSE;
		if(user != null && admin.getState() == 1) {
			UserToken userToken = userService.getUserToken(user.getId());
			return JSON.toJSONString(userToken);
		}else {
			return JsonResult.RS_FALSE;
		}
	}
	
	@ResponseBody
	@RequestMapping("/allinfo")
	public String allinfo(String token,Integer userid) {
		//�����ж��û���¼��Ϣ
		User user = userService.getUser(userid);

		if(user == null)
			return JsonResult.RS_FALSE;
		
		UserToken tokenObj = userService.getUserToken(userid);

		if(tokenObj == null || !tokenObj.getToken().equals(token))
			return JsonResult.RS_FALSE;
		
		Admin admin = adminService.findAdminByUserid(userid);

		if(admin == null) 
			return JsonResult.RS_FALSE;
		
		List<HelpInfo> list = helpInfoDao.selectAll();
		if(list != null) {
			LayuiTableDataType<HelpInfo> ltdt = new LayuiTableDataType<>();
			ltdt.setCount(list.size());
			ltdt.setData(list);
			
			String jsonStr = JSON.toJSONString(ltdt);
			
			System.out.println("[LOG] json data:" + jsonStr);
			return jsonStr;
		}else {
			return JsonResult.RS_FALSE;
		}
	}
	
	@ResponseBody
	@RequestMapping("/delete/helpinfo/{infoid}")
	public String deleteHelpInfo(String token,Integer userid,@PathVariable("infoid")Integer infoid) {
		//�����ж��û���¼��Ϣ
		if(infoid == null) return JsonResult.RS_FALSE;
		
		User user = userService.getUser(userid);
		if(user == null) return JsonResult.RS_FALSE;
		
		UserToken tokenObj = userService.getUserToken(userid);
		if(tokenObj == null || !tokenObj.getToken().equals(token))
			return JsonResult.RS_FALSE;
		
		Admin admin = adminService.findAdminByUserid(userid);
		if(admin == null) 
			return JsonResult.RS_FALSE;
		
		if(taskService.deleteHelpInfoByInfoId(infoid))
			return JsonResult.RS_TRUE;
		return JsonResult.RS_FALSE;
	}
	
	@ResponseBody
	@RequestMapping("/get/certify/all")
	public String allcertification(String token,Integer userid) {
		//�����ж��û���¼��Ϣ
		User user = userService.getUser(userid);
		if(user == null) return JsonResult.RS_FALSE;
		
		UserToken tokenObj = userService.getUserToken(userid);
		if(tokenObj == null || !tokenObj.getToken().equals(token))
			return JsonResult.RS_FALSE;
		
		Admin admin = adminService.findAdminByUserid(userid);
		if(admin == null) 
			return JsonResult.RS_FALSE;
		
		List<UserCertif> list = userCertifDao.selectAll();
		//��װ����
		if(list != null) {
			LayuiTableDataType<UserCertif> ltdt = new LayuiTableDataType<>();
			ltdt.setCount(list.size());
			ltdt.setData(list);
			String jsonStr = JSON.toJSONString(ltdt);
			
			System.out.println("[LOG] json data:" + jsonStr);
			return jsonStr;
		}else {
			return JsonResult.RS_FALSE;
		}
		
	}
	
	/**
	 * userid2��Ҫ��ͨ�����û���ID,userid�ǹ���Ա��userid
	 * @param userid2
	 * @param userid
	 * @param token
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/passcertify/{uid}")
	public String allcertification(@PathVariable("uid")Integer uid,Integer userid,String token) {
		System.out.println("[LOG] params from user:" + uid + "  " + userid + "  " + token);
		if(uid == null) return JsonResult.RS_FALSE;
		//�����ж��û���¼��Ϣ
		User user = userService.getUser(userid);
		if(user == null) return JsonResult.RS_FALSE;
		
		UserToken tokenObj = userService.getUserToken(userid);
		if(tokenObj == null || !tokenObj.getToken().equals(token))
			return JsonResult.RS_FALSE;
		
		Admin admin = adminService.findAdminByUserid(userid);
		if(admin == null) 
			return JsonResult.RS_FALSE;
		System.out.println("����ԱȨ����֤ͨ��...");
		
		if(adminService.passUserCertify(uid))
			return JsonResult.RS_TRUE;
		return JsonResult.RS_FALSE;
	}
	
	/**
	 * ȥ��̨�������ҳ
	 * @param token �û���token
	 * @param userid �û���id
	 * @return ��̨������ҳ
	 */
	@RequestMapping("/index")
	public String toIndex() {
		return "/backend/index";
	}
	
	/**
	 * ȥ������Ա��������Ϣ����ҳ��
	 * @return
	 */
	@RequestMapping("/helpinfo")
	public String toHelpinfo() {

		return "/backend/helpinfo";
	}
	
	/**
	 *ȥ������Ա��ʵ������ҳ��
	 * @return
	 */
	@RequestMapping("/certify")
	public String toCertify() {
		return "/backend/certify";
	}
}







