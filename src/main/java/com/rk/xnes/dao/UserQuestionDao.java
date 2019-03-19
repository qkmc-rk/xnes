package com.rk.xnes.dao;

import com.rk.xnes.entity.UserQuestion;

public interface UserQuestionDao {

	UserQuestion selectById(Integer id);
	UserQuestion selectByUserId(Integer userId);

	
	Integer add(UserQuestion userQuestion);
	
	Integer deleteById(Integer id);
	Integer deleteByUserId(Integer userId);
	
	Integer update(UserQuestion userQuestion);
}
