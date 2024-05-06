package webserver;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;

import org.junit.Test;

import type.HttpMethod;

public class HttpRequestTest {
	private String testDirectory = "./src/test/resources/";

	@Test
	public void request_GET() throws IOException {
		// Given
		File file = new File(testDirectory + "Http_GET.txt");
		InputStream in = Files.newInputStream(file.toPath());

		// When
		HttpRequest httpRequest = new HttpRequest(in);

		// Then
		assertEquals(HttpMethod.GET, httpRequest.getMethod());
		assertEquals("/user/create", httpRequest.getPath());
		assertEquals("keep-alive", httpRequest.getHeader("Connection"));
		assertEquals("javajigi", httpRequest.getParameter("userId"));


	}

	@Test
	public void request_POST() throws Exception {
		// Given
		File file = new File(testDirectory + "Http_POST.txt");
		InputStream in = Files.newInputStream(file.toPath());

		// When
		HttpRequest httpRequest = new HttpRequest(in);

		// Then
		assertEquals(HttpMethod.POST, httpRequest.getMethod());
		assertEquals("/user/create", httpRequest.getPath());
		assertEquals("keep-alive", httpRequest.getHeader("Connection"));
		assertEquals("javajigi", httpRequest.getParameter("userId"));
		assertEquals("JaeSung", httpRequest.getParameter("name"));
	}

	@Test
	public void responseForward() throws Exception {
		// Given
		HttpResponse response = new HttpResponse(createOutputStream("Http_Redirect.txt"));

		//then
		response.sendRedirect("/index.html");
	}

	@Test
	public void responseCookies() throws Exception {
		// Given
		HttpResponse response = new HttpResponse(createOutputStream("Http_Cookie.txt"));

		//then
		response.addHeader("Set-Cookie", "logined=true");
		response.sendRedirect( "/index.html");
	}


	private OutputStream createOutputStream(String filename) throws FileNotFoundException {
		return new FileOutputStream(new File(testDirectory + filename));
	}

}