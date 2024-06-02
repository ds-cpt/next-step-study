package next.model;

import java.util.Date;

public class Question {
	private final long questionId;

	private final String writer;

	private String title;

	private String contents;

	private final Date createdDate;

	private int countOfAnswer;

	public Question(String writer, String title, String contents) {
		this(0, writer, title, contents, new Date(), 0);
	}

	public Question(long questionId, String writer, String title, String contents, Date createdDate,
		int countOfAnswer) {
		this.questionId = questionId;
		this.writer = writer;
		this.title = title;
		this.contents = contents;
		this.createdDate = createdDate;
		this.countOfAnswer = countOfAnswer;
	}

	public long getQuestionId() {
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public long getTimeFromCreateDate() {
		return this.createdDate.getTime();
	}

	public int getCountOfAnswer() {
		return countOfAnswer;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", writer=" + writer + ", title=" + title + ", contents="
			+ contents + ", createdDate=" + createdDate + ", countOfAnswer=" + countOfAnswer + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int)(questionId ^ (questionId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question)obj;
		return questionId == other.questionId;
	}

	public void addCountOfAnswer() {
		this.countOfAnswer++;
	}

	public void minusCountOfAnswer() {
		this.countOfAnswer--;
	}

	public boolean isSameUser(User user) {
		return user.isSameUser(this.writer);
	}

	public void update(Question newQuestion) {
		this.title = newQuestion.title;
		this.contents = newQuestion.contents;
	}
}
