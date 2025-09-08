package com.example.LendBuddy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class 	LendBuddyApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LendBuddyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("The server for Lend Buddy has been started.");
	}
}
