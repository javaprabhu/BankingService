package com.example.demo.exception;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.response.GenericResponse;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler{
	private Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<GenericResponse<String>> handleSqlException(SQLException sqlException) {
		logger.error("sql exception :{}", sqlException.getCause());
		GenericResponse<String> genericResponse = new GenericResponse<String>("Some error on database",500, null);
		return new ResponseEntity<>(genericResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<GenericResponse<String>> handleNullPointerException(NullPointerException sqlException) {
		logger.error("nullpointer exception :{}", sqlException.getCause());
		GenericResponse<String> genericResponse = new GenericResponse<String>("Seems data issue.",500, null);
		return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
