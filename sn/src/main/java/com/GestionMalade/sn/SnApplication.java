package com.GestionMalade.sn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.GestionMalade")
@EntityScan("com.GestionMalade.entity")
@EnableJpaRepositories("com.GestionMalade.Repos")

public class SnApplication {

	public static void main(String[] args) {
		SpringApplication.run(SnApplication.class, args);
	}

}
