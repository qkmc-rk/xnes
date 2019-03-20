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

import com.rk.xnes.dao.CommentDao;
import com.rk.xnes.entity.Comment;

@Repository("CommentDao")
public class CommentDaoImpl implements CommentDao {


	JdbcTemplate jdbct;

	@Autowired
	public CommentDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbct = jdbcTemplate;
	}

	@Override
	public Comment selectById(Integer id) {
		String sql = "select * from t_comment where id=?";
		RowMapper<Comment> rowMapper = new BeanPropertyRowMapper<>(Comment.class);
		Comment comment = jdbct.queryForObject(sql, rowMapper, id);
		return comment;
	}

	@Override
	public List<Comment> selectByUserId(Integer userId) {
		String sql = "select * from t_comment where userid=?";
		//需要自己实现rowmapper接口
		RowMapper<List<Comment>> rowMapper = new RowMapper<List<Comment>>() {

			@Override
			public List<Comment> mapRow(ResultSet rs, int rowNumber) throws SQLException {
				List<Comment> list = new ArrayList<Comment>();
				Comment comment = null;
				do {
					comment = new Comment();
					comment.setId(rs.getInt(1));
					comment.setUserid(rs.getInt(2));
					comment.setInfoid(rs.getInt(3));
					comment.setTitle(rs.getString(4));
					comment.setComment(rs.getString(5));
					comment.setCommenttime(rs.getTimestamp(rowNumber));
					list.add(comment);
				}while(rs.next());
				return list;
			}
		};
		//接着执行
		List<Comment> list = jdbct.queryForObject(sql, rowMapper, userId);
		return list;
	}

	@Override
	public List<Comment> selectByInfoId(Integer infoId) {
		String sql = "select * from t_comment where infoid=?";
		//需要自己实现rowmapper接口
		RowMapper<List<Comment>> rowMapper = new RowMapper<List<Comment>>() {

			@Override
			public List<Comment> mapRow(ResultSet rs, int rowNumber) throws SQLException {
				List<Comment> list = new ArrayList<Comment>();
				Comment comment = null;
				do {
					comment = new Comment();
					comment.setId(rs.getInt(1));
					comment.setUserid(rs.getInt(2));
					comment.setInfoid(rs.getInt(3));
					comment.setTitle(rs.getString(4));
					comment.setComment(rs.getString(5));
					comment.setCommenttime(rs.getTimestamp(rowNumber));
					list.add(comment);
				}while(rs.next());
				return list;
			}
		};
		List<Comment> list = jdbct.queryForObject(sql, rowMapper, infoId);
		return list;
	}

	@Override
	public List<Comment> selectByUserIdInfoId(Integer userId, Integer infoId) {
		String sql = "select * from t_comment where userid=? and infoid=?";
		RowMapper<List<Comment>> rowMapper = new RowMapper<List<Comment>>() {

			@Override
			public List<Comment> mapRow(ResultSet rs, int rowNumber) throws SQLException {
				List<Comment> list = new ArrayList<Comment>();
				Comment comment = null;
				do {
					comment = new Comment();
					comment.setId(rs.getInt(1));
					comment.setUserid(rs.getInt(2));
					comment.setInfoid(rs.getInt(3));
					comment.setTitle(rs.getString(4));
					comment.setComment(rs.getString(5));
					comment.setCommenttime(rs.getTimestamp(rowNumber));
					list.add(comment);
				}while(rs.next());
				return list;
			}
		};
		List<Comment> list = jdbct.queryForObject(sql, rowMapper, userId, infoId);
		return list;
		
	}

	@Override
	public Integer add(Comment comment) {
		String sql = "insert into t_comment(userid,infoid,title,comment,commenttime)values(?,?,?,?,?)";
		Integer rs = jdbct.update(sql,comment.getUserid(),comment.getInfoid(),comment.getTitle(),comment.getComment(),comment.getCommenttime());
		return rs;
	}

	@Override
	public Integer deleteById(Integer id) {
		String sql = "delete from t_comment where id=?";
		Integer rs = jdbct.update(sql,id);
		return rs;
	}

	@Override
	public Integer deleteByUserId(Integer userId) {
		String sql = "delete from t_comment where userid=?";
		Integer rs = jdbct.update(sql,userId);
		return rs;
	}

	@Override
	public Integer deleteByUserIdInfoId(Integer userId, Integer infoId) {
		String sql = "delete from t_comment where userid=? and infoid=?";
		Integer rs = jdbct.update(sql,userId,infoId);
		return rs;
	}

	@Override
	public Integer Update(Comment comment) {
		String sql = "update t_commnet set title=?,comment=? where id=?";
		Integer rs = jdbct.update(sql,comment.getTitle(),comment.getComment());
		return rs;
	}

}




