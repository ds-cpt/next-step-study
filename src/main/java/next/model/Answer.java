package next.model;

import java.time.LocalDateTime;


public class Answer {
	private Long answerId;
	private String writer;
	private String contents;
	private String createdDate;
	private Long questionId;

	public Answer(String writer, String contents, Long questionId) {
		this.writer = writer;
		this.contents = contents;
		this.createdDate = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		this.questionId = questionId;
	}

	public Answer(Long answerId, String writer, String contents, String createdDate, Long questionId) {
		this.answerId = answerId;
		this.writer = writer;
		this.contents = contents;
		this.createdDate = createdDate;
		this.questionId = questionId;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public String getWriter() {
		return writer;
	}

	public String getContents() {
		return contents;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public Long getQuestionId() {
		return questionId;
	}
}
