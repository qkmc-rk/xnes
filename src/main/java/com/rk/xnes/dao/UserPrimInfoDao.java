package com.rk.xnes.dao;

import com.rk.xnes.entity.UserPrimInfo;

public interface UserPrimInfoDao {

	UserPrimInfo selectById(Integer id);
	UserPrimInfo selectByUserId(Integer userId);

	
	Integer add(UserPrimInfo userPrimInfo);
	
	Integer deleteById(Integer id);
	Integer deleteByUserId(Integer userId);
	
	Integer update(UserPrimInfo userPrimInfo);
}
