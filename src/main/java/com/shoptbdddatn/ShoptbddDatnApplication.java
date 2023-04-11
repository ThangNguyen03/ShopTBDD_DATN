package com.shoptbdddatn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ShoptbddDatnApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoptbddDatnApplication.class, args);
	}

}
