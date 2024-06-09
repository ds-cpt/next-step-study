package core.nmvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.reflections.ReflectionUtils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.HandlerMapping;

public class AnnotationHandlerMapping implements HandlerMapping {
	private Object[] basePackage;

	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}

	public void initialize() {
		ControllerScanner controllerScanner = new ControllerScanner(basePackage);
		Map<Class<?>, Object> controllers = controllerScanner.getControllers();
		Set<Method> methods = getRequestMappingMethods(controllers.keySet());
		for (Method method : methods) {
			RequestMapping rm = method.getAnnotation(RequestMapping.class);
			handlerExecutions.put(new HandlerKey(rm.value(), rm.method()), new HandlerExecution(
				controllers.get(method.getDeclaringClass()), method));

		}
	}

	@SuppressWarnings("unchecked")
	private Set<Method> getRequestMappingMethods(Set<Class<?>> classes) {
		Set<Method> requestMappingMethods = Sets.newHashSet();
		for (Class<?> clazz : classes) {
			requestMappingMethods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(
				RequestMapping.class)));
		}
		return requestMappingMethods;
	}

	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}
}
