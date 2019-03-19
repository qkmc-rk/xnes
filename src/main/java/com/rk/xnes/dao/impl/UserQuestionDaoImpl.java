package com.rk.xnes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rk.xnes.dao.UserQuestionDao;
import com.rk.xnes.entity.UserQuestion;

@Repository("userQuestionDao")
public class UserQuestionDaoImpl implements UserQuestionDao {

	@Autowired
	JdbcTemplate jdbct;
	
	@Override
	public UserQuestion selectById(Integer id) {
		String sql = "select * from t_userquestion where id=?";
		RowMapper<UserQuestion> rowMapper = new BeanPropertyRowMapper<>(UserQuestion.class);
		UserQuestion userQuestion = jdbct.queryForObject(sql, rowMapper, id);
		return userQuestion;
	}

	@Override
	public UserQuestion selectByUserId(Integer userId) {
		String sql = "select * from t_userquestion where userid=?";
		RowMapper<UserQuestion> rowMapper = new BeanPropertyRowMapper<>(UserQuestion.class);
		UserQuestion userQuestion = jdbct.queryForObject(sql, rowMapper, userId);
		return userQuestion;
	}

	@Override
	public Integer add(UserQuestion userQuestion) {
		String sql = "insert into t_userquestion(userid,question1,answer1,question2,answer2,question3,answer3)values(?,?,?,?,?,?,?)";
		Integer rs = jdbct.update(sql,userQuestion.getUserid(),userQuestion.getQuestion1(),userQuestion.getAnswer1(),userQuestion.getQuestion2(),userQuestion.getAnswer2(),
				userQuestion.getQuestion3(),userQuestion.getAnswer3());
		return rs;
	}

	@Override
	public Integer deleteById(Integer id) {
		String sql = "delete from t_userquestion where id=?";
		Integer rs = jdbct.update(sql,id);
		return rs;
	}

	@Override
	public Integer deleteByUserId(Integer userId) {
		String sql = "delete from t_userquestion where userid=?";
		Integer rs = jdbct.update(sql,userId);
		return rs;
	}

	@Override
	public Integer update(UserQuestion userQuestion) {
		String sql = "update t_userquestion set question1=?,answer1=?,question2=?,answer2=?,question3=?,answer3=? where id=?";
		Integer rs = jdbct.update(sql,userQuestion.getQuestion1(),userQuestion.getAnswer1(),userQuestion.getQuestion2(),userQuestion.getAnswer2(),
				userQuestion.getQuestion3(),userQuestion.getAnswer3(),userQuestion.getId());
		return rs;
	}

}
