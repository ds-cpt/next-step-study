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
import next.model.Question;
import next.model.Result;

public class DeleteAnswerController extends AbstractController {
	private static final Logger logger = LoggerFactory.getLogger(DeleteAnswerController.class);

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ObjectMapper om = new ObjectMapper();
		Long answerId = Long.parseLong(req.getParameter("answerId"));
		try {
			AnswerDao answerDao = new AnswerDao();
			answerDao.delete(answerId);

			QuestionDao questionDao = new QuestionDao();
			Question question = questionDao.findById(Long.parseLong(req.getParameter("questionId")));
			question.minusCountOfAnswer();
			questionDao.update(question);

			resp.setContentType("application/json;charset=UTF-8");
			return jsonView().addObject("result", Result.ok());
		} catch (Exception e) {
			logger.error("Delete Answer Error : {}", e);
			resp.setContentType("application/json;charset=UTF-8");
			return jsonView().addObject("result", Result.fail("Delete Answer Error"));
		}


	}
}
