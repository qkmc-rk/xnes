package com.rk.xnes.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rk.xnes.entity.HelpInfo;
import com.rk.xnes.entity.HelpState;
import com.rk.xnes.entity.User;
import com.rk.xnes.service.TaskService;
import com.rk.xnes.service.UserService;
import com.rk.xnes.util.JsonResult;

/**
 * ���controller��������4��ģ�������
 *
 * <p>������Ϣ:publish
 * <p>�ҵķ���:ɾ��,�����ͽ�,����ʣ��ʱ�� <s>�鿴�ҵķ�������ϸ��Ϣ</s>
 * <p>�ѽ�����:����ѽ�����
 * <p>���·���:��������  <s>�鿴����</s>
 * 
 * �ò���������Ҫ�û���¼���ܷ���,���Ҳ���������Ҫʹ�ù�����,����
 * @author Mrruan
 *
 */
@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	TaskService taskService;
	
	@Autowired
	UserService userService;
	
	/**
	 * ������Ϣ,������,���سɹ�
	 * @param userid �û�id
	 * @param title ��������
	 * @param timeout ��ʱʱ��
	 * @param tip ��ע
	 * @param reward �ͽ�,Ԫ��λ
	 * @param content ��Ҫ����
	 * @return JSON�ַ���
	 */
	@RequestMapping(value="/publish",method=RequestMethod.POST)
	@ResponseBody
	public String publish(@RequestParam("userid") Integer userid,
			@RequestParam("title") String title,
			@RequestParam("timeout") String timeout,
			@RequestParam("tip") String tip,
			@RequestParam("reward") Integer reward,
			@RequestParam("content") String content) {
		System.out.println("[LOG]����/task/publish");
		System.out.println("[LOG]content:" + content);
		//��һ��,ǰ̨������֤..
		//......
		if(userid == null ) return JsonResult.RS_FALSE;
		if(content == null) return JsonResult.RS_FALSE;
		if(title == null) return JsonResult.RS_FALSE;
		if(timeout == null) return JsonResult.RS_FALSE;
		if(tip == null) return JsonResult.RS_FALSE;
		if(reward == null) return JsonResult.RS_FALSE;
		
		//�ж�userid�ĺ�����//.....������֤��ĺ÷���
		if(userService.getUser(userid) == null) return JsonResult.RS_FALSE;
		
		//��װ����
		HelpInfo helpInfo = new HelpInfo();
		helpInfo.setUserid(userid);
		helpInfo.setTitle(title);
		helpInfo.setTip(tip);
		helpInfo.setReward(reward);
		helpInfo.setContent(content);
		helpInfo.setCrettime(new Timestamp(new Date().getTime()));//��ǰʱ��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(timeout);
		} catch (ParseException e) {
			System.out.println("[LOG]ʱ��ת���쳣!");
			e.printStackTrace();
			return JsonResult.RS_FALSE;
		}
		helpInfo.setTimeout(date.getTime());
		
		//��info�浽���ݿ�,Ȼ�����infostate�е�����
		
		if(taskService.saveOneInfo(helpInfo) > 0) {
			System.out.println("[LOG]���񷢲��ɹ�");
			return JsonResult.RS_TRUE;
		}
		else
			return JsonResult.RS_FALSE;
	}
	
	/**
	 * <p>��session���õ�userid,Ȼ��ͨ��infoid�ҵ�info,�ж����userid��ͬ,�ͽ�info�ӱ���ɾ��,��ɾ��state.
	 * <p>��Ϊstate�������,������ɾstate,��ɾ��info
	 * @param session 
	 * @param infoid 
	 * @return <b>true</b> or <b>false</b>
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public String deleteMypublish(HttpSession session,
			@RequestParam("infoid")Integer infoid){
		//��ȡuserid;
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		//�п�
		if(infoid == null)return JsonResult.RS_FALSE;
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		if(helpInfo == null) return JsonResult.RS_FALSE;
		//�ж��û���helpinfo
		if(helpInfo.getUserid().intValue() == userid.intValue()) {
			if(taskService.deleteHelpInfoByInfoId(infoid))
				return JsonResult.RS_TRUE;
			else
				return JsonResult.RS_FALSE;
		}else {
			return JsonResult.RS_FALSE;
		}
		
	}
	
	/**
	 * �Ľ���
	 * @param session
	 * @param infoid
	 * @param reward
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/changereward",method=RequestMethod.POST)
	public String changeReward(HttpSession session,
			@RequestParam("infoid")Integer infoid,
			@RequestParam("reward")Integer reward) {
		//��ȡuserid;
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		//��ȡhelpinfo
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		//�ж��Ƿ��Ǻϵ�¼�ĵ�ǰ�û�
		if(userid.intValue() == helpInfo.getUserid().intValue()) {
			//�Ǻ�,����reward,Ȼ�󱣴浽���ݿ�,������.
			if(reward == null) return JsonResult.RS_FALSE;
			helpInfo.setReward(reward);
			if(taskService.UpdateHelpInfo(helpInfo)) return JsonResult.RS_TRUE;
			else return JsonResult.RS_FALSE;
		}else {
			//���Ǻ�,���ش���
			return JsonResult.RS_FALSE;
		}
	}
	
	/**
	 * �Ľ�ֹ����
	 * @param session
	 * @param infoid
	 * @param timeout
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/changetimeout",method=RequestMethod.POST)
	public String changeTimeout(HttpSession session,Integer infoid,String timeout) {
		//��ȡuserid;
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		//��ȡhelpinfo
		Long l = null;
		try {
			l = new SimpleDateFormat("yyyy-MM-dd").parse(timeout).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		if(userid.intValue() == helpInfo.getUserid().intValue()) {
			if(timeout == null) return JsonResult.RS_FALSE;
			helpInfo.setTimeout(l);
			if(taskService.UpdateHelpInfo(helpInfo)) return JsonResult.RS_TRUE;
			else return JsonResult.RS_FALSE;
		}else return JsonResult.RS_FALSE;
	}
	
	/**
	 * �������
	 * @param session
	 * @param infoid
	 * @return true/false
	 */
	@ResponseBody
	@RequestMapping(value="/finish",method=RequestMethod.POST)
	public String finishWork(HttpSession session,
			@RequestParam("infoid")Integer infoid) {
		//��ȡuserid;
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		
		//��ȡhelpinfo
		//HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		HelpState helpState = taskService.findByInfoId(infoid);
		if(helpState.getReceiverid() == userid.intValue()) {
			helpState.setAchieved(1);
			if(taskService.UpdateHelpState(helpState)) return JsonResult.RS_TRUE;
			else return  JsonResult.RS_FALSE;
		}else {
			return  JsonResult.RS_FALSE;
		}
	}
	
	/**
	 * �û�����ĳ������.
	 * @param session
	 * @param infoid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/receive"/*,method=RequestMethod.POST*/)
	public String receive(HttpSession session,
			@RequestParam("infoid")Integer infoid) {
		System.out.println("[LOG] /receive" + "   infoid:" + infoid);
		//��ȡuserid;
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		//�Լ��������ܽ���
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		if(helpInfo.getUserid().intValue()  == userid.intValue()) return  JsonResult.RS_FALSE;
		System.out.println("[LOG] �û��Լ�����������:" + (helpInfo.getUserid().intValue()  == userid.intValue()));
		//��ȡhelpinfo
		//HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		HelpState helpState = taskService.findByInfoId(infoid);
		helpState.setReceived(1);
		helpState.setReceiverid(userid);
		System.out.println(helpState);
		if(taskService.UpdateHelpState(helpState))
			return JsonResult.RS_TRUE;
		else 
			return  JsonResult.RS_FALSE;
	}
	
}



