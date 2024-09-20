package com.example.questApp.business.abstracts;

import com.example.questApp.business.requests.UserRegisterRequest;
import com.example.questApp.core.utilities.results.Result;


public interface AuthRegisterService {
    Result registerUser(UserRegisterRequest request);
}
