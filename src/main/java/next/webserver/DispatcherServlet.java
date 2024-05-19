package next.webserver;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	private RequestMapping rm;

	@Override
	public void init() {
		this.rm = new RequestMapping();
		rm.initMapping();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI();
		logger.info("Method : {}, Request URI : {}", req.getMethod(), requestURI);

		Controller controller = rm.findController(requestURI);
		try {
			String viewName = controller.execute(req, resp);
			move(viewName, req, resp);
		} catch (Throwable e) {
			logger.error(e.getMessage());
			throw new ServletException(e);
		}
	}

	private void move(String viewName, HttpServletRequest req, HttpServletResponse resp) {
		if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
			try {
				resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			return;
		}
		RequestDispatcher rd = req.getRequestDispatcher(viewName);
		try {
			rd.forward(req, resp);
		} catch (ServletException | IOException e) {
			throw new RuntimeException(e);
		}
	}

}
