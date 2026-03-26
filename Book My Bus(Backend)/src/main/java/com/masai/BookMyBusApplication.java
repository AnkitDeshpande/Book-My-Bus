package com.masai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BookMyBusApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMyBusApplication.class, args);
	}
}
