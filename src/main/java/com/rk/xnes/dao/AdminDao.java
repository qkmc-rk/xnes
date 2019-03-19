package com.rk.xnes.dao;

import com.rk.xnes.entity.Admin;

/**
 * 
 * @author Mrruan
 *
 */
public interface AdminDao {
	
	/**
	 * ����Աdao
	 * �ṩ��ɾ�Ĳ� 
	 * ���в����ͨ��id����ͨ��userid
	 * 
	 */
	
	Admin selectById(Integer id);
	Admin selectByUserId(Integer userId);
	
	/**
	 * ����
	 * @return �����¼����,����ʧ�ܷ���-1
	 */
	Integer insert(Admin admin);
	
	/**
	 * ɾ��ͨ��userid����id
	 */
	Integer deleteById(Integer id);
	Integer deleteByUserId(Integer userId);
	
	/**
	 * ����
	 * ������Ҫ���µ��ֶ���ʵֻ��һ��,state.
	 */
	Integer update(Admin admin);
}













