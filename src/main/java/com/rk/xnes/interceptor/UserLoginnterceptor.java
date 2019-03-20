package com.rk.xnes.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserLoginnterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object obj, ModelAndView mdv)
			throws Exception {
		//判断session
		HttpSession session = req.getSession();
		if(session.getAttribute("user") == null) {
			mdv.clear();
			mdv.setViewName("/frontend/nologin");
		}
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object obj) throws Exception {
		HttpSession session = req.getSession();
		if(session.getAttribute("user") == null) {
			System.out.print("用户session原因被挡了");
			return false;
		}
		return true;
	}
}
