package com.gotruck.shipperservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ShipperServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShipperServiceApplication.class, args);
	}

}