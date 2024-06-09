package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.Controller;
import core.mvc.ModelAndView;

public class ControllerHandlerAdapter implements HandlerAdapter{
	@Override
	public boolean supports(Object handler) {
		return handler instanceof Controller;
	}

	@Override
	public ModelAndView handle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		return ((Controller) handler).execute(req, res);
	}
}
