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

import com.rk.xnes.dao.UserCertifDao;
import com.rk.xnes.entity.UserCertif;

@Repository("userCertifDao")
public class UserCertifDaoImpl implements UserCertifDao {
	
	JdbcTemplate jdbct;

	@Autowired
	public UserCertifDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbct = jdbcTemplate;
	}

	@Override
	public Integer add(UserCertif userCertif) {
		String sql = "insert into t_usercertif(userid,state,cardpath)values(?,?,?)";
		Integer rs = jdbct.update(sql, userCertif.getUserid(),userCertif.getState(),userCertif.getCardpath());
		return rs;
	}

	@Override
	public Integer deleteById(Integer id) {
		String sql ="delete from t_usercertif where id=?";
		Integer rs = jdbct.update(sql,id);
		return rs;
	}

	@Override
	public Integer deleteByUserId(Integer userId) {
		String sql ="delete from t_usercertif where userid=?";
		Integer rs = jdbct.update(sql,userId);
		return rs;
	}

	@Override
	public Integer update(UserCertif userCertif) {
		String sql = "update t_usercertif set state=?,cardpath=? where id=?";
		Integer rs = jdbct.update(sql,userCertif.getState(),userCertif.getCardpath(),userCertif.getId());
		return rs;
	}

	@Override
	public UserCertif selectById(Integer id) {
		String sql = "select * from t_usercertif where id=?";
		RowMapper<UserCertif> rowMapper = new BeanPropertyRowMapper<>(UserCertif.class);
		UserCertif userCertif = jdbct.queryForObject(sql, rowMapper, id);
		return userCertif;
	}

	@Override
	public UserCertif selectByUserId(Integer userId) {
		String sql = "select * from t_usercertif where userid=?";
		RowMapper<UserCertif> rowMapper = new BeanPropertyRowMapper<>(UserCertif.class);
		UserCertif userCertif = jdbct.queryForObject(sql, rowMapper, userId);
		return userCertif;
	}

	@Override
	public List<UserCertif> selectAll() {
		String sql = "select * from t_usercertif";
		
		RowMapper<List<UserCertif>> rowMapper = new RowMapper<List<UserCertif>>() {

			@Override
			public List<UserCertif> mapRow(ResultSet rs, int rowNumber) throws SQLException {
				List<UserCertif> list = new ArrayList<UserCertif>();
				UserCertif userCertif = null;
				do {
					userCertif = new UserCertif();
					userCertif.setId(rs.getInt(1));
					userCertif.setUserid(rs.getInt(2));
					userCertif.setState(rs.getInt(3));
					userCertif.setCardpath(rs.getString(4));
					list.add(userCertif);
				}while(rs.next());
				return list;
			}
		};
		//½Ó×ÅÖ´ÐÐ
		List<UserCertif> list = jdbct.queryForObject(sql, rowMapper);
		return list;
	}

}
