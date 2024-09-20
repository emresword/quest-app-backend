package com.example.questApp.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.questApp.business.abstracts.AuthLoginService;
import com.example.questApp.business.abstracts.AuthRegisterService;
import com.example.questApp.business.requests.UserLoginRequest;
import com.example.questApp.business.requests.UserRegisterRequest;
import com.example.questApp.core.utilities.results.Result;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final AuthRegisterService authRegisterService;
    private final AuthLoginService authLoginService;

    @Autowired
    public AuthController(AuthRegisterService authRegisterService, AuthLoginService authLoginService) {
        this.authRegisterService = authRegisterService;
        this.authLoginService = authLoginService;
    }

    @PostMapping("/register")
    public Result registerUser(@RequestBody UserRegisterRequest request) {
        return authRegisterService.registerUser(request);
    }

    @PostMapping("/login")
    public Result loginUser(@RequestBody UserLoginRequest request) {
        return authLoginService.loginUser(request);
    }
}
