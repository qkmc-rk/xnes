package com.rk.xnes.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rk.xnes.dao.HelpStateDao;
import com.rk.xnes.entity.HelpState;

@Repository("helpStateDao")
public class HelpStateDaoImpl implements HelpStateDao {

	@Autowired
	JdbcTemplate jdbct;
	
	@Override
	public Integer add(HelpState helpState) {
		String sql = "insert into t_helpstate(infoid,timeout,received,receiverid,achieved)values(?,?,?,?,?)";
		Integer rs = jdbct.update(sql,helpState.getInfoid(),helpState.getTimeout(),helpState.getReceived(),helpState.getReceiverid(),helpState.getAchieved());
		return rs;
	}

	@Override
	public Integer deleteById(Integer id) {
		String sql = "delete from t_helpstate where id=?";
		Integer rs = jdbct.update(sql,id);
		return rs;
	}

	@Override
	public Integer deleteByInfoId(Integer infoId) {
		String sql = "delete from t_helpstate where infoid=?";
		Integer rs = jdbct.update(sql,infoId);
		return rs;
	}

	@Override
	public Integer update(HelpState helpState) {
		String sql ="update t_helpstate set timeout=?,received=?,receiverid=?,"
				+ "achieved=? where id=?";
		Integer rs = jdbct.update(sql,helpState.getTimeout(),helpState.getReceived(),helpState.getReceiverid(),
				helpState.getAchieved(),helpState.getId());
		return rs;
	}

	@Override
	public HelpState selectById(Integer id) {
		String sql = "select * from t_helpstate where id=?";
		RowMapper<HelpState> rowMapper = new BeanPropertyRowMapper<>(HelpState.class);
		HelpState helpState = jdbct.queryForObject(sql, rowMapper, id);
		return helpState;
	}

	@Override
	public HelpState selectByInfoId(Integer infoId) {
		String sql = "select * from t_helpstate where infoid=?";
		RowMapper<HelpState> rowMapper = new BeanPropertyRowMapper<>(HelpState.class);
		HelpState helpState = jdbct.queryForObject(sql, rowMapper, infoId);
		return helpState;
	}

}
