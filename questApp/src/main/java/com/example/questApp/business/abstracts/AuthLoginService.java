package com.example.questApp.business.abstracts;

import com.example.questApp.business.requests.UserLoginRequest;
import com.example.questApp.core.utilities.results.Result;

public interface AuthLoginService {
	Result loginUser(UserLoginRequest request);
}
