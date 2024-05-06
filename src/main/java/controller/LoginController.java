package controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class LoginController extends AbstractController{
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Override
	public void doPost(HttpRequest request, HttpResponse response) {
		if (request.getMethod().isPost()) {
			Map<String, String> stringStringMap = HttpRequestUtils.parseQueryString(request.getBody());
			User user = new User(stringStringMap.get("userId"), stringStringMap.get("password"), null, null);
			User userById = DataBase.findUserById(user.getUserId());
			if (userById == null) {
				response.sendRedirect("/user/login_failed.html");
				return;
			} else if (userById.getPassword().equals(user.getPassword())) {
				response.addHeader("Set-Cookie", "logined=true");
				response.sendRedirect("/index.html");
				return;
			}
			log.debug("User : {}", user.toString());
		}

		response.sendRedirect("/user/login_failed.html");
		return;
	}
}
