package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

public class LoginController extends AbstractController {
	UserDao userDao = UserDao.getInstance();

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");

		User user = userDao.findByUserId(userId);
		if (user == null) {
			req.setAttribute("loginFailed", true);
			return jspView("/user/login.jsp");
		}
		if (user.matchPassword(password)) {
			HttpSession session = req.getSession();
			session.setAttribute(UserSessionUtils.USER_SESSION_KEY, user);
			return jspView("redirect:/");
		} else {
			req.setAttribute("loginFailed", true);
			return jspView("/user/login.jsp");
		}
	}
}
