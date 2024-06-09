package core.nmvc;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerScannerTest {
	private static final Logger logger = LoggerFactory.getLogger(ControllerScannerTest.class);

	private ControllerScanner cf;

	@Before
	public void setup() throws InstantiationException, IllegalAccessException {
		cf = new ControllerScanner("core.nmvc");
	}

	@Test
	public void getCotrollers() {
		Map<Class<?>, Object> controllers = cf.getControllers();
		for (Class<?> controller : controllers.keySet()) {
			logger.debug("controller : {}", controller);
		}
	}

}