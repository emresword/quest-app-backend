package com.example.questApp.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    String message;
    Long userId;
    String accessToken;
    String refreshToken;  
}
