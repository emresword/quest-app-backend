package com.example.questApp.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.AuthLoginService;
import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.business.requests.UserLoginRequest;
import com.example.questApp.business.responses.AuthResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.ErrorResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.core.utilities.results.SuccessDataResult;
import com.example.questApp.core.utilities.results.SuccessResult;
import com.example.questApp.entities.concretes.User;
import com.example.questApp.security.jwt.JwtTokenProvider;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.AuthLoginService;
import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.business.requests.UserLoginRequest;
import com.example.questApp.business.responses.AuthResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.ErrorResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.core.utilities.results.SuccessDataResult;
import com.example.questApp.entities.concretes.User;
import com.example.questApp.security.jwt.JwtTokenProvider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.AuthLoginService;
import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.business.requests.UserLoginRequest;
import com.example.questApp.business.responses.AuthResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.ErrorResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.core.utilities.results.SuccessDataResult;
import com.example.questApp.entities.concretes.User;
import com.example.questApp.security.jwt.JwtTokenProvider;

@Service
public class AuthLoginManager implements AuthLoginService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthLoginManager(UserService userService, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Result loginUser(UserLoginRequest request) {
        DataResult<User> userDataResult = userService.getByUserName(request.getUserName());

        if (!userDataResult.isSuccess()) {
            return new ErrorResult("User not found.");
        }

        User user = userDataResult.getData();
        if (user == null) {
            return new ErrorResult("User not found.");
        }

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (passwordMatches) {
            String accessToken = jwtTokenProvider.generateJwtTokenByUserId(user.getId()); // Ensure this method is defined
            String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId()); // Ensure this method is defined
            AuthResponse authResponse = new AuthResponse("Login successful!", user.getId(), accessToken, refreshToken);

            return new SuccessDataResult<>(authResponse, "Login successful!");
        } else {
            return new ErrorResult("Invalid username or password.");
        }
    }
}
