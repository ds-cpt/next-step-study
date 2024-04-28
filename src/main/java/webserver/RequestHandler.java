package webserver;

import ch.qos.logback.core.util.ContentTypeUtil;
import model.User;
import util.HttpRequestUtils;
import util.IOUtils;

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
            } else if (reqUri.contains("/user/create")){
                //create handling function for post method
                if(requestHeader.getMethod().equals("POST")){
                    if(reqUri.equals("/user/create")){
                        Map<String, String> stringStringMap = HttpRequestUtils.parseQueryString(requestHeader.getBody());
                        User user = new User(stringStringMap.get("userId"), stringStringMap.get("password"), stringStringMap.get("name"), stringStringMap.get("email"));
                        log.debug("User : {}", user.toString());
                    }
                }
                if(requestHeader.getMethod().equals("GET")){
                    String[] split = reqUri.split("\\?");
                    String requestPath = split[0];
                    String params = split[1];
                    if(requestPath.equals("/user/create") && split.length == 2){
                        Map<String, String> stringStringMap = HttpRequestUtils.parseQueryString(params);
                        User user = new User(stringStringMap.get("userId"), stringStringMap.get("password"), stringStringMap.get("name"), stringStringMap.get("email"));
                        log.debug("User : {}", user.toString());
                    }
                }

                body = "Hello World".getBytes();
            }else {
                body = "Hello World".getBytes();
            }
            response200Header(dos, body.length);
            responseBody(dos, body);
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
