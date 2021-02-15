package com.example.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.allService.ServiceEnum;
import com.example.demo.dto.Customer;
import com.example.demo.dto.UserService;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.UserServiceRepository;
import com.example.demo.request.TokenRequest;
import com.example.demo.request.UserFeedback;
import com.example.demo.request.UserRequest;
import com.example.demo.response.GenericResponse;
import com.example.demo.service.BankingService;

@Service
public class BankingServiceImpl implements BankingService{
	private Logger logger  = LoggerFactory.getLogger(BankingServiceImpl.class);
	private SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy");
	
	@Autowired
	private UserServiceRepository userServiceRepo;
	
	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private LinkedBlockingQueue<String> passbookQueue;
	
	@Autowired
	private LinkedBlockingQueue<String> cashDeposit;
	
	@Autowired
	private LinkedBlockingQueue<String> demandDraft;
	
	@Transactional
	public GenericResponse<String> generateTokenForService(TokenRequest tokenRequest) {
		Customer cust = customerRepo.findByAccount(tokenRequest.getAccount());
		if(cust==null) {
			logger.error("Wrong Account No.:-{}",tokenRequest.getAccount());
			return new GenericResponse<String>("Please enter valid account no.", 400, null);
		}
		if(!cust.getName().equalsIgnoreCase(tokenRequest.getUserName())) {
			logger.error("Entered name mismatched.:-{}",tokenRequest.getUserName());
			return new GenericResponse<String>("Enter name mismatched.", 400, null);
		}
		ServiceEnum enumByName = ServiceEnum.getEnumByName(tokenRequest.getServiceName());
		if(enumByName==null) {
			logger.error("Invalid Service name:-{}",tokenRequest.getServiceName());
			return new GenericResponse<String>("Please select valid Service Name", 400, null);
		}
		String token = enumByName.getToken();
		LinkedBlockingQueue<String> queue = getQueueByEnum(enumByName);
		queue.add(token);
		UserService userService = new UserService();
		userService.setAccount(tokenRequest.getAccount());
		userService.setName(tokenRequest.getUserName());
		userService.setDate(df.format(new Date()));
		userService.setStatus("STARTED");
		userService.setToken(token);
		userServiceRepo.save(userService);
		logger.info("Token {} added to queue against the service :-{}", token,tokenRequest.getServiceName());
		return new GenericResponse<String>(token);
	}
	
	public GenericResponse<String> nextToken(String serviceName) {
		ServiceEnum enumByName = ServiceEnum.getEnumByName(serviceName);
		if(enumByName==null) {
			return new GenericResponse<String>("Please select valid Service Name", 400, null);
		}
		LinkedBlockingQueue<String> queue = getQueueByEnum(enumByName);
		String peek = queue.peek();
		if(peek==null) {
			return new GenericResponse<String>("No token available right now.", 200, null);
		}
		return new GenericResponse<String>(peek);
	}
	
	@Transactional
	public GenericResponse<String> tokenService(UserRequest userRequest) {
		if(userRequest==null) {
			return new GenericResponse<String>("User data is missing", 400, null);
		}
		if(userRequest.getToken()!=null && !userRequest.getToken().contains("-")) {
			return new GenericResponse<String>("User data is missing along with Token", 400, null);
		}
		String serviceCode = userRequest.getToken().split("-")[0];
		ServiceEnum enumByCode = ServiceEnum.getEnumByCode(serviceCode);
		if(enumByCode==null) {
			return new GenericResponse<String>("Entered service code is invalid", 400, null);
		}
		LinkedBlockingQueue<String> queue = getQueueByEnum(enumByCode);
		if(queue==null || queue.size()==0) {
			return new GenericResponse<String>("No token is in queue", 200, null);
		}
		if(!queue.peek().equals(userRequest.getToken())) {
			return new GenericResponse<String>("Wrong token, Current token is:-"+queue.peek(), 400, null);
		}
		String queueToken = queue.poll();
		UserService userService = userServiceRepo.findByToken(queueToken, df.format(new Date()));
		userService.setContact(userRequest.getContact());
		userService.setQuery(userRequest.getQuery());
		userService.setStatus("PROCESSING");
		userServiceRepo.saveAndFlush(userService);
		logger.info("User:-{} has been served, Please ask for feedback.",userRequest.getName());
		return new GenericResponse<String>("User has been served, Please ask for feedback.");
	}
	
	@Transactional
	public GenericResponse<String> feedbackService(UserFeedback feedback) {
		if(feedback==null || feedback.getToken()==null) {
			return new GenericResponse<String>("Please write feedback against token.",400,null);
		}
		UserService userService = userServiceRepo.findByToken(feedback.getToken(), df.format(new Date()));
		if(userService==null) {
			return new GenericResponse<String>("Entered token is not served or invalid.",400,null);
		}
		userService.setFeedback(feedback.getFeedback());
		userService.setStatus(feedback.getStatus());
		userServiceRepo.save(userService);
		return new GenericResponse<String>("feedback submit successfully.");
	}
	
	public GenericResponse<String> resetToken(){
		if(passbookQueue.size()==0 && cashDeposit.size()==0 && demandDraft.size()==0) {
			ServiceEnum.resetValue();
			return new GenericResponse<String>("Reset value for all Service to 0.");
		}
		return new GenericResponse<String>("There are some token available to be processed.");
	}
	
	private LinkedBlockingQueue<String> getQueueByEnum(ServiceEnum sEnum) {
		switch (sEnum) {
		case PASSBOOKUPDATE:
			return passbookQueue;
		case CASHDEPOSIT:
			return cashDeposit;
		case DEMANDDRAFT:
			return demandDraft;
		}
		return null;
	}
}
