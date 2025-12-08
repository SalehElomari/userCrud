package com.se;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages = "com.se")

public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		Environment environment = SpringApplication.run(DemoApplication.class, args).getEnvironment();
		String port = environment.getProperty("server.port");
		System.out.println("Spring Boot application running on port: " + port);

	}

}
