package io.mglobe.customerdetails;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author sakawaelijahbob
 *
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.kcbgroup")
public class Application {
	public static final Logger LOG = LogManager.getLogger(Application.class);
	
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
		LOG.info("===================================== STARTING ====================================");
		LOG.info("================== KCB HAKIKISHA CUSTOMER DETAILS - MICROSERVICE ==================");
		LOG.info("===================================================================================");

	}

}
