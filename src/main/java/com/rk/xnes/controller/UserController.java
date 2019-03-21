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
 * ��¼,ע��,�һ�����,����ҳ,��������Ϣҳ��,���ҵķ���ҳ��,���ҵ�����,��ȥ������ҳ��
 * ����������ҳ��
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
	 * �û���¼
	 * @param account �˺�
	 * @param password ����
	 * @param session session
	 * @return �Ƿ��¼�ɹ�
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public String login(@RequestParam("account")String account,@RequestParam("password")String password,HttpSession session) {
		//�пյȴ���
		if(account == null || account.equals("") || password == null || password.equals(""))
			return JsonResult.RS_FALSE;
		//��¼
		System.out.println("[LOG] account:" + account + "   password:" + password);
		User user = userService.login(account, password);
		if(user != null) {
			//��¼�ɹ�Ҫ����session
			session.setAttribute("user", user);
			return JsonResult.RS_TRUE;
		}
		else
			return JsonResult.RS_FALSE;
	}
	
	
	/**
	 * ע����¼
	 * @param session
	 * @return ע����¼ҳ��
	 */
	@RequestMapping("/loginoff")
	public String loginoff(HttpSession session) {
		session.removeAttribute("user");
		return "/frontend/loginoff";
	}
	/**
	 * ��������ҳ,������ҳ�кܶർ������
	 * @param session session�д洢�źܶ����,��jsp�п���ֱ�ӵ���.
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
			mdv.addObject("msg", "�û�״̬�쳣,�����µ�¼!");
			mdv.setViewName("error");
		}
		return mdv;
	}
	
	/**
	 * ������Ϣ����
	 * @param session �洢������Ϣ����
	 * @return center view
	 */
	@RequestMapping("/center")
	public ModelAndView center(HttpSession session) {
		ModelAndView mdv = new ModelAndView();
		//����user��id��ȡuser��priminfo,question info,ʵʵ����Ϣ,(ͷ����Ϣ ��),token��Ϣ.
		User user = (User)session.getAttribute("user");
		//�жϵ�¼״̬��
		if(user == null) {
			mdv.addObject("msg", "�û�δ��¼!!");
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
		//2019321  ����
		modelMap.put("user", user);
		
		mdv.addAllObjects(modelMap);
		mdv.setViewName("/frontend/center");
		return mdv;
	}
	
	/**
	 * ȥ���񷢲�ҳ��
	 * @param session session�е�user��userid���ڻ�ȡ�û��Ļ�����Ϣ
	 * @return ModelAndView publishҳ��
	 */
	@RequestMapping("/publish")
	public ModelAndView publish(HttpSession session) {
		System.out.println("[LOG][SESSION] content:" + session.toString());
		System.out.println("[LOG][SESSION] list:" + session.getAttribute("user"));
		User user = (User)session.getAttribute("user");
		UserPrimInfo userPrimInfo = userService.getUserPrimInfo(user.getId());
		ModelAndView mdv = new ModelAndView();
		//�ж��û��Ƿ����ʵ����֤,��û����ת������ҳ��
		//.......
		mdv.addObject("user", user);
		mdv.addObject("userPrimInfo", userPrimInfo);
		mdv.setViewName("/frontend/publish");
		return mdv;
	}
	
	/**
	 * ��ת�ҵķ���ҳ��,���Կ����ҷ���������
	 * @param session
	 * @return mypublish view
	 */
	@RequestMapping("/mypublish")
	public ModelAndView mypublish(HttpSession session) {
		ModelAndView mdv = new ModelAndView();
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		//��ȡuserid��Ӧ�ĵ�helpinfo��state
		List<HelpInfo> list = taskService.findByUserId(userid);
		List<HelpState> listState = new ArrayList<>();
		if(list != null) {
			mdv.addObject("helpInfoList", list);
			//��ȡ��Ӧ��state.
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
		//��helpinfo��list����model��,��user���벢����ҳ��.
		return mdv;
	}
	
	/**
	 * 
	 * ת����������ҳ��
	 * @param session
	 * @return ����ҳ��
	 */
	@RequestMapping("/works")
	public ModelAndView works(HttpSession session) {
		ModelAndView mdv = new ModelAndView();
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		//��Ҫʲô����?primInfo,Ȼ��stateΪδ���ӵ�����
		UserPrimInfo userPrimInfo = userService.getUserPrimInfo(userid);
		List<HelpInfo> list = null;
		List<HelpInfo> listafter = new ArrayList<>();
		if(userPrimInfo != null) {
			mdv.addObject("userPrimInfo", userPrimInfo);
			//Ȼ���ȡ���е�work.
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
	 * ת���ҽ��ܵ������ҳ��
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
						//��ȡstate
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
	 * ������work_detail,���⻹��һ���ҵ��������ϸҳ��,��������Լ���,�϶��ǲ��ܽ��ܵ�!���ܽ����Լ�������
	 * @param session
	 * @param infoid
	 * @return ������work_detailҳ��
	 */
	@RequestMapping("/work_detail")
	public ModelAndView work_detail(HttpSession session,
			@RequestParam("infoid")Integer infoid){
		ModelAndView mdv = new ModelAndView();
		User user = (User)session.getAttribute("user");
		UserPrimInfo userPrimInfo = userService.getUserPrimInfo(user.getId());
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		HelpState helpState = taskService.findByInfoId(helpInfo.getId());
		
		Integer userid2 = helpInfo.getUserid();  //���Ƿ����ߵ�id
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
		//��ȡuserid
		Integer userid = user.getId();
		
		//��ȡinfo,�ж��Ƿ��ǵ�ǰ��¼�û�
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		if(helpInfo != null && helpInfo.getUserid().intValue() == userid.intValue()) {
			HelpState helpState = taskService.findByInfoId(helpInfo.getId());
			UserPrimInfo userPrimInfo = userService.getUserPrimInfo(user.getId());
			
			//��ֹ����
			mdv.addObject("helpInfo",helpInfo);
			mdv.addObject("helpState",helpState);
			mdv.addObject("user",user);
			mdv.addObject("userPrimInfo",userPrimInfo);
			mdv.setViewName("/frontend/my_detail");
		}else {
			//�Ƿ�����
			mdv.addObject("msg", "��ΪʲôҪ�Ƿ�����?!�ܺ�����???");
			mdv.setViewName("/error");
		}
		
		return mdv;
		
	}
	
	@RequestMapping("/mywork_detail")
	public ModelAndView mywork_detail(HttpSession session,Integer infoid) {
		ModelAndView mdv = new ModelAndView();
		User user = (User)session.getAttribute("user");
		//��ȡuserid
		
		//��ȡinfo,�ж��Ƿ��ǵ�ǰ��¼�û�
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		if(helpInfo != null) {
			HelpState helpState = taskService.findByInfoId(helpInfo.getId());
			UserPrimInfo userPrimInfo = userService.getUserPrimInfo(user.getId());
			//��ֹ����
			
			Integer publisherId = helpInfo.getUserid();
			UserPrimInfo publisher = userService.getUserPrimInfo(publisherId);
			
			mdv.addObject("publisher", publisher);
			mdv.addObject("helpInfo",helpInfo);
			mdv.addObject("helpState",helpState);
			mdv.addObject("user",user);
			mdv.addObject("userPrimInfo",userPrimInfo);
			mdv.setViewName("/frontend/mywork_detail");
		}else {
			//�Ƿ�����
			mdv.addObject("msg", "��ΪʲôҪ�Ƿ�����?!�ܺ�����???");
			mdv.setViewName("/error");
		}
		return mdv;
	}
	
	/**
	 * дһ��ע�Ṧ��
	 */
	/**
	 * ע�Ṧ��
	 * @param firstname
	 * @param lastname
	 * @param stuid
	 * @param account
	 * @param password
	 * @return ע���Ƿ�ɹ�
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
		System.out.println("ע��");
		
		//�����п�
		if(firstname == null || firstname.equals("") || lastname == null || lastname.equals("") || stuid == null || account == null || account.equals("") || password == null || password.equals(""))
			return JsonResult.RS_FALSE;
		if(usermail == null || question1.equals("") || answer1 == null || 
				question2.equals("") || answer2 == null || question3 == null || 
				answer3.equals("") || question1 == null || usermail.equals("") || answer1.equals("") || 
				question2 == null || answer2.equals("") || question3 == null || answer3 == null)
			return JsonResult.RS_FALSE;
		//��ע��,Ȼ������priminfo��¼,portrait��¼,token��¼,question��¼,certif��¼
		
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
		//��ȥ֮��Ҫ����prim��question��userid
		if(userService.register(user,userPrimInfo,userQuestion))
			return JsonResult.RS_TRUE;
		return JsonResult.RS_FALSE;
	}
	
	
	/**
	 * �����û��Ļ�����Ϣ
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
	 * @return �Ƿ񱣴�ɹ�
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
		//�п�
		if(userid == null) return JsonResult.RS_FALSE;
		
		//��װ���ֶ�����Ϣ
		//User user = null;
		UserPrimInfo userPrimInfo = null;
		
		//�ȴ����ݿ��ȡ,�ڸ�����Ϣ,Ȼ�󱣴�
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
		//�������ݿ����
		UserQuestion userQuestion = null;
		User user = null;
		//�����ݿ��ȡ����
		user = userService.getUser(userid);
		userQuestion = userService.getUserQuestion(userid);
		//������Ҫ���µ��ֶ�
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
	 * ȥʵ����֤ҳ��
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
	 * ͨ��ѧ�Ż�ȡ���û����ܱ�����
	 * @param stuid ѧ��,ע��ʱ��ʹ�õ�
	 * @return ���������JSON�ַ���
	 */
	@RequestMapping(value="/getq",method=RequestMethod.POST)
	@ResponseBody
	public String getQuestion(@RequestParam("stuid")Integer stuid) {
		//ͨ��stuid�ҵ�user
		User user = userService.getUserByStuid(stuid);
		//ͨ��userid�ҵ�question
		UserQuestion userQuestion = userService.getUserQuestion(user.getId());
		userQuestion.setAnswer1("");
		userQuestion.setAnswer2("");
		userQuestion.setAnswer3("");
		userQuestion.setId(null);
		userQuestion.setUserid(null);
		//��questionת��Json
		String JSON_userQuestion = JSON.toJSONString(userQuestion);
		System.out.println("[LOG][JSON_QUESTION]" + JSON_userQuestion);
		//����ǰ̨
		return JSON_userQuestion;
	}
	
	@RequestMapping(value = "/findpassword", method=RequestMethod.POST)
	@ResponseBody
	public String findpassword(@RequestParam("answer1") String answer1,
			@RequestParam("answer2") String answer2,
			@RequestParam("answer3") String answer3,
			@RequestParam("stuid") Integer stuid) {
		System.out.println("[LOG] params:" + answer1 + " " + answer2 + " " + answer3 + "  " + stuid);
		//���Ȱ���stuid�ҵ�user
		User user = userService.getUserByStuid(stuid);
		//ͨ��user�ҵ������ܱ�����
		UserQuestion userQuestion = userService.getUserQuestion(user.getId());
		//�ж������ܱ������Ƿ�ش���ȷ
		if(userQuestion.getAnswer1().equals(answer1) &&
				userQuestion.getAnswer2().equals(answer2) &&
				userQuestion.getAnswer3().equals(answer3)) {
			System.out.println("[LOG]�ܱ���֤ͨ��");
			//��ȷ,�����������������������ݿ�
			String newPsd = RandomPsd.getRadomPassword();
			user.setOldpassword(user.getPassword());
			user.setPassword(newPsd);
			if(userService.updateUser(user) > 0) {
				System.out.println("[LOG]�������óɹ�");
				//Ȼ�����ʼ�֪ͨ�û���������ɹ�,����������
				UserPrimInfo userPrimInfo = userService.getUserPrimInfo(user.getId());
				System.out.println("[LOG]��ʼ�����ʼ�");
				passwordAssistant.passwordToMailWithInlineResource(userPrimInfo.getUsermail(), newPsd, userPrimInfo.getNeckname(), user.getOldpassword());
				System.out.println("[LOG]���������ʼ�");
				//������ȷ
				return JsonResult.RS_TRUE;
			}else {
				System.out.println("[LOG]�������ʧ��");
				return JsonResult.RS_FALSE;
			}
			
		}else {
			System.out.println("[LOG]�ܱ���֤ʧ��");
			//����,���ش���.
			return JsonResult.RS_FALSE;
		}
	}
}
















