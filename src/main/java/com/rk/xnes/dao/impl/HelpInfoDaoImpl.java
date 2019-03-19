package com.rk.xnes.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.rk.xnes.dao.HelpInfoDao;
import com.rk.xnes.entity.HelpInfo;

@Repository("helpInfoDao")
public class HelpInfoDaoImpl implements HelpInfoDao {

	@Autowired
	JdbcTemplate jdbct;
	@Override
	public Integer add(HelpInfo helpInfo) {
		String sql = "insert into t_helpinfo(userid,crettime,"
				+ "timeout,title,content,tip,imgpath,reward)values(?,?,?,?,?,?,?,?)";
		Integer rs = jdbct.update(sql,helpInfo.getUserid(),
				helpInfo.getCrettime(),helpInfo.getTimeout(),helpInfo.getTitle(),helpInfo.getContent(),
				helpInfo.getTip(),helpInfo.getImgpath(),helpInfo.getReward());
		return rs;
	}

	@Override
	public Integer deleteById(Integer id) {
		String sql = "delete from t_helpinfo where id=?";
		Integer rs = jdbct.update(sql,id);
		return rs;
	}

	@Override
	public Integer deleteByUserId(Integer userId) {
		String sql = "delete from t_helpinfo where userid=?";
		Integer rs = jdbct.update(sql,userId);
		return rs;
	}

	@Override
	public Integer update(HelpInfo helpInfo) {
		String sql = "update t_helpinfo set timeout=?,title=?,content=?,tip=?,imgpath=?,reward=? where id=?";
		Integer rs = jdbct.update(sql,helpInfo.getTimeout(),helpInfo.getTitle(),helpInfo.getContent(),helpInfo.getTip(),
				helpInfo.getImgpath(),helpInfo.getReward(),helpInfo.getId());
		return rs;
	}

	@Override
	public HelpInfo selectById(Integer id) {
		String sql = "select * from t_helpinfo where id=?";
		RowMapper<HelpInfo> rowMapper = new BeanPropertyRowMapper<>(HelpInfo.class);
		HelpInfo helpInfo = jdbct.queryForObject(sql, rowMapper, id);
		return helpInfo;
	}
	@Override
	public HelpInfo selectByContent(String content) {
		String sql = "select * from t_helpinfo where content=?";
		RowMapper<HelpInfo> rowMapper = new BeanPropertyRowMapper<>(HelpInfo.class);
		HelpInfo helpInfo = jdbct.queryForObject(sql, rowMapper, content);
		return helpInfo;
	}

	@Override
	public List<HelpInfo> selectByUserId(Integer userId) {
		String sql = "select * from t_helpinfo where userid=?";
		//rowMapper结果集映射使用
		RowMapper<List<HelpInfo>> rowMapper = new RowMapper<List<HelpInfo>>() {
			@Override
			public List<HelpInfo> mapRow(ResultSet rs, int arg1) throws SQLException {
				List<HelpInfo> list = new ArrayList<>();
				HelpInfo helpInfo = null;
				do {
					helpInfo = new HelpInfo();
					helpInfo.setId(rs.getInt(1));
					helpInfo.setUserid(rs.getInt(2));
					helpInfo.setCrettime(rs.getTimestamp(3));
					helpInfo.setTimeout(rs.getLong(4));
					helpInfo.setTitle(rs.getString(5));
					helpInfo.setContent(rs.getString(6));
					helpInfo.setTip(rs.getString(7));
					helpInfo.setImgpath(rs.getString(8));
					helpInfo.setReward(rs.getInt(9));
					list.add(helpInfo);
				}while(rs.next());
				
				return list;
			}
		};
		//查询
		List<HelpInfo> list = jdbct.queryForObject(sql, rowMapper, userId);
		return list;
	}

	@Override
	public List<HelpInfo> selectAll() {
		String sql = "select * from t_helpinfo";
		//rowMapper结果集映射使用
		RowMapper<List<HelpInfo>> rowMapper = new RowMapper<List<HelpInfo>>() {
			@Override
			public List<HelpInfo> mapRow(ResultSet rs, int arg1) throws SQLException {
				List<HelpInfo> list = new ArrayList<>();
				HelpInfo helpInfo = null;
				do {
					helpInfo = new HelpInfo();
					helpInfo.setId(rs.getInt(1));
					helpInfo.setUserid(rs.getInt(2));
					helpInfo.setCrettime(rs.getTimestamp(3));
					helpInfo.setTimeout(rs.getLong(4));
					helpInfo.setTitle(rs.getString(5));
					helpInfo.setContent(rs.getString(6));
					helpInfo.setTip(rs.getString(7));
					helpInfo.setImgpath(rs.getString(8));
					helpInfo.setReward(rs.getInt(9));
					list.add(helpInfo);
				}while(rs.next());
				
				return list;
			}
		};
		//查询
		List<HelpInfo> list = jdbct.queryForObject(sql, rowMapper);
		return list;
	}

}





