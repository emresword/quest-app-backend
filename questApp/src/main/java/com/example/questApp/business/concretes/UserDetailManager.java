package com.example.questApp.business.concretes;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.entities.concretes.User;
import com.example.questApp.security.jwt.JwtUserDetails;

@Service
public class UserDetailManager implements UserDetailsService {

    private final UserService userService;

    public UserDetailManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DataResult<User> userResult = userService.getByUserName(username);
        if (userResult.isSuccess()) {
            User user = userResult.getData();
            return JwtUserDetails.create(user);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    public UserDetails loadUserById(Long userId) {
        DataResult<User> userResult = userService.getById(userId);
        if (userResult.isSuccess()) {
            User user = userResult.getData();
            return JwtUserDetails.create(user);
        } else {
            throw new UsernameNotFoundException("User not found with ID: " + userId);
        }
    }
}
