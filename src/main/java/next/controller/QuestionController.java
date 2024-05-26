package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

public class QuestionController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();

        req.setAttribute("question", questionDao.findById(req.getParameter("questionId")));

        AnswerDao answerDao = new AnswerDao();
        req.setAttribute("answers", answerDao.findAllByQuestionId(req.getParameter("questionId")));
        return "/qna/show.jsp";
    }
}
