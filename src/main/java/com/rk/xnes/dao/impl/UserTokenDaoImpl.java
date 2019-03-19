package com.rk.xnes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rk.xnes.dao.UserTokenDao;
import com.rk.xnes.entity.UserToken;

@Repository("userTokenDao")
public class UserTokenDaoImpl implements UserTokenDao {

	@Autowired
	JdbcTemplate jdbct;
	
	@Override
	public UserToken selectById(Integer id) {
		String sql = "select * from t_usertoken where id=?";
		RowMapper<UserToken> rowMapper = new BeanPropertyRowMapper<>(UserToken.class);
		UserToken userToken = jdbct.queryForObject(sql, rowMapper, id);
		return userToken;
	}

	@Override
	public UserToken selectByUserId(Integer userId) {
		String sql = "select * from t_usertoken where userid=?";
		RowMapper<UserToken> rowMapper = new BeanPropertyRowMapper<>(UserToken.class);
		UserToken userToken = jdbct.queryForObject(sql, rowMapper, userId);
		return userToken;
	}

	@Override
	public Integer add(UserToken userToken) {
		String sql = "insert into t_usertoken(userid,token,crettime)values(?,?,?)";
		Integer rs = jdbct.update(sql,userToken.getUserid(),userToken.getToken(),userToken.getCrettime());
		return rs;
	}

	@Override
	public Integer deleteById(Integer id) {
		String sql = "delete from t_usertoken where id=?";
		Integer rs = jdbct.update(sql,id);
		return rs;
	}

	@Override
	public Integer deleteByUserId(Integer userId) {
		String sql = "delete from t_usertoken where userid=?";
		Integer rs = jdbct.update(sql,userId);
		return rs;
	}

	@Override
	public Integer update(UserToken userToken) {
		String sql = "update t_usertoken set token=?,crettime=? where id=?";
		Integer rs = jdbct.update(sql,userToken.getToken(),userToken.getCrettime(),userToken.getId());
		return rs;
	}

}
