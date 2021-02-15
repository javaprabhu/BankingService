package com.example.demo;

import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public LinkedBlockingQueue<String> passbookQueue(){
		return new LinkedBlockingQueue<String>();
	}
	
	@Bean
	public LinkedBlockingQueue<String> cashDeposit(){
		return new LinkedBlockingQueue<String>();
	}
	
	@Bean
	public LinkedBlockingQueue<String> demandDraft(){
		return new LinkedBlockingQueue<String>();
	}

}
