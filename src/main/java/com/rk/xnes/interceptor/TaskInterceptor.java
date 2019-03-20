package com.rk.xnes.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.rk.xnes.entity.User;

/**
 * 
 * @author Mrruan
 *
 */
public class TaskInterceptor implements HandlerInterceptor{

	/**
	 * ��ɺ�
	 * 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exp)
			throws Exception {
		
	}

	/**
	 * 
	 * ִ��controller��
	 * 
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mdv)
			throws Exception {
	}

	/**
	 * 
	 * ִ��controllerǰ
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		System.out.println("[LOG]��ʼ����/task");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		System.out.println("[LOG]��ȡsession" + user + "  " + user.getId());
		if(user != null && user.getId() != null)
			return true;
		else {
			return false;
		}
	}
}
