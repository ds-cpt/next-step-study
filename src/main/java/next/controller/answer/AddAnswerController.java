package next.controller.answer;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import core.mvc.AbstractController;
import core.mvc.JsonView;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

public class AddAnswerController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(AddAnswerController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"),
			Long.parseLong(req.getParameter("questionId")));
		logger.debug("answer : {}", answer);
		QuestionDao questionDao = new QuestionDao();
		Question question = questionDao.findById(answer.getQuestionId());
		question.addCountOfAnswer();
		questionDao.update(question);

		AnswerDao answerDao = new AnswerDao();
		Answer savedAnswer = answerDao.insert(answer);
		ObjectMapper om = new ObjectMapper();
		resp.setContentType("application/json;charset=UTF-8");
		return jsonView().addObject("answer", savedAnswer);
	}
}
