package com.rk.xnes.dao;

import com.rk.xnes.entity.UserToken;

public interface UserTokenDao {

	UserToken selectById(Integer id);
	UserToken selectByUserId(Integer userId);

	
	Integer add(UserToken userToken);
	
	Integer deleteById(Integer id);
	Integer deleteByUserId(Integer userId);
	
	Integer update(UserToken userToken);
}
