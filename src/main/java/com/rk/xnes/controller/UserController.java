package com.rk.xnes.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.rk.xnes.entity.HelpInfo;
import com.rk.xnes.entity.HelpState;
import com.rk.xnes.entity.User;
import com.rk.xnes.entity.UserCertif;
import com.rk.xnes.entity.UserPortrait;
import com.rk.xnes.entity.UserPrimInfo;
import com.rk.xnes.entity.UserQuestion;
import com.rk.xnes.entity.UserToken;
import com.rk.xnes.service.PasswordAssistant;
import com.rk.xnes.service.TaskService;
import com.rk.xnes.service.UserService;
import com.rk.xnes.util.JsonResult;
import com.rk.xnes.util.RandomPsd;

/**
 * 登录,注册,找回密码,到主页,到发布信息页面,到我的发布页面,到我的任务,到去接任务页面
 * 到个人中心页面
 * @author Mrruan
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordAssistant passwordAssistant;
	
	@Autowired
	TaskService taskService;
	/**
	 * 用户登录
	 * @param account 账号
	 * @param password 密码
	 * @param session session
	 * @return 是否登录成功
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public String login(@RequestParam("account")String account,@RequestParam("password")String password,HttpSession session) {
		//判空等处理
		if(account == null || account.equals("") || password == null || password.equals(""))
			return JsonResult.RS_FALSE;
		//登录
		System.out.println("[LOG] account:" + account + "   password:" + password);
		User user = userService.login(account, password);
		if(user != null) {
			//登录成功要设置session
			session.setAttribute("user", user);
			return JsonResult.RS_TRUE;
		}
		else
			return JsonResult.RS_FALSE;
	}
	
	
	/**
	 * 注销登录
	 * @param session
	 * @return 注销登录页面
	 */
	@RequestMapping("/loginoff")
	public String loginoff(HttpSession session) {
		session.removeAttribute("user");
		return "/frontend/loginoff";
	}
	/**
	 * 到个人主页,个人主页有很多导航链接
	 * @param session session中存储着很多对象,在jsp中可以直接调用.
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mdv = new ModelAndView();
		User user = (User) session.getAttribute("user");
		if(user != null) {
			UserPrimInfo userPrimInfo = userService.getUserPrimInfo(user.getId());
			mdv.addObject("user", user);
			mdv.addObject("userPrimInfo", userPrimInfo);
			mdv.setViewName("/frontend/index");
		}else {
			mdv.addObject("msg", "用户状态异常,请重新登录!");
			mdv.setViewName("error");
		}
		return mdv;
	}
	
	/**
	 * 个人信息中心
	 * @param session 存储个人信息对象
	 * @return center view
	 */
	@RequestMapping("/center")
	public ModelAndView center(HttpSession session) {
		ModelAndView mdv = new ModelAndView();
		//根据user的id获取user的priminfo,question info,实实名信息,(头像信息 略),token信息.
		User user = (User)session.getAttribute("user");
		//判断登录状态否
		if(user == null) {
			mdv.addObject("msg", "用户未登录!!");
			mdv.setViewName("error");
		}
		Integer userid = user.getId();
		UserCertif userCertif = userService.getUserCertif(userid);
		UserPortrait userPortrait = userService.getUserPortrait(userid);
		UserPrimInfo userPrimInfo = userService.getUserPrimInfo(userid);
		UserQuestion userQuestion = userService.getUserQuestion(userid);
		UserToken userToken = userService.getUserToken(userid);
		
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("userCertif", userCertif);
		modelMap.put("userPortrait", userPortrait);
		modelMap.put("userPrimInfo", userPrimInfo);
		modelMap.put("userQuestion", userQuestion);
		modelMap.put("userToken", userToken);
		//2019321  加上
		modelMap.put("user", user);
		
		mdv.addAllObjects(modelMap);
		mdv.setViewName("/frontend/center");
		return mdv;
	}
	
	/**
	 * 去任务发布页面
	 * @param session session中的user的userid用于获取用户的基础信息
	 * @return ModelAndView publish页面
	 */
	@RequestMapping("/publish")
	public ModelAndView publish(HttpSession session) {
		System.out.println("[LOG][SESSION] content:" + session.toString());
		System.out.println("[LOG][SESSION] list:" + session.getAttribute("user"));
		User user = (User)session.getAttribute("user");
		UserPrimInfo userPrimInfo = userService.getUserPrimInfo(user.getId());
		ModelAndView mdv = new ModelAndView();
		//判断用户是否进行实名认证,若没有跳转到错误页面
		//.......
		mdv.addObject("user", user);
		mdv.addObject("userPrimInfo", userPrimInfo);
		mdv.setViewName("/frontend/publish");
		return mdv;
	}
	
	/**
	 * 跳转我的发布页面,可以看到我发布的任务
	 * @param session
	 * @return mypublish view
	 */
	@RequestMapping("/mypublish")
	public ModelAndView mypublish(HttpSession session) {
		ModelAndView mdv = new ModelAndView();
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		//获取userid对应的的helpinfo和state
		List<HelpInfo> list = taskService.findByUserId(userid);
		List<HelpState> listState = new ArrayList<>();
		if(list != null) {
			mdv.addObject("helpInfoList", list);
			//获取对应的state.
			for (HelpInfo helpInfo : list) {
				HelpState hs = taskService.findByInfoId(helpInfo.getId());
				listState.add(hs);
			}
			UserPrimInfo userPrimInfo = userService.getUserPrimInfo(userid);
			mdv.addObject("helpStateList", listState);
			mdv.addObject("userPrimInfo", userPrimInfo);
			mdv.addObject("user", user);
		}
		mdv.setViewName("/frontend/mypublish");
		//将helpinfo的list放在model中,将user放入并返回页面.
		return mdv;
	}
	
	/**
	 * 
	 * 转到所有任务页面
	 * @param session
	 * @return 任务页面
	 */
	@RequestMapping("/works")
	public ModelAndView works(HttpSession session) {
		ModelAndView mdv = new ModelAndView();
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		//需要什么内容?primInfo,然后state为未被接的任务
		UserPrimInfo userPrimInfo = userService.getUserPrimInfo(userid);
		List<HelpInfo> list = null;
		List<HelpInfo> listafter = new ArrayList<>();
		if(userPrimInfo != null) {
			mdv.addObject("userPrimInfo", userPrimInfo);
			//然后获取所有的work.
			list = taskService.findAll();
			if(list != null)
				for (HelpInfo helpInfo : list) {
					if(taskService.findByInfoId(helpInfo.getId()).getReceived() != 1) {
						listafter.add(helpInfo);
					}
				}
		}
		mdv.addObject("userPrimInfo", userPrimInfo);
		mdv.addObject("user", user);
		mdv.addObject("helpInfoList", listafter);
		mdv.setViewName("/frontend/works");
		return mdv;
	}
	
	/**
	 * 转到我接受的任务的页面
	 * @param session
	 * @return
	 */
	@RequestMapping("/mywork")
	public ModelAndView mywork(HttpSession session) {
		ModelAndView mdv = new ModelAndView();
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		UserPrimInfo userPrimInfo = userService.getUserPrimInfo(userid);
		List<HelpInfo> list = null;
		List<HelpInfo> listafter = new ArrayList<>();
		List<HelpState> statelist = new ArrayList<>();
		HelpState helpState = null;
		if(userPrimInfo != null) {
			mdv.addObject("userPrimInfo", userPrimInfo);
			list = taskService.findAll();
			if(list != null)
				for (HelpInfo helpInfo : list) {
					Integer receiverid = taskService.findByInfoId(helpInfo.getId()).getReceiverid();
					if(receiverid != null && receiverid.intValue() == userid.intValue()) {
						listafter.add(helpInfo);
						//获取state
						helpState = taskService.findByInfoId(helpInfo.getId());
						statelist.add(helpState);
					}
				}
			System.out.println("[LOG][/mywork]-->helpInfoList" + listafter);
		}
		mdv.addObject("userPrimInfo", userPrimInfo);
		mdv.addObject("user", user);
		mdv.addObject("helpInfoList", listafter);
		mdv.addObject("helpStateList", statelist);
		mdv.setViewName("/frontend/mywork");
		return mdv;
	}
	
	/**
	 * 公共的work_detail,另外还有一个我的任务的详细页面,如果是我自己的,肯定是不能接受的!不能接受自己的任务
	 * @param session
	 * @param infoid
	 * @return 公共的work_detail页面
	 */
	@RequestMapping("/work_detail")
	public ModelAndView work_detail(HttpSession session,
			@RequestParam("infoid")Integer infoid){
		ModelAndView mdv = new ModelAndView();
		User user = (User)session.getAttribute("user");
		UserPrimInfo userPrimInfo = userService.getUserPrimInfo(user.getId());
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		HelpState helpState = taskService.findByInfoId(helpInfo.getId());
		
		Integer userid2 = helpInfo.getUserid();  //这是发布者的id
		UserPrimInfo publisher = userService.getUserPrimInfo(userid2);
		mdv.addObject("helpInfo",helpInfo);
		mdv.addObject("helpState",helpState);
		mdv.addObject("user",user);
		mdv.addObject("userPrimInfo",userPrimInfo);
		mdv.addObject("publisher", publisher);
		
		mdv.setViewName("/frontend/work_detail");
		return mdv;
	}
	
	@RequestMapping("/my_detail")
	public ModelAndView my_detail(HttpSession session,Integer infoid) {
		ModelAndView mdv = new ModelAndView();
		User user = (User)session.getAttribute("user");
		//获取userid
		Integer userid = user.getId();
		
		//获取info,判断是否是当前登录用户
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		if(helpInfo != null && helpInfo.getUserid().intValue() == userid.intValue()) {
			HelpState helpState = taskService.findByInfoId(helpInfo.getId());
			UserPrimInfo userPrimInfo = userService.getUserPrimInfo(user.getId());
			
			//防止数据
			mdv.addObject("helpInfo",helpInfo);
			mdv.addObject("helpState",helpState);
			mdv.addObject("user",user);
			mdv.addObject("userPrimInfo",userPrimInfo);
			mdv.setViewName("/frontend/my_detail");
		}else {
			//非法访问
			mdv.addObject("msg", "你为什么要非法访问?!很好玩吗???");
			mdv.setViewName("/error");
		}
		
		return mdv;
		
	}
	
	@RequestMapping("/mywork_detail")
	public ModelAndView mywork_detail(HttpSession session,Integer infoid) {
		ModelAndView mdv = new ModelAndView();
		User user = (User)session.getAttribute("user");
		//获取userid
		
		//获取info,判断是否是当前登录用户
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		if(helpInfo != null) {
			HelpState helpState = taskService.findByInfoId(helpInfo.getId());
			UserPrimInfo userPrimInfo = userService.getUserPrimInfo(user.getId());
			//防止数据
			
			Integer publisherId = helpInfo.getUserid();
			UserPrimInfo publisher = userService.getUserPrimInfo(publisherId);
			
			mdv.addObject("publisher", publisher);
			mdv.addObject("helpInfo",helpInfo);
			mdv.addObject("helpState",helpState);
			mdv.addObject("user",user);
			mdv.addObject("userPrimInfo",userPrimInfo);
			mdv.setViewName("/frontend/mywork_detail");
		}else {
			//非法访问
			mdv.addObject("msg", "你为什么要非法访问?!很好玩吗???");
			mdv.setViewName("/error");
		}
		return mdv;
	}
	
	/**
	 * 写一个注册功能
	 */
	/**
	 * 注册功能
	 * @param firstname
	 * @param lastname
	 * @param stuid
	 * @param account
	 * @param password
	 * @return 注册是否成功
	 */
	@RequestMapping(value="/regist",method=RequestMethod.POST)
	@ResponseBody
	public String regist(@RequestParam("firstname")String firstname,
			@RequestParam("lastname")String lastname,
			@RequestParam("stuid")Integer stuid,
			@RequestParam("account")String account,
			@RequestParam("password")String password,
			@RequestParam("usermail")String usermail,
			@RequestParam("question1")String question1,
			@RequestParam("answer1")String answer1,
			@RequestParam("question2")String question2,
			@RequestParam("answer2")String answer2,
			@RequestParam("question3")String question3,
			@RequestParam("answer3")String answer3) {
		System.out.println("注册");
		
		//首先判空
		if(firstname == null || firstname.equals("") || lastname == null || lastname.equals("") || stuid == null || account == null || account.equals("") || password == null || password.equals(""))
			return JsonResult.RS_FALSE;
		if(usermail == null || question1.equals("") || answer1 == null || 
				question2.equals("") || answer2 == null || question3 == null || 
				answer3.equals("") || question1 == null || usermail.equals("") || answer1.equals("") || 
				question2 == null || answer2.equals("") || question3 == null || answer3 == null)
			return JsonResult.RS_FALSE;
		//先注册,然后生成priminfo记录,portrait记录,token记录,question记录,certif记录
		
		User user = new User();
		UserPrimInfo userPrimInfo = new UserPrimInfo();
		UserQuestion userQuestion = new UserQuestion();
		
		user.setStuid(stuid);
		user.setAccount(account);
		user.setPassword(password);
		
		userPrimInfo.setNeckname(firstname + lastname);
		userPrimInfo.setUsermail(usermail);
		
		userQuestion.setQuestion1(question1);
		userQuestion.setAnswer1(answer1);
		userQuestion.setQuestion2(question2);
		userQuestion.setAnswer2(answer2);
		userQuestion.setQuestion3(question3);
		userQuestion.setAnswer3(answer3);
		//进去之后还要设置prim和question的userid
		if(userService.register(user,userPrimInfo,userQuestion))
			return JsonResult.RS_TRUE;
		return JsonResult.RS_FALSE;
	}
	
	
	/**
	 * 保存用户的基础信息
	 * @param neckname
	 * @param usermail
	 * @param userphone
	 * @param sex
	 * @param age
	 * @param qq
	 * @param dormnum
	 * @param dormaddr
	 * @param homeaddr
	 * @param userid
	 * @return 是否保存成功
	 */
	@RequestMapping("/saveuserprim")
	@ResponseBody
	public String saveUserPrim(@RequestParam("neckname") String neckname,
			@RequestParam("usermail") String usermail,
			@RequestParam("userphone") String userphone,
			@RequestParam("sex") String sex,
			@RequestParam("age") Integer age,
			@RequestParam("qq") String qq,
			@RequestParam("dormnum") Integer dormnum,
			@RequestParam("dormaddr") String dormaddr,
			@RequestParam("homeaddr") String homeaddr,
			@RequestParam("userid") Integer userid) {
		//判空
		if(userid == null) return JsonResult.RS_FALSE;
		
		//封装各种对象信息
		//User user = null;
		UserPrimInfo userPrimInfo = null;
		
		//先从数据库获取,在更改信息,然后保存
		//user = userService.getUser(userid);
		userPrimInfo = userService.getUserPrimInfo(userid);
		
		
		userPrimInfo.setUserid(userid);
		userPrimInfo.setNeckname(neckname);
		userPrimInfo.setUsermail(usermail);
		userPrimInfo.setUserphone(userphone);
		userPrimInfo.setSex(sex);
		userPrimInfo.setAge(age);
		userPrimInfo.setQq(qq);
		userPrimInfo.setDormnum(dormnum);
		userPrimInfo.setDormaddr(dormaddr);
		userPrimInfo.setHomeaddr(homeaddr);
		
		if(userService.updateUserPrimInfo(userPrimInfo) > 0)
			return JsonResult.RS_TRUE;
		return JsonResult.RS_FALSE;
	}
	
	@RequestMapping("/saveusersafe")
	@ResponseBody
	public String saveUserSafe(@RequestParam("password") String password,
			@RequestParam("question1") String question1,
			@RequestParam("answer1") String answer1,
			@RequestParam("question2") String question2,
			@RequestParam("answer2") String answer2,
			@RequestParam("question3") String question3,
			@RequestParam("userid") Integer userid,
			@RequestParam("answer3") String answer3) {
		if(userid == null) return JsonResult.RS_FALSE;
		//定义数据库对象
		UserQuestion userQuestion = null;
		User user = null;
		//从数据库获取对象
		user = userService.getUser(userid);
		userQuestion = userService.getUserQuestion(userid);
		//设置需要更新的字段
		user.setPassword(password);
		
		userQuestion.setQuestion1(question1);
		userQuestion.setQuestion2(question2);
		userQuestion.setQuestion3(question3);
		userQuestion.setAnswer1(answer1);
		userQuestion.setAnswer2(answer2);
		userQuestion.setAnswer3(answer3);
		
		if(userService.updateUser(user) > 0) {
			if(userService.updateUserQuestion(userQuestion) > 0) {
				return JsonResult.RS_TRUE;
			}else {
				return JsonResult.RS_FALSE;
			}
		}else
			return JsonResult.RS_FALSE;
	}
	
	/**
	 * 去实名认证页面
	 * @param session
	 * @return
	 */
	@RequestMapping("/certify")
	public ModelAndView toCertify(HttpSession session) {
		ModelAndView mdv = new ModelAndView();
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		UserPrimInfo userPrimInfo = userService.getUserPrimInfo(userid);
		mdv.addObject("user", user);
		mdv.addObject("userPrimInfo", userPrimInfo);
		mdv.setViewName("/frontend/usercertify");
		return mdv;
	}
	
	/**
	 * 通过学号获取到用户的密保问题
	 * @param stuid 学号,注册时候使用的
	 * @return 三个问题的JSON字符串
	 */
	@RequestMapping(value="/getq",method=RequestMethod.POST)
	@ResponseBody
	public String getQuestion(@RequestParam("stuid")Integer stuid) {
		//通过stuid找到user
		User user = userService.getUserByStuid(stuid);
		//通过userid找到question
		UserQuestion userQuestion = userService.getUserQuestion(user.getId());
		userQuestion.setAnswer1("");
		userQuestion.setAnswer2("");
		userQuestion.setAnswer3("");
		userQuestion.setId(null);
		userQuestion.setUserid(null);
		//将question转成Json
		String JSON_userQuestion = JSON.toJSONString(userQuestion);
		System.out.println("[LOG][JSON_QUESTION]" + JSON_userQuestion);
		//返回前台
		return JSON_userQuestion;
	}
	
	@RequestMapping(value = "/findpassword", method=RequestMethod.POST)
	@ResponseBody
	public String findpassword(@RequestParam("answer1") String answer1,
			@RequestParam("answer2") String answer2,
			@RequestParam("answer3") String answer3,
			@RequestParam("stuid") Integer stuid) {
		System.out.println("[LOG] params:" + answer1 + " " + answer2 + " " + answer3 + "  " + stuid);
		//首先按照stuid找到user
		User user = userService.getUserByStuid(stuid);
		//通过user找到三个密保问题
		UserQuestion userQuestion = userService.getUserQuestion(user.getId());
		//判断三个密保问题是否回答正确
		if(userQuestion.getAnswer1().equals(answer1) &&
				userQuestion.getAnswer2().equals(answer2) &&
				userQuestion.getAnswer3().equals(answer3)) {
			System.out.println("[LOG]密保验证通过");
			//正确,重新生成随机密码放置在数据库
			String newPsd = RandomPsd.getRadomPassword();
			user.setOldpassword(user.getPassword());
			user.setPassword(newPsd);
			if(userService.updateUser(user) > 0) {
				System.out.println("[LOG]密码重置成功");
				//然后发送邮件通知用户重置密码成功,并附上密码
				UserPrimInfo userPrimInfo = userService.getUserPrimInfo(user.getId());
				System.out.println("[LOG]开始发送邮件");
				passwordAssistant.passwordToMailWithInlineResource(userPrimInfo.getUsermail(), newPsd, userPrimInfo.getNeckname(), user.getOldpassword());
				System.out.println("[LOG]结束发送邮件");
				//返回正确
				return JsonResult.RS_TRUE;
			}else {
				System.out.println("[LOG]密码更新失败");
				return JsonResult.RS_FALSE;
			}
			
		}else {
			System.out.println("[LOG]密保验证失败");
			//错误,返回错误.
			return JsonResult.RS_FALSE;
		}
	}
}
















