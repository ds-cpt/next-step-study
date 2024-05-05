package webserver;

import http.HttpRequest;
import http.HttpResponse;
import model.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.Controller;
import db.DataBase;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = new HttpRequest(in);
            HttpResponse response = new HttpResponse(out);
            String path = getDefaultPath(request.getPath());

            switch (path) {
                case "/user/create":
                    User newUser = new User(
                        request.getParameter("userId"),
                        request.getParameter("password"),
                        request.getParameter("name"),
                        request.getParameter("email")
                    );
                    DataBase.addUser(newUser);
                    response.sendRedirect("/index.html");
                    break;
                case "/user/login":
                    User loginUser = DataBase.findUserById(request.getHeader("userId"));
                    if(loginUser == null){
                        new Exception(); // 사용자 못찾는 익셉션처리 후 login 조회불가 페이지로 이동
                    }else{
                        if(loginUser.login(request.getParameter("password"))){
                            response.addHeader("Set-Cookie", "logined=true");
                            response.sendRedirect("/index.html");
                        }else{
                            response.sendRedirect("/user/login_failed.html");
                        }
                    }
                    break;
                case "/user/list":
                    if(!"logined=true".equals(response.getHeader("Set-Cookie"))){
                        response.sendRedirect("user/login.html");
                        return;
                    }
                    Collection<User> users = DataBase.findAll();
                    StringBuilder sb = new StringBuilder();
                    sb.append("<table border='1'>");
                    for (User user : users) {
                        sb.append("<tr>");
                        sb.append("<td>" + user.getUserId() + "</td>");
                        sb.append("<td>" + user.getName() + "</td>");
                        sb.append("<td>" + user.getEmail() + "</td>");
                        sb.append("</tr>");
                    }
                    sb.append("</table>");
                    response.forwardBody(sb.toString());
                    break;
                default:
                    break;
            }


            // Controller controller = RequestMapping.getController(request.getPath());
            // if (controller == null) {
            //     String path = getDefaultPath(request.getPath());
            //     response.forward(path);
            // } else {
            //     controller.service(request, response);
            // }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private String getDefaultPath(String path) {
        if (path.equals("/")) {
            return "/index.html";
        }
        return path;
    }
}
