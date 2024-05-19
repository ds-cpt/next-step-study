package next.dao;

import java.sql.SQLException;
import java.util.List;

import next.model.User;

public class UserDao {
	public void insert(User user) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, user.getUserId(), user.getPassword(),
			user.getName(), user.getEmail());

	}

	public void update(User user) throws SQLException {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userid=?";
		jdbcTemplate.update(sql, user.getPassword(), user.getName(),
			user.getEmail(), user.getUserId());
	}

	public List<User> findAll() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		return jdbcTemplate.query("SELECT userId, password, name, email FROM USERS", rs ->
			new User(rs.getString("userId"),
				rs.getString("password"),
				rs.getString("name"),
				rs.getString("email")));
	}

	public User findByUserId(String userId) throws SQLException {

		JdbcTemplate selectJdbcTemplate = new JdbcTemplate();
		return selectJdbcTemplate.queryForObject("SELECT userId, password, name, email FROM USERS WHERE userid=?",
			rs -> {
				try {
					return new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"),
						rs.getString("email"));
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			},userId);

	}
}
