package next.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.jdbc.ConnectionManager;

public class JdbcTemplate {
	public  void update(String sql, Object... params) {
		try (Connection con = ConnectionManager.getConnection();
			 PreparedStatement pstmt = con.prepareStatement(sql)) {
			for(int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}

			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... params) {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			for(int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}
			ResultSet rs = pstmt.executeQuery();
			List<T> list = new ArrayList<>();
			while (rs.next()) {
				list.add(rowMapper.mapRow(rs));
			}
			return list;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... params) {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(sql)) {
			for(int i = 0; i < params.length; i++) {
				pstmt.setObject(i+1, params[i]);
			}
			ResultSet rs = pstmt.executeQuery();
			T object = null;
			if (rs.next()) {
				object = rowMapper.mapRow(rs);
			}
			return object;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}