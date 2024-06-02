package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

public class HomeController extends AbstractController {
	private final QuestionDao questionDao = QuestionDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		req.setAttribute("questions", questionDao.findAll());
		return jspView("/home.jsp");
	}
}
