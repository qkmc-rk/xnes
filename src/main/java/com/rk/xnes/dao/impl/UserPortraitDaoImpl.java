package com.rk.xnes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rk.xnes.dao.UserPortraitDao;
import com.rk.xnes.entity.UserPortrait;

@Repository("userPortraitDao")
public class UserPortraitDaoImpl implements UserPortraitDao {


	JdbcTemplate jdbct;

	@Autowired
	public UserPortraitDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbct = jdbcTemplate;
	}

	@Override
	public UserPortrait selectById(Integer id) {
		String sql = "select * from t_userportrait where id=?";
		RowMapper<UserPortrait> rowMapper = new BeanPropertyRowMapper<>(UserPortrait.class);
		UserPortrait userPortrait = jdbct.queryForObject(sql, rowMapper, id);
		return userPortrait;
	}

	@Override
	public UserPortrait selectByUserId(Integer userId) {
		String sql = "select * from t_userportrait where userid=?";
		RowMapper<UserPortrait> rowMapper = new BeanPropertyRowMapper<>(UserPortrait.class);
		UserPortrait userPortrait = jdbct.queryForObject(sql, rowMapper, userId);
		return userPortrait;
	}

	@Override
	public Integer add(UserPortrait userPortrait) {
		String sql = "insert into t_userportrait(userid,portrait)values(?,?)";
		Integer rs = jdbct.update(sql,userPortrait.getUserid(),userPortrait.getPortrait());
		return rs;
	}

	@Override
	public Integer deleteById(Integer id) {
		String sql = "delete from t_userportrait where id=?";
		Integer rs = jdbct.update(sql,id);
		return rs;
	}

	@Override
	public Integer deleteByUserId(Integer userId) {
		String sql = "delete from t_userportrait where userid=?";
		Integer rs = jdbct.update(sql,userId);
		return rs;
	}

	@Override
	public Integer update(UserPortrait userPortrait) {
		String sql ="update t_userportrait set portrait=? where id=?";
		Integer rs = jdbct.update(sql,userPortrait.getPortrait(),userPortrait.getId());
		return rs;
	}

}
