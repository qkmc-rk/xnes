package com.rk.xnes.dao;

import com.rk.xnes.entity.Admin;

/**
 * 
 * @author Mrruan
 *
 */
public interface AdminDao {
	
	/**
	 * 管理员dao
	 * 提供增删改查 
	 * 其中查可以通过id或者通过userid
	 * 
	 */
	
	Admin selectById(Integer id);
	Admin selectByUserId(Integer userId);
	
	/**
	 * 增加
	 * @return 插入记录条数,插入失败返回-1
	 */
	Integer insert(Admin admin);
	
	/**
	 * 删除通过userid或者id
	 */
	Integer deleteById(Integer id);
	Integer deleteByUserId(Integer userId);
	
	/**
	 * 更新
	 * 其中需要更新的字段其实只有一个,state.
	 */
	Integer update(Admin admin);
}













