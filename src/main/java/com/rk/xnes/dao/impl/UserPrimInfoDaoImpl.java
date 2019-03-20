package com.rk.xnes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rk.xnes.dao.UserPrimInfoDao;
import com.rk.xnes.entity.UserPrimInfo;
@Repository("userPrimInfoDao")
public class UserPrimInfoDaoImpl implements UserPrimInfoDao {

	JdbcTemplate jdbct;

	@Autowired
	public UserPrimInfoDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbct = jdbcTemplate;
	}

	@Override
	public UserPrimInfo selectById(Integer id) {
		String sql = "select * from t_userpriminfo where id=?";
		RowMapper<UserPrimInfo> rowMapper = new BeanPropertyRowMapper<>(UserPrimInfo.class);
		UserPrimInfo userPrimInfo = jdbct.queryForObject(sql, rowMapper, id);
		return userPrimInfo;
	}

	@Override
	public UserPrimInfo selectByUserId(Integer userId) {
		String sql = "select * from t_userpriminfo where userid=?";
		RowMapper<UserPrimInfo> rowMapper = new BeanPropertyRowMapper<>(UserPrimInfo.class);
		UserPrimInfo userPrimInfo = jdbct.queryForObject(sql, rowMapper, userId);
		return userPrimInfo;
	}

	@Override
	public Integer add(UserPrimInfo userPrimInfo) {
		String sql = "insert into t_userpriminfo(userid,neckname,usermail,userphone,sex,age,qq,dormnum,dormaddr,homeaddr)VALUES(?,?,?,?,?,?,?,?,?,?)";
		Integer rs = jdbct.update(sql,userPrimInfo.getUserid(),userPrimInfo.getNeckname(),userPrimInfo.getUsermail(),userPrimInfo.getUserphone(),userPrimInfo.getSex(),
				userPrimInfo.getAge(),userPrimInfo.getQq(),userPrimInfo.getDormnum(),userPrimInfo.getDormaddr(),userPrimInfo.getHomeaddr());
		return rs;
	}

	@Override
	public Integer deleteById(Integer id) {
		String sql = "delete from t_userpriminfo where id=?";
		Integer rs = jdbct.update(sql,id);
		return rs;
	}

	@Override
	public Integer deleteByUserId(Integer userId) {
		String sql = "delete from t_userpriminfo where userid=?";
		Integer rs = jdbct.update(sql,userId);
		return rs;
	}

	@Override
	public Integer update(UserPrimInfo userPrimInfo) {
		
		String sql ="update t_userpriminfo set neckname=?,usermail=?,userphone=?,sex=?,age=?,qq=?,dormnum=?,dormaddr=?,homeaddr=? where id=?";
		Integer rs = jdbct.update(sql,userPrimInfo.getNeckname(),userPrimInfo.getUsermail(),userPrimInfo.getUserphone(),userPrimInfo.getSex(),
				userPrimInfo.getAge(),userPrimInfo.getQq(),userPrimInfo.getDormnum(),userPrimInfo.getDormaddr(),userPrimInfo.getHomeaddr(),userPrimInfo.getId());
		return rs;
	}

}
