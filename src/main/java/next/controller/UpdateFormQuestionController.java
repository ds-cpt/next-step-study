package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Question;
import next.model.User;

public class UpdateFormQuestionController implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String questionId = req.getParameter("questionId");
        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findById(questionId);
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(question.getWriter());
        // if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
        //     throw new IllegalStateException("다른 사용자의 게시글을 수정할 수 없습니다.");
        // }
        req.setAttribute("question", question);
        req.setAttribute("user", user);
        return "/qna/updateForm.jsp";
    }
}
