package webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import db.DataBase;
import model.User;
import util.HttpRequestUtils;

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
			RequestHeader requestHeader = new RequestHeader(in);
			DataOutputStream dos = new DataOutputStream(out);

			String reqUri = requestHeader.getUri();
			byte[] body;
			if (reqUri.equals("/index.html") || reqUri.endsWith(".html")) {
				body = Files.readAllBytes(new File("./webapp" + reqUri).toPath());
			} else if (reqUri.equals("/user/login")) {
				if (requestHeader.getMethod().equals("POST")) {
					Map<String, String> stringStringMap = HttpRequestUtils.parseQueryString(requestHeader.getBody());
					User user = new User(stringStringMap.get("userId"), stringStringMap.get("password"), null, null);
					User userById = DataBase.findUserById(user.getUserId());
					if (userById == null) {
						response302Header(dos, "/user/login_failed.html", "logined=false");
						return;
					} else if (userById.getPassword().equals(user.getPassword())) {
						response302Header(dos, "/index.html", "logined=true");
						return;
					}
					log.debug("User : {}", user.toString());
				}

				response302Header(dos, "/index.html");
				return;
			} else if (reqUri.contains("/user/create")) {

				if (requestHeader.getMethod().equals("POST")) {
					if (reqUri.equals("/user/create")) {
						Map<String, String> stringStringMap = HttpRequestUtils.parseQueryString(
							requestHeader.getBody());
						User user = new User(stringStringMap.get("userId"), stringStringMap.get("password"),
							stringStringMap.get("name"), stringStringMap.get("email"));
						DataBase.addUser(user);
					}
				}

				response302Header(dos, "/index.html");
				return;

			} else {
				body = "Hello World".getBytes();
			}
			response200Header(dos, body.length);
			responseBody(dos, body);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void response302Header(DataOutputStream dos, String location) {
		try {
			dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
			dos.writeBytes("Location: " + location + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void response302Header(DataOutputStream dos, String location, String cookie) {
		try {
			dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
			dos.writeBytes("Location: " + location + "\r\n");
			dos.writeBytes("Set-Cookie: " + cookie + "; Domain=localhost; Path=/" + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
			dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	private void responseBody(DataOutputStream dos, byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
