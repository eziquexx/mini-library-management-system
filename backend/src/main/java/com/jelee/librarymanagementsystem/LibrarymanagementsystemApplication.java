package com.jelee.librarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LibrarymanagementsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarymanagementsystemApplication.class, args);
	}

}
