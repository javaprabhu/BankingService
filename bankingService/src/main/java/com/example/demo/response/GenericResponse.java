package com.example.demo.response;

public class GenericResponse<T> {
	private String errorMsg;
	private Integer code;
	private T data;
	
	public GenericResponse(T data) {
		this.code=200;
		this.data = data;
	}
	
	public GenericResponse(String errorMsg, Integer code, T data) {
		this.errorMsg = errorMsg;
		this.code = code;
		this.data = data;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "GenericResponse [errorMsg=" + errorMsg + ", code=" + code + ", data=" + data + "]";
	}
}
