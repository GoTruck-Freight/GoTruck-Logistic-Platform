package com.gotruck.shipperservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@ComponentScan(basePackages = "com.gotruck.shipperservice")
@PropertySource("classpath:env.properties")
@EnableFeignClients
public class ShipperServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipperServiceApplication.class, args);
	}

}
