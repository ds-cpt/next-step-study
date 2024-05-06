package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import type.HttpMethod;
import util.HttpRequestUtils;
import util.IOUtils;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private final Map<String, String> headers = new HashMap<>();
    private String body;
    private RequestLine requestLine;


    public HttpRequest(InputStream in) throws IOException {
        parseHeader(in);
    }

    public HttpMethod getMethod() {
        return requestLine.getMethod();
    }


    public String getProtocol() {
        return requestLine.getProtocol();
    }

    public String getBody() {
        return body;
    }

    private void parseHeader(InputStream in) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String line = bufferedReader.readLine();
        if (line != null) {
            requestLine = new RequestLine(line);
            log.debug("{}", line);
            while (!line.isEmpty()) {
                line = bufferedReader.readLine();
                parseHeaderLine(line);
                log.debug("header: {}", line);
            }
        }
        if(getContentLength() > 0){
            char[] body = new char[getContentLength()];
            String bodyString = IOUtils.readData(bufferedReader, body.length);
            this.body = bodyString;
            this.requestLine.getParams().putAll(HttpRequestUtils.parseQueryString(bodyString));
            log.debug("body: {}", this.body);
        }

    }



    private void parseHeaderLine(String line) {
        String[] tokens = line.split(":");
        if (tokens.length == 2) {
            headers.put(tokens[0].trim(), tokens[1].trim());
        }
    }

    public int getContentLength() {
        return Integer.parseInt(headers.getOrDefault("Content-Length", "0"));
    }

    public String getHeader(String key) {
        return headers.get(key);
    }

    public String getParameter(String userId) {
        return requestLine.getParameter(userId);
    }

    public String getPath() {
        return requestLine.getPath();
    }
}
