package webserver;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpResponse {
	private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

	private DataOutputStream dos = null;
	private Map<String, String> headers = new HashMap<>();

	public HttpResponse(OutputStream out) {
		dos = new DataOutputStream(out);
	}

	public HttpResponse(DataOutputStream dos, Map<String, String> headers) {
		this.dos = dos;
		this.headers = headers;
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public void forward(String url) throws IOException {
		byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());

		if (url.endsWith(".css")) {
			headers.put("Content-Type", "text/css");
		} else if (url.endsWith(".js")) {
			headers.put("Content-Type", "application/javascript");
		} else {
			headers.put("Content-Type", "text/html;charset=utf-8");
		}
		headers.put("Content-Length", body.length + "");
		response200Header(body.length);
		responseBody(body);
	}

	public void forwardBody(String body) {
		byte[] contents = body.getBytes();
		headers.put("Content-Type", "text/html;charset=utf-8");
		headers.put("Content-Length", contents.length + "");
		response200Header(contents.length);
		responseBody(contents);
	}

	public void response200Header(int length) {
		try {
			dos.writeBytes("HTTP/1.1 200 OK \r\n");
			processHeaders();
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void responseBody(byte[] body) {
		try {
			dos.write(body, 0, body.length);
			dos.writeBytes("\r\n");
			dos.flush();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void sendRedirect(String location) {
		try {
			dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
			processHeaders();
			dos.writeBytes("Location: " + location + "\r\n");
			dos.writeBytes("\r\n");
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public void processHeaders() {
		try {
			for (String key : headers.keySet()) {
				dos.writeBytes(key + ": " + headers.get(key) + "\r\n");
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

}
