package com.rk.xnes.dao;

import java.util.List;

import com.rk.xnes.entity.Comment;

public interface CommentDao {

	/**
	 * 增删改查
	 */
	//查询
	Comment selectById(Integer id);
	//一个用户可能发表多个评论
	List<Comment> selectByUserId(Integer userId);
	//某条发布的消息可能含有多条评论
	List<Comment> selectByInfoId(Integer infoId);
	//一个用户对同一个任务发布多条评论
	List<Comment> selectByUserIdInfoId(Integer userId, Integer infoId);
	
	//增加
	Integer add(Comment comment);
	
	//删除
	Integer deleteById(Integer id);
	/**
	 * 通过用户的id删除一部分记录,估计用不到
	 * @param userId
	 * @return 删除条数
	 */
	Integer deleteByUserId(Integer userId);
	Integer deleteByUserIdInfoId(Integer userId, Integer infoId);
	
	/**
	 * 更新某条记录
	 * @param comment
	 * @return返回更新的条数 错误返回-1
	 */
	Integer Update(Comment comment);
	
	
}










