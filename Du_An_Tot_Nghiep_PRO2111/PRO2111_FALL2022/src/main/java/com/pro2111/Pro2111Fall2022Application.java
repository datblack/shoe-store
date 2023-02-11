package com.pro2111;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class Pro2111Fall2022Application {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Pro2111Fall2022Application.class, args);
	}

}
