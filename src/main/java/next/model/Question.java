package next.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Question {

	private String questionId;
	private String writer;
	private String title;
	private String contents;
	private String createdDate;
	private int countOfAnswer;

	public Question(String questionId, String writer, String title, String contents, String createdDate,
		int countOfAnswer) {
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
		this.countOfAnswer = countOfAnswer;
	}

	public Question(String writer, String title, String contents) {
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createdDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		this.countOfAnswer = 0;
	}

	public String getQuestionId() {
		return questionId;
	}

	public String getWriter() {
		return writer;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public int getCountOfAnswer() {
		return countOfAnswer;
	}

	public void addCountOfAnswer() {
		this.countOfAnswer += 1;
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
}
