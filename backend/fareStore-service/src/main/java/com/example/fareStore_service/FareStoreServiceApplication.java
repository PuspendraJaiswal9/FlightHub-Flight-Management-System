package com.example.fareStore_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FareStoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FareStoreServiceApplication.class, args);
	}

}
