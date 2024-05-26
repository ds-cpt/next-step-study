package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.QuestionDao;

public class DeleteQuestionController implements Controller {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO Auto-generated method stub
		QuestionDao questionDao = new QuestionDao();
		questionDao.delete(req.getParameter("questionId"));
		return "redirect:/";
	}
}
