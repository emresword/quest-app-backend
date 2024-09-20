package com.example.questApp.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.AuthRegisterService;
import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.business.mail.MailManager;
import com.example.questApp.business.requests.UserRegisterRequest;
import com.example.questApp.core.mail.MailService;
import com.example.questApp.core.utilities.results.ErrorResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.core.utilities.results.SuccessResult;
import com.example.questApp.entities.concretes.User;

@Service
public class AuthRegisterManager implements AuthRegisterService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService; 

    @Autowired
    public AuthRegisterManager(UserService userService, PasswordEncoder passwordEncoder,MailService mailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Override
    public Result registerUser(UserRegisterRequest request) {
        if (!checkIfPasswordsMatch(request.getPassword(), request.getConfirmPassword())) {
            return new ErrorResult("Password doesn't match.");
        }

        User user = new User();
        user.setUserName(request.getUserName());
       // user.setPassword(passwordEncoder.encode(request.getPassword())); // Encode password
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        System.out.println("name: " + request.getUserName());
	   
        Result result = userService.add(user);

        try {
            mailService.sendMail(user.getEmail());
        } catch (Exception e) {
            return new ErrorResult("Registration successful, but email could not be sent.");
        }

        return result;
    }

    private boolean checkIfPasswordsMatch(String password, String confirmPassword) {
    	  System.out.println("Password: " + password);
    	    System.out.println("Confirm Password: " + confirmPassword);
        return password.equals(confirmPassword);
    }


    private Result validateUser(User user, String confirmPassword) {
        if (!checkIfPasswordsMatch(user.getPassword(), confirmPassword)) {
            return new ErrorResult("Password doesn't match.");
        }
       
        return new SuccessResult("User validation successful.");
    }
}
