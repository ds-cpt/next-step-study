package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

public class CreateAnswerController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(CreateAnswerController.class);

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User userFromSession = UserSessionUtils.getUserFromSession(req.getSession());
        if(userFromSession == null) {
            return "redirect:/users/loginForm";
        }
        Answer question = new Answer(userFromSession.getName(), req.getParameter("contents"), Long.parseLong(req.getParameter("questionId")));
        log.debug("Answer : {}", question);

        QuestionDao questionDao = new QuestionDao();
        Question questionFromDb = questionDao.findById(req.getParameter("questionId"));
        questionFromDb.addCountOfAnswer();
        questionDao.update(questionFromDb);

        AnswerDao answerDao = new AnswerDao();
        answerDao.insert(question);
        return "redirect:/";
    }
}
