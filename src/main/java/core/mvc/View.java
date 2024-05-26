package core.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface View {
	void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
