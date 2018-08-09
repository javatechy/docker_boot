package javatechy.dockboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	private Logger logger = LoggerFactory.getLogger(HelloController.class);

	@GetMapping(value = "/hello")
	public String hello() {
		logger.info("Hello info  is reached");
		logger.debug("Hello debug  is reached");
		logger.error("Hello error  is reached");
		return "Hello response";
	}

}
