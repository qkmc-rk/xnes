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

import com.rk.xnes.dao.NoticeDao;
import com.rk.xnes.entity.Notice;

@Repository("noticeDao")
public class NoticeDaoImpl implements NoticeDao {

	JdbcTemplate jdbct;

	@Autowired
	public NoticeDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbct = jdbcTemplate;
	}

	@Override
	public Integer add(Notice notice) {
		String sql ="insert into t_notice(cretime,creatorid,notice)values(?,?,?)"; 
		Integer rs = jdbct.update(sql,notice.getCretime(),notice.getCreatorid(),notice.getNotice());
		return rs;
	}

	@Override
	public Integer deleteById(Integer id) {
		String sql = "delete from t_notice where id=?";
		Integer rs = jdbct.update(sql,id);
		return rs;
	}

	@Override
	public Integer update(Notice notice) {
		String sql ="update t_notice set cretime=?,creatorid=?,notice=? where id=?"; 
		Integer rs = jdbct.update(sql,notice.getCretime(),notice.getCreatorid(),notice.getNotice(),notice.getId());
		return rs;
	}

	@Override
	public Notice selectById(Integer id) {
		String sql = "select * from t_notice where id=?";
		RowMapper<Notice> rowMapper = new BeanPropertyRowMapper<>(Notice.class);
		Notice notice = jdbct.queryForObject(sql, rowMapper, id);
		return notice;
	}

	@Override
	public List<Notice> selectAll() {
		String sql = "select * from t_notice ";
		RowMapper<List<Notice>> rowMapper = new RowMapper<List<Notice>>() {

			@Override
			public List<Notice> mapRow(ResultSet rs, int rowNum) throws SQLException {
				List<Notice> list = new ArrayList<>();
				Notice notice = null;
				do {
					notice = new Notice();
					notice.setId(rs.getInt(1));
					notice.setCretime(rs.getTimestamp(2));
					notice.setCreatorid(rs.getInt(3));
					notice.setNotice(rs.getString(4));
					list.add(notice);
				}while(rs.next());
				return list;
			}
			
		};
		List<Notice> list = jdbct.queryForObject(sql, rowMapper);
		return list;
	}

	
	/**
	 * 暂时不用实现
	 */
	@Override
	public List<Notice> selectByCreatorId(Integer creatorId) {
		return null;
	}

}
