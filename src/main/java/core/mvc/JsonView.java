package core.mvc;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonView implements View {

	@Override
	public void render(Map<String, ?> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ObjectMapper om = new ObjectMapper();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(om.writeValueAsString(createModel(req)));

	}

	private Map<String, Object> createModel(HttpServletRequest req) {
		Enumeration<String> attributeNames = req.getAttributeNames();
		Map<String, Object> model = new HashMap<>();
		while (attributeNames.hasMoreElements()) {
			String name = attributeNames.nextElement();
			model.put(name, req.getAttribute(name));
		}
		return model;
	}
}
