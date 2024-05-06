package controller;

import java.io.IOException;

import type.HttpMethod;
import webserver.HttpRequest;
import webserver.HttpResponse;

public abstract class AbstractController implements Controller{
	@Override
	public void service(HttpRequest request, HttpResponse response) throws IOException {
		HttpMethod method = request.getMethod();
		if(method.isPost()){
			doPost(request, response);
		}else
			doGet(request, response);

	}

	protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
	}
	protected void doPost(HttpRequest request, HttpResponse response) {
	}

}
