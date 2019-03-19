package com.rk.xnes.dao;

import com.rk.xnes.entity.HelpState;

public interface HelpStateDao {

	//��
	Integer add(HelpState helpState);
	
	//ɾ
	Integer deleteById(Integer id);
	Integer deleteByInfoId(Integer infoId);
	//��
	Integer update(HelpState helpState);
	//��
	HelpState selectById(Integer id);
	HelpState selectByInfoId(Integer infoId);
}
