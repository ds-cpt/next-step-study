package core.nmvc;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import core.mvc.ModelAndView;

public class HandlerExecution {
	private static final Logger logger = LoggerFactory.getLogger(HandlerExecution.class);
	// controller
	private Object declaredObject;
	// controller 의 method
	private Method method;

	public HandlerExecution(Object declaredObject, Method method) {
		this.declaredObject = declaredObject;
		this.method = method;
	}

	public ModelAndView handle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			return (ModelAndView) method.invoke(declaredObject, request, response);
		} catch (Exception e) {
			logger.error("Error : {}", e);
			throw e;
		}
	}
}
