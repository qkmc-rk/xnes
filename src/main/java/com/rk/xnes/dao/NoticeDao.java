package com.rk.xnes.dao;

import java.util.List;

import com.rk.xnes.entity.Notice;

public interface NoticeDao {

	//Ôö
	Integer add(Notice notice);
	
	//É¾
	Integer deleteById(Integer id);
	
	//¸Ä
	Integer update(Notice notice);
	
	//²é
	Notice selectById(Integer id);
	List<Notice> selectAll();
	List<Notice> selectByCreatorId(Integer creatorId);
	
}
