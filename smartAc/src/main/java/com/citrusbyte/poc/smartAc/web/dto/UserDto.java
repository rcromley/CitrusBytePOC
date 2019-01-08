package com.citrusbyte.poc.smartAc.web.dto;

public class UserDto {
	private String userName;
	
	public UserDto(String name) {
		this.userName = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
