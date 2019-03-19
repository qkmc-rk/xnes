package com.rk.xnes.dao;

import com.rk.xnes.entity.UserPortrait;

public interface UserPortraitDao {

	//��ɾ�Ĳ�
	
	UserPortrait selectById(Integer id);
	UserPortrait selectByUserId(Integer userId);

	
	Integer add(UserPortrait userPortrait);
	
	Integer deleteById(Integer id);
	Integer deleteByUserId(Integer userId);
	
	Integer update(UserPortrait userPortrait);
}
