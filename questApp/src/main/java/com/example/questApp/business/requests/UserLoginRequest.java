package com.example.questApp.business.requests;

import lombok.Data;

@Data
public class UserLoginRequest {
	String userName;
	String password;

}