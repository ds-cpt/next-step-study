package core.nmvc;

import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class ControllerScanner {
	private static final Logger log = LoggerFactory.getLogger(ControllerScanner.class);
	private Reflections reflections;

	public ControllerScanner(Object... basePackage) {
		reflections = new Reflections(basePackage);
	}

	public Map<Class<?>, Object> getControllers() {
		Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(core.annotation.Controller.class);
		return instantiateControllers(typesAnnotatedWith);
	}

	private Map<Class<?>, Object> instantiateControllers(Set<Class<?>> typesAnnotatedWith) {
		Map<Class<?>, Object> controllers = Maps.newHashMap();
		for (Class<?> clazz : typesAnnotatedWith) {
			try {
				controllers.put(clazz, clazz.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				log.error(e.getMessage());
			}
		}

		return controllers;
	}

}
