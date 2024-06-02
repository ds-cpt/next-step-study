package next.controller.qna;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

public class CreateQuestionController extends AbstractController {
	QuestionDao questionDao = QuestionDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if (!UserSessionUtils.isLogined(req.getSession())) {
			return jspView("redirect:/users/loginForm");
		}
		User user = UserSessionUtils.getUserFromSession(req.getSession());
		Question question = new Question(user.getUserId(), req.getParameter("title"), req.getParameter("contents"));

		questionDao.insert(question);
		return jspView("redirect:/");

	}
}
