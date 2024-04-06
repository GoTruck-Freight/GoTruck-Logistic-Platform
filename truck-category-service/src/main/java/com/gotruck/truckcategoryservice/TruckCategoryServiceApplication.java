package com.gotruck.truckcategoryservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.gotruck.truckcategoryservice.repository")
@ComponentScan(basePackages = {"com.gotruck.truckcategoryservice", "com.gotruck.truckcategoryservice.mapper"})
public class TruckCategoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruckCategoryServiceApplication.class, args);
	}
}
