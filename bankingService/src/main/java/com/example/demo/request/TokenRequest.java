package com.example.demo.request;

public class TokenRequest {
	private String userName;
	private String account;
	private String serviceName;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "TokenRequest [userName=" + userName + ", account=" + account + ", serviceName=" + serviceName + "]";
	}
	
	
}
