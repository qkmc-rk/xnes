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

	
	//晕死,直接使用dao算了,麻烦死了
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
	 * 管理员登录
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
		//首先判断用户登录信息
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
		//首先判断用户登录信息
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
		//首先判断用户登录信息
		User user = userService.getUser(userid);
		if(user == null) return JsonResult.RS_FALSE;
		
		UserToken tokenObj = userService.getUserToken(userid);
		if(tokenObj == null || !tokenObj.getToken().equals(token))
			return JsonResult.RS_FALSE;
		
		Admin admin = adminService.findAdminByUserid(userid);
		if(admin == null) 
			return JsonResult.RS_FALSE;
		
		List<UserCertif> list = userCertifDao.selectAll();
		//封装数据
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
	 * userid2是要被通过的用户的ID,userid是管理员的userid
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
		//首先判断用户登录信息
		User user = userService.getUser(userid);
		if(user == null) return JsonResult.RS_FALSE;
		
		UserToken tokenObj = userService.getUserToken(userid);
		if(tokenObj == null || !tokenObj.getToken().equals(token))
			return JsonResult.RS_FALSE;
		
		Admin admin = adminService.findAdminByUserid(userid);
		if(admin == null) 
			return JsonResult.RS_FALSE;
		System.out.println("管理员权限验证通过...");
		
		if(adminService.passUserCertify(uid))
			return JsonResult.RS_TRUE;
		return JsonResult.RS_FALSE;
	}
	
	/**
	 * 去后台管理的主页
	 * @param token 用户的token
	 * @param userid 用户的id
	 * @return 后台管理主页
	 */
	@RequestMapping("/index")
	public String toIndex() {
		return "/backend/index";
	}
	
	/**
	 * 去到管理员的求助信息管理页面
	 * @return
	 */
	@RequestMapping("/helpinfo")
	public String toHelpinfo() {

		return "/backend/helpinfo";
	}
	
	/**
	 *去到管理员的实名管理页面
	 * @return
	 */
	@RequestMapping("/certify")
	public String toCertify() {
		return "/backend/certify";
	}
}







