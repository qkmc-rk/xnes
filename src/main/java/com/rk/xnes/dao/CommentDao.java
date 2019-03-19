package com.rk.xnes.dao;

import java.util.List;

import com.rk.xnes.entity.Comment;

public interface CommentDao {

	/**
	 * ��ɾ�Ĳ�
	 */
	//��ѯ
	Comment selectById(Integer id);
	//һ���û����ܷ���������
	List<Comment> selectByUserId(Integer userId);
	//ĳ����������Ϣ���ܺ��ж�������
	List<Comment> selectByInfoId(Integer infoId);
	//һ���û���ͬһ�����񷢲���������
	List<Comment> selectByUserIdInfoId(Integer userId, Integer infoId);
	
	//����
	Integer add(Comment comment);
	
	//ɾ��
	Integer deleteById(Integer id);
	/**
	 * ͨ���û���idɾ��һ���ּ�¼,�����ò���
	 * @param userId
	 * @return ɾ������
	 */
	Integer deleteByUserId(Integer userId);
	Integer deleteByUserIdInfoId(Integer userId, Integer infoId);
	
	/**
	 * ����ĳ����¼
	 * @param comment
	 * @return���ظ��µ����� ���󷵻�-1
	 */
	Integer Update(Comment comment);
	
	
}










