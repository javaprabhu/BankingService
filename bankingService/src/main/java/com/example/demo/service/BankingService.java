package com.example.demo.service;

import com.example.demo.request.TokenRequest;
import com.example.demo.request.UserFeedback;
import com.example.demo.request.UserRequest;
import com.example.demo.response.GenericResponse;

public interface BankingService {
	public GenericResponse<String> generateTokenForService(TokenRequest tokenRequest);
	public GenericResponse<String> nextToken(String serviceName);
	public GenericResponse<String> tokenService(UserRequest userRequest);
	public GenericResponse<String> feedbackService(UserFeedback feedback);
	public GenericResponse<String> resetToken();
}
