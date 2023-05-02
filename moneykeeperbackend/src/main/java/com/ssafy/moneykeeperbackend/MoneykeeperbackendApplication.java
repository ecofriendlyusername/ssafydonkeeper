package com.ssafy.moneykeeperbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoneykeeperbackendApplication {

	public static void main(String[] args) { SpringApplication.run(MoneykeeperbackendApplication.class, args); }

}
