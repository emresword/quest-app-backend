package com.example.questApp.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.entities.concretes.Post;
import com.example.questApp.entities.concretes.User;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public DataResult<List<User>> getAll() {
        return this.userService.getAll();
    }

    @GetMapping("/{userId}")
    public DataResult<User> getById(@PathVariable("userId") Long id) {
        return this.userService.getById(id);
    }

    @PutMapping("/{userId}")
    public DataResult<User> update(@PathVariable("userId") Long id, @RequestBody User user) {
        return this.userService.update(id, user);
    }

    @DeleteMapping("/{userId}")
    public Result delete(@PathVariable("userId") Long id) {
        return this.userService.delete(id);
    }

    @GetMapping("/byUserName/{userName}")
    public DataResult<User> getByUserName(@PathVariable("userName") String userName) {
        return this.userService.getByUserName(userName);
    }

//    @PutMapping("/{userId}/updateAvatar")
//    public DataResult<User> updateAvatar(@PathVariable Long userId, @RequestBody Map<String, Integer> requestBody) {
//        int avatarId = requestBody.get("avatarId");
//        return userService.updateAvatar(userId, avatarId);
//    }
    @PutMapping("/{userId}/updateAvatar")
    public DataResult<User> updateAvatar(@PathVariable Long userId, @RequestBody Map<String, Integer> requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        
        return userService.updateAvatar(userId, requestBody.get("avatarId"), currentUsername);
    }

    @GetMapping("/{userId}/posts")
    public DataResult<List<Post>> getPostsByUserId(@PathVariable("userId") Long userId) {
        return this.userService.getPostsByUserId(userId);
    }
    @GetMapping("/searchUsers/{userName}")
    public DataResult<List<User>> getByUserNameContaining(@PathVariable("userName") String userName) {
        return this.userService.getByUserNameContaining(userName); // This may cause issues
    }
    
    @PutMapping("/updateDescription/{userId}")
    public DataResult<User> updateDescription(@PathVariable("userId") Long userId, @RequestBody Map<String, String> requestBody) {
        String description = requestBody.get("description");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        return this.userService.updateDescription(userId, description, currentUsername);
    }


    
}
