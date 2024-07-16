package com.user.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * This is main class to start the application
 * @author Jitendra
 */
@SpringBootApplication
public class UserAuthenticatiorApplication {

	
	public static void main(String... args) {
		SpringApplication.run(UserAuthenticatiorApplication.class, args);
	}

}
