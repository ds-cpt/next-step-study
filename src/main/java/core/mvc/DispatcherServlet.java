package core.mvc;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.ControllerHandlerAdapter;
import core.nmvc.HandlerAdapter;
import core.nmvc.HandlerExecution;
import core.nmvc.HandlerExecutionHandlerAdapter;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
	private List<HandlerMapping> mappings = Lists.newArrayList();
	private List<HandlerAdapter> handlerAdapters = Lists.newArrayList();

	private static void render(HttpServletRequest req, HttpServletResponse resp, ModelAndView execute) throws
		Exception {
		View view = execute.getView();
		view.render(execute.getModel(), req, resp);
	}

	@Override
	public void init() throws ServletException {
		LegacyRequestMapping legacyRequestMapping = new LegacyRequestMapping();
		legacyRequestMapping.initMapping();
		AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("next.controller");
		annotationHandlerMapping.initialize();

		mappings.add(legacyRequestMapping);
		mappings.add(annotationHandlerMapping);

		handlerAdapters.add(new HandlerExecutionHandlerAdapter());
		handlerAdapters.add(new ControllerHandlerAdapter());

	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object handler = getHandler(req);
		if (handler == null) {
			throw new IllegalArgumentException("존재하지 않는 URL입니다.");
		}

		try {
			ModelAndView mav = execute(handler, req, resp);
			View view = mav.getView();
			view.render(mav.getModel(), req, resp);

		} catch (Throwable e) {
			logger.error("Exception : {}", e);
			throw new ServletException(e.getMessage());
		}
	}
	private Object getHandler(HttpServletRequest req) {
		for (HandlerMapping handlerMapping : mappings) {
			Object handler = handlerMapping.getHandler(req);
			if (handler != null) {
				return handler;
			}
		}
		return null;
	}

	private ModelAndView execute(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		for (HandlerAdapter handlerAdapter : handlerAdapters) {
			if (handlerAdapter.supports(handler)) {
				return handlerAdapter.handle(req, resp, handler);
			}
		}
		throw new IllegalArgumentException("존재하지 않는 핸들러입니다.");
	}


}
