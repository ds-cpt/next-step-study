package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.mvc.AbstractController;
import core.mvc.AbstractNewController;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;

@Controller
public class UserController extends AbstractNewController {
	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	private UserDao userDao = UserDao.getInstance();

	@RequestMapping("/users")
	public ModelAndView list(HttpServletRequest req, HttpServletResponse resp) {
		if(!UserSessionUtils.isLogined(req.getSession()))
			return jspView("redirect:/users/loginForm");
		return jspView("/user/list.jsp");

	}

}
