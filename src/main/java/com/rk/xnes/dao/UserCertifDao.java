package com.rk.xnes.dao;

import java.util.List;

import com.rk.xnes.entity.UserCertif;

public interface UserCertifDao {

	//Ôö
	Integer add(UserCertif userCertif);
	
	//É¾
	Integer deleteById(Integer id);
	Integer deleteByUserId(Integer userId);
	//¸Ä
	Integer update(UserCertif userCertif);
	
	//²é
	UserCertif selectById(Integer id);
	UserCertif selectByUserId(Integer userId);

	List<UserCertif> selectAll();
}
