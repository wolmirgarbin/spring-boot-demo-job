package com.example.demojobag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoJobAgApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJobAgApplication.class, args);
	}
}
