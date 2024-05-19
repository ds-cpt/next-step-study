package next.webserver;

import java.util.HashMap;
import java.util.Map;

import next.controller.CreateUserController;
import next.controller.ForwardController;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.LogoutController;
import next.controller.ProfileController;
import next.controller.UpdateFormUserController;
import next.controller.UpdateUserController;

public class RequestMapping {
	private Map<String, Controller> mappings = new HashMap<>();

	public Controller findController(String url) {
		return mappings.get(url);
	}
	public void put(String url, Controller controller){
		mappings.put(url, controller);
	}

	public void initMapping() {
		mappings.put("/", new HomeController());
		mappings.put("/users/create", new CreateUserController());
		mappings.put("/users", new ListUserController());
		mappings.put("/users/login", new LoginController());
		mappings.put("/users/logout", new LogoutController());
		mappings.put("/users/update", new UpdateUserController());
		mappings.put("/users/profile", new ProfileController());
		mappings.put("/users/updateForm", new UpdateFormUserController());

		mappings.put("/users/form", new ForwardController("/user/form.jsp"));
		mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
	}

}
