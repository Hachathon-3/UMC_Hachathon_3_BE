package com.example.anxiety_senpai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AnxietySenpaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnxietySenpaiApplication.class, args);
	}

}
