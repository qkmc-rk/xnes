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
	 * 完成后
	 * 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exp)
			throws Exception {
		
	}

	/**
	 * 
	 * 执行controller后
	 * 
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object obj, ModelAndView mdv)
			throws Exception {
	}

	/**
	 * 
	 * 执行controller前
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
		System.out.println("[LOG]开始拦截/task");
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		System.out.println("[LOG]获取session" + user + "  " + user.getId());
		if(user != null && user.getId() != null)
			return true;
		else {
			return false;
		}
	}
}
