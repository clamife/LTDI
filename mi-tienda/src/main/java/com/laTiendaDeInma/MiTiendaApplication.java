package com.laTiendaDeInma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.laTiendaDeInma.repository")
@SpringBootApplication
public class MiTiendaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiTiendaApplication.class, args);
	}

}
