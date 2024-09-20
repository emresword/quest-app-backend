package com.example.questApp.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questApp.business.abstracts.FollowerService;
import com.example.questApp.business.requests.FlirtIdRequest;
import com.example.questApp.business.requests.FollowerRequest;
import com.example.questApp.business.responses.FollowerResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.entities.concretes.User;

@RestController
@RequestMapping("/followers")
@CrossOrigin
public class FollowerController {

    private final FollowerService followerService;

    @Autowired
    public FollowerController(FollowerService followerService) {
        this.followerService = followerService;
    }

    @GetMapping
    public DataResult<List<FollowerResponse>> getAll(@RequestParam Optional<Long> userId) {
        return followerService.getAll(userId);
    }

    @PostMapping("/add")
    public FollowerResponse add(@RequestBody FollowerRequest request) {
        return followerService.add(request);
    }

    @DeleteMapping("/{followerId}")
    public Result delete(@PathVariable("followerId") Long followerId) {
        return followerService.delete(followerId);
    }

    @PostMapping("/follow")
    public Result followUser(@RequestParam Long followerUserId, @RequestParam Long followedUserId) {
        return followerService.followUser(followerUserId, followedUserId);
    }

    @PostMapping("/unfollow")
    public Result unfollowUser(@RequestParam Long followerUserId, @RequestParam Long followedUserId) {
        return followerService.unfollowUser(followerUserId, followedUserId);
    }
    @GetMapping("/followers/{userId}")
    public DataResult<List<FollowerResponse>> getFollowers(@PathVariable Long userId) {
        return followerService.getFollowers(userId);
    }

    @GetMapping("/following/{userId}")
    public DataResult<List<FollowerResponse>> getFollowing(@PathVariable Long userId) {
        return followerService.getFollowing(userId);
    }
    @PostMapping("/setFlirtId")
    public Result setFlirtId(@RequestBody FlirtIdRequest request) {
        return followerService.setFlirtIdIfFollowing(request.getUserId(), request.getFlirtId());
    }
   
    @GetMapping("/isMatchedFlirt")
    public DataResult<Boolean>  isMatchedFlirt(@RequestParam Long userId) {
        return followerService.isMatchedFlirt(userId);
    }

    @GetMapping("/findMutualFlirt")
    public DataResult<Boolean> findMutualFlirt(@RequestParam Long userId, @RequestParam Long flirtId) {
        return followerService.findMutualFlirt(userId, flirtId);
       
    }

}


