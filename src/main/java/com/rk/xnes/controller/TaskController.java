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
 * 这个controller用来处理4个模块的内容
 *
 * <p>发布信息:publish
 * <p>我的发布:删除,更改赏金,更改剩余时间 <s>查看我的发布的详细信息</s>
 * <p>已接任务:完成已接任务
 * <p>最新发布:接收任务  <s>查看任务</s>
 * 
 * 该部分内容需要用户登录才能访问,而且部分内容需要使用过滤器,过滤
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
	 * 发布信息,发布后,返回成功
	 * @param userid 用户id
	 * @param title 发布标题
	 * @param timeout 超时时间
	 * @param tip 备注
	 * @param reward 赏金,元单位
	 * @param content 主要内容
	 * @return JSON字符串
	 */
	@RequestMapping(value="/publish",method=RequestMethod.POST)
	@ResponseBody
	public String publish(@RequestParam("userid") Integer userid,
			@RequestParam("title") String title,
			@RequestParam("timeout") String timeout,
			@RequestParam("tip") String tip,
			@RequestParam("reward") Integer reward,
			@RequestParam("content") String content) {
		System.out.println("[LOG]请求/task/publish");
		System.out.println("[LOG]content:" + content);
		//第一步,前台数据验证..
		//......
		if(userid == null ) return JsonResult.RS_FALSE;
		if(content == null) return JsonResult.RS_FALSE;
		if(title == null) return JsonResult.RS_FALSE;
		if(timeout == null) return JsonResult.RS_FALSE;
		if(tip == null) return JsonResult.RS_FALSE;
		if(reward == null) return JsonResult.RS_FALSE;
		
		//判断userid的合理性//.....数据验证真的好繁琐
		if(userService.getUser(userid) == null) return JsonResult.RS_FALSE;
		
		//封装数据
		HelpInfo helpInfo = new HelpInfo();
		helpInfo.setUserid(userid);
		helpInfo.setTitle(title);
		helpInfo.setTip(tip);
		helpInfo.setReward(reward);
		helpInfo.setContent(content);
		helpInfo.setCrettime(new Timestamp(new Date().getTime()));//当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sdf.parse(timeout);
		} catch (ParseException e) {
			System.out.println("[LOG]时间转换异常!");
			e.printStackTrace();
			return JsonResult.RS_FALSE;
		}
		helpInfo.setTimeout(date.getTime());
		
		//将info存到数据库,然后更新infostate中的内容
		
		if(taskService.saveOneInfo(helpInfo) > 0) {
			System.out.println("[LOG]任务发布成功");
			return JsonResult.RS_TRUE;
		}
		else
			return JsonResult.RS_FALSE;
	}
	
	/**
	 * <p>从session中拿到userid,然后通过infoid找到info,判断如果userid相同,就将info从表中删除,并删除state.
	 * <p>因为state中有外键,所以先删state,再删除info
	 * @param session 
	 * @param infoid 
	 * @return <b>true</b> or <b>false</b>
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public String deleteMypublish(HttpSession session,
			@RequestParam("infoid")Integer infoid){
		//获取userid;
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		//判空
		if(infoid == null)return JsonResult.RS_FALSE;
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		if(helpInfo == null) return JsonResult.RS_FALSE;
		//判断用户的helpinfo
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
	 * 改奖金
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
		//获取userid;
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		//获取helpinfo
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		//判断是否吻合登录的当前用户
		if(userid.intValue() == helpInfo.getUserid().intValue()) {
			//吻合,更改reward,然后保存到数据库,返回真.
			if(reward == null) return JsonResult.RS_FALSE;
			helpInfo.setReward(reward);
			if(taskService.UpdateHelpInfo(helpInfo)) return JsonResult.RS_TRUE;
			else return JsonResult.RS_FALSE;
		}else {
			//不吻合,返回错误
			return JsonResult.RS_FALSE;
		}
	}
	
	/**
	 * 改截止日期
	 * @param session
	 * @param infoid
	 * @param timeout
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/changetimeout",method=RequestMethod.POST)
	public String changeTimeout(HttpSession session,Integer infoid,String timeout) {
		//获取userid;
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		//获取helpinfo
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
	 * 完成任务
	 * @param session
	 * @param infoid
	 * @return true/false
	 */
	@ResponseBody
	@RequestMapping(value="/finish",method=RequestMethod.POST)
	public String finishWork(HttpSession session,
			@RequestParam("infoid")Integer infoid) {
		//获取userid;
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		
		//获取helpinfo
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
	 * 用户接受某个任务.
	 * @param session
	 * @param infoid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/receive"/*,method=RequestMethod.POST*/)
	public String receive(HttpSession session,
			@RequestParam("infoid")Integer infoid) {
		System.out.println("[LOG] /receive" + "   infoid:" + infoid);
		//获取userid;
		User user = (User)session.getAttribute("user");
		Integer userid = user.getId();
		//自己的任务不能接受
		HelpInfo helpInfo = taskService.findHelpInfoById(infoid);
		if(helpInfo.getUserid().intValue()  == userid.intValue()) return  JsonResult.RS_FALSE;
		System.out.println("[LOG] 用户自己发布的任务:" + (helpInfo.getUserid().intValue()  == userid.intValue()));
		//获取helpinfo
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



