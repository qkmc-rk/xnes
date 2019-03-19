package com.rk.xnes.dao;

import com.rk.xnes.entity.User;

public interface UserDao {

	//��ɾ�Ĳ�
	User selectById(Integer id);
	User selectByStuid(Integer stuId);
	User selectByAccount(String account);
	
	//��
	Integer add(User user);
	
	//ɾ
	Integer deleteById(Integer id);
	Integer deleteByStuId(Integer stuId);
	
	//��
	Integer  update(User user);
}
