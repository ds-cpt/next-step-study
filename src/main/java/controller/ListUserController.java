package controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class ListUserController extends AbstractController{
	@Override
	public void doGet(HttpRequest request, HttpResponse response) throws IOException {
		if (request.getMethod().isGet()) {
			if (isLogin(request.getHeader("Cookie"))) {
				Collection<User> all = DataBase.findAll();
				StringBuilder sb = new StringBuilder();
				sb.append("<table border='1'>");
				sb.append("<tr>");
				sb.append("<th>userId</th>");
				sb.append("<th>name</th>");
				sb.append("<th>email</th>");
				sb.append("</tr>");
				for (User user : all) {
					sb.append("<tr>");
					sb.append("<td>" + user.getUserId() + "</td>");
					sb.append("<td>" + user.getName() + "</td>");
					sb.append("<td>" + user.getEmail() + "</td>");
					sb.append("</tr>");
				}
				sb.append("</table>");

				response.forwardBody(sb.toString());
			} else {
				response.forward("/user/login.html");
			}

			return;
		}
		response.forward("index.html");
		return;
	}
	private boolean isLogin(String cookieValue) {
		Map<String, String> cookies = HttpRequestUtils.parseCookies(cookieValue);
		String value = cookies.get("logined");
		if (value == null)
			return false;
		return Boolean.parseBoolean(cookies.get("logined"));
	}
}
