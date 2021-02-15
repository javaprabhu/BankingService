package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.request.TokenRequest;
import com.example.demo.request.UserFeedback;
import com.example.demo.request.UserRequest;
import com.example.demo.response.GenericResponse;
import com.example.demo.service.BankingService;

@RestController
//@RequestMapping("api/")
public class BankingServiceController {
	private Logger logger = LoggerFactory.getLogger(BankingServiceController.class);

	@Autowired
	private BankingService bankingService;
	
	@PostMapping("generateToken")
	public ResponseEntity<GenericResponse<String>> generateToken(@RequestBody TokenRequest tokenRequest) {
		logger.info("generateToken is being called for service: {}",tokenRequest);
		GenericResponse<String> response = bankingService.generateTokenForService(tokenRequest);
		return new ResponseEntity<GenericResponse<String>>(response, HttpStatus.OK);
	}
	
	@GetMapping("nextToken")
	public ResponseEntity<GenericResponse<String>> nextToken(@RequestParam("serviceName")String serviceName) {
		logger.info("nextToken invoked for service name: "+serviceName);
		GenericResponse<String> response = bankingService.nextToken(serviceName);
		return new ResponseEntity<GenericResponse<String>>(response, HttpStatus.OK);
	}
	
	@PutMapping("tokenService")
	public ResponseEntity<GenericResponse<String>> processToken(@RequestBody UserRequest userRequest) {
		logger.info("token service is invoked for :-{}",userRequest);
		GenericResponse<String> tokenService = bankingService.tokenService(userRequest);
		return new ResponseEntity<GenericResponse<String>>(tokenService, HttpStatus.OK);
	}
	
	@PutMapping("feedback")
	public ResponseEntity<GenericResponse<String>> feedback(@RequestBody UserFeedback feedback) {
		logger.info("feedback service is invoked for :-{}",feedback);
		GenericResponse<String> feedbackService = bankingService.feedbackService(feedback);
		return new ResponseEntity<GenericResponse<String>>(feedbackService,HttpStatus.OK);
	}
	
	@GetMapping("resetToken")
	public ResponseEntity<GenericResponse<String>> resetToken(){
		logger.info("reset token initialized");
		GenericResponse<String> resetToken = bankingService.resetToken();
		return new ResponseEntity<GenericResponse<String>>(resetToken, HttpStatus.OK);
	}
}
