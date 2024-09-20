package com.example.questApp.business.requests;



import lombok.Data;

@Data
public class UserRegisterRequest {
    
    private String userName;
    private String password;
    private String confirmPassword;
    private String email;
}

