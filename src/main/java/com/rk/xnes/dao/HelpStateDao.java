package com.rk.xnes.dao;

import com.rk.xnes.entity.HelpState;

public interface HelpStateDao {

	//Ôö
	Integer add(HelpState helpState);
	
	//É¾
	Integer deleteById(Integer id);
	Integer deleteByInfoId(Integer infoId);
	//¸Ä
	Integer update(HelpState helpState);
	//²é
	HelpState selectById(Integer id);
	HelpState selectByInfoId(Integer infoId);
}
