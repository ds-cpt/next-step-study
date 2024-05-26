package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.model.Question;

public class UpdaetQuestionController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub\
		QuestionDao questionDao = new QuestionDao();
		Question byId = questionDao.findById(req.getParameter("questionId"));
		byId.update(req.getParameter("title"), req.getParameter("contents"));
		questionDao.update(byId);

		return "redirect:/";
	}
}
