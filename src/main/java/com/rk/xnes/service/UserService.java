package com.rk.xnes.service;

import com.rk.xnes.entity.User;
import com.rk.xnes.entity.UserCertif;
import com.rk.xnes.entity.UserPortrait;
import com.rk.xnes.entity.UserPrimInfo;
import com.rk.xnes.entity.UserQuestion;
import com.rk.xnes.entity.UserToken;

public interface UserService {

	/**
	 * 用户登录,输入一个用户名一个密码,返回登录是否成功
	 * @return 是否登录成功
	 */
	User login(String account, String password);
	
	/**
	 * 获取用户的基础信息
	 * @param userId
	 * @return
	 */
	UserPrimInfo getUserPrimInfo(Integer userId);
	
	/**
	 * 注册功能的实现
	 * @param user 用户注册信息
	 * @return 注册是否成功
	 */
	boolean register(User user, UserPrimInfo userPrimInfo, UserQuestion userQuestion);
	
	UserCertif getUserCertif(Integer userid);
	UserPortrait getUserPortrait(Integer userid);
	UserQuestion getUserQuestion(Integer userid);
	UserToken getUserToken(Integer userid);
	User getUser(Integer userid);
	User getUserByStuid(Integer stuid);
	
	Integer updateUserPrimInfo(UserPrimInfo userPrimInfo);
	Integer updateUser(User user);
	Integer updateUserQuestion(UserQuestion userQuestion);
	
}
