package com.rk.xnes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rk.xnes.dao.AdminDao;
import com.rk.xnes.entity.Admin;

@Repository("AdminDao")
public class AdminDaoImpl implements AdminDao {

	@Autowired
	JdbcTemplate jdbct;
	@Override
	public Admin selectById(Integer id) {
		String sql = "select * from t_admin where id=?";
		RowMapper<Admin> rowMapper = new BeanPropertyRowMapper<>(Admin.class);
		Admin admin = jdbct.queryForObject(sql, rowMapper, id);
		return admin;
	}

	@Override
	public Admin selectByUserId(Integer userId) {
		String sql = "select * from t_admin where userid=?";
		RowMapper<Admin> rowMapper = new BeanPropertyRowMapper<>(Admin.class);
		Admin admin = null;
		try {
			admin = jdbct.queryForObject(sql, rowMapper, userId);
		} catch (DataAccessException e) {
			e.printStackTrace();
			return null;
		}
		return admin;
	}

	@Override
	public Integer insert(Admin admin) {
		String sql = "insert into t_admin(userid,state)values(?,?)";
		Integer rs = jdbct.update(sql,admin.getUserid(),admin.getState());
		return rs; 
	}

	@Override
	public Integer deleteById(Integer id) {
		String sql = "delete from t_admin where id=?";
		Integer rs = jdbct.update(sql,id);
		return rs;
	}

	@Override
	public Integer deleteByUserId(Integer userId) {
		String sql = "delete from t_admin where userid=?";
		Integer rs = jdbct.update(sql,userId);
		return rs;
	}

	@Override
	public Integer update(Admin admin) {
		String sql = "update t_admin set userid=?,state=?";
		Integer rs = jdbct.update(sql,admin.getUserid(),admin.getState());
		return rs;
	}

}
