package next.dao;

import java.util.List;

import core.jdbc.JdbcTemplate;
import next.model.Answer;

public class AnswerDao {

	public List<Answer> findAllByQuestionId(String questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "SELECT answerId, writer, contents, createdDate, questionId FROM ANSWERS WHERE questionId = ?";
		return jdbcTemplate.query(sql, (rs) -> new Answer(rs.getLong("answerId"), rs.getString("writer"), rs.getString("contents"),
				rs.getString("createdDate"), rs.getLong("questionId")), questionId);
	}

	public void insert(Answer question) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		String sql = "INSERT INTO ANSWERS (writer, contents, createdDate, questionId) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, question.getWriter(), question.getContents(), question.getCreatedDate(), question.getQuestionId());
	}
}
