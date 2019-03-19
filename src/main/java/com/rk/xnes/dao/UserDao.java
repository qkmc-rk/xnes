package com.rk.xnes.dao;

import com.rk.xnes.entity.User;

public interface UserDao {

	//增删改查
	User selectById(Integer id);
	User selectByStuid(Integer stuId);
	User selectByAccount(String account);
	
	//增
	Integer add(User user);
	
	//删
	Integer deleteById(Integer id);
	Integer deleteByStuId(Integer stuId);
	
	//改
	Integer  update(User user);
}
