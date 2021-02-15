package com.example.demo.request;

public class UserRequest {
	private String name;
	private String account;
	private String contact;
	private String query;
	private String token;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "UserRequest [name=" + name + ", account=" + account + ", contact=" + contact + ", query=" + query
				+ ", token=" + token + "]";
	}
}
