package com.rk.xnes.dao;

import java.util.List;

import com.rk.xnes.entity.UserCertif;

public interface UserCertifDao {

	//��
	Integer add(UserCertif userCertif);
	
	//ɾ
	Integer deleteById(Integer id);
	Integer deleteByUserId(Integer userId);
	//��
	Integer update(UserCertif userCertif);
	
	//��
	UserCertif selectById(Integer id);
	UserCertif selectByUserId(Integer userId);

	List<UserCertif> selectAll();
}
