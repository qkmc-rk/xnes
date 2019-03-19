package com.rk.xnes.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rk.xnes.entity.HelpInfo;

public interface HelpInfoDao {

	//��ɾ�Ĳ�
	//��
	Integer add(HelpInfo helpInfo);
	
	//ɾ
	Integer deleteById(Integer id);
	//���ܻ�ɾ��һ��
	/**
	 * �����û���idɾ���û�����������
	 * @param userId
	 * @return ɾ��������,ע��ʹ��,���ܻ�ɾ������û����е�������Ϣ
	 */
	Integer deleteByUserId(Integer userId);
	
	//��
	Integer update(HelpInfo helpInfo);
	
	//��
	HelpInfo selectById(Integer id);
	List<HelpInfo> selectByUserId(Integer userId);
	List<HelpInfo> selectAll();

	/**
	 * ͨ��content����helpinfo,����Ҫ���õ�helpinfo��id...
	 * @param content
	 * @return
	 */
	HelpInfo selectByContent(String content);
}
