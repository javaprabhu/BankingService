package com.example.demo.request;

public class UserFeedback {
	private String token;
	private String feedback;
	private String status;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "UserFeedback [token=" + token + ", feedback=" + feedback + ", status=" + status + "]";
	}

}
