package com.rk.xnes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rk.xnes.dao.UserDao;
import com.rk.xnes.entity.User;

import javax.annotation.Resource;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

	private JdbcTemplate jdbcTemplate;


    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


	
	@Override
	public User selectById(Integer id) {
		String sql = "select * from t_user where id=?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		System.out.println("fuck" + jdbcTemplate);
		//return null;
		User user = jdbcTemplate.queryForObject(sql, rowMapper, id);
		return user;
	}

	@Override
	public User selectByStuid(Integer stuId) {
		String sql = "select * from t_user where stuid=?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		User user = jdbcTemplate.queryForObject(sql, rowMapper, stuId);
		return user;
	}

	@Override
	public User selectByAccount(String account) {
		System.out.println("[LOG][USERDAO][selectByAccount] param:" + account);
		String sql = "select * from t_user where account=?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);
		User user = jdbcTemplate.queryForObject(sql, rowMapper, account);
		return user;
	}

	@Override
	public Integer add(User user) {
		String sql = "insert into t_user(stuid,account,password,oldpassword)values(?,?,?,?)";
		Integer rs = jdbcTemplate.update(sql,user.getStuid(),user.getAccount(),user.getPassword(),user.getPassword());
		return rs;
	}

	@Override
	public Integer deleteById(Integer id) {
		String sql = "delete from t_user where id=?";
		Integer rs = jdbcTemplate.update(sql,id);
		return rs;
	}

	@Override
	public Integer deleteByStuId(Integer stuId) {
		String sql = "delete from t_user where stuid=?";
		Integer rs = jdbcTemplate.update(sql,stuId);
		return rs;
	}

	@Override
	public Integer update(User user) {
		String sql = "update t_user set password=?,oldpassword=? where id=?";
		Integer rs = jdbcTemplate.update(sql,user.getPassword(),user.getOldpassword(),user.getId());
		return rs;
	}

}
