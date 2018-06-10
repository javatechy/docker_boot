package javatechy.dockboot.service;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class MyService {

	org.slf4j.Logger logger = LoggerFactory.getLogger(MyService.class);

	@Autowired
	private Environment environment;

	@PostConstruct
	public void postSetup() {
		print("BOOTAPP_USR");
		print("ENV_NAME");
		print("BOOTAPP_USR");
		print("SERVER_PORT");
	}

	private void print(String key) {
		logger.info("----------------------------" + key + "---------------------------");
		logger.info(environment.getProperty(key));
	}

}
