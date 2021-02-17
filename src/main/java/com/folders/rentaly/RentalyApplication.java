package com.folders.rentaly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RentalyApplication { 

	public static void main(String[] args) {
		SpringApplication.run(RentalyApplication.class, args);
	}

}
