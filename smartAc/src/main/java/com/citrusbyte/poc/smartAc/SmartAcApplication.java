package com.citrusbyte.poc.smartAc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.citrusbyte.poc.smartAc.web.dto.Notifications;

@ServletComponentScan
@SpringBootApplication
@ComponentScan("com.citrusbyte.poc.smartAc")
@EnableJpaRepositories("com.citrusbyte.poc.smartAc.persistence.repo")
@EntityScan("com.citrusbyte.poc.smartAc.persistence.model")
public class SmartAcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartAcApplication.class, args);
	}
	
	@Bean(name = "notifications")
	public Notifications getNotifications(){
		return new Notifications();
	}

}

