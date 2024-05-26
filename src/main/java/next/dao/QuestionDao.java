package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Question;

public class QuestionDao {
	public void insert(Question question) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "INSERT INTO QUESTIONS VALUES (?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, question.getQuestionId(), question.getWriter(), question.getTitle(),
			question.getContents(), question.getCreatedDate(), question.getCountOfAnswer());
	}

	public List<Question> findAll() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS";

		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getString("questionId"), rs.getString("writer"), rs.getString("title"),
					rs.getString("contents"), rs.getString("createdDate"), rs.getInt("countOfAnswer"));
			}
		};

		return jdbcTemplate.query(sql, rm);
	}

	public Question findById(String questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS WHERE questionId = ?";
		RowMapper<Question> rm = new RowMapper<Question>() {
			@Override
			public Question mapRow(ResultSet rs) throws SQLException {
				return new Question(rs.getString("questionId"), rs.getString("writer"), rs.getString("title"),
					rs.getString("contents"), rs.getString("createdDate"), rs.getInt("countOfAnswer"));
			}
		};

		return jdbcTemplate.queryForObject(sql, rm, questionId);
	}

	public void update(Question questionFromDb) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "UPDATE QUESTIONS set writer = ?, title = ?, contents = ?, createdDate = ?, countOfAnswer = ? WHERE questionId = ?";
		jdbcTemplate.update(sql, questionFromDb.getWriter(), questionFromDb.getTitle(), questionFromDb.getContents(),
			questionFromDb.getCreatedDate(), questionFromDb.getCountOfAnswer(), questionFromDb.getQuestionId());
	}

	public void delete(String questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "DELETE FROM QUESTIONS WHERE questionId = ?";
		jdbcTemplate.update(sql, questionId);
	}
}
