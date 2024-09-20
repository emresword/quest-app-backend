package com.example.questApp.business.responses;

import com.example.questApp.entities.concretes.Follower;

import lombok.Data;

@Data
public class FollowerResponse {
    private Long id;
    private Long followerUserId;
    private String followerUserName;
    private Long followedUserId;
    private String followedUserName;

    // Constructor using Follower object
    public FollowerResponse(Follower follower) {
        this.id = follower.getId();
        this.followerUserId = follower.getFollower().getId();
        this.followerUserName = follower.getFollower().getUserName(); // Ensure this method exists
        this.followedUserId = follower.getFollowed().getId();
        this.followedUserName = follower.getFollowed().getUserName(); // Ensure this method exists
    }

    // Constructor using IDs
    public FollowerResponse(Long id, Long followerUserId, String followerUserName, Long followedUserId, String followedUserName) {
        this.id = id;
        this.followerUserId = followerUserId;
        this.followerUserName = followerUserName;
        this.followedUserId = followedUserId;
        this.followedUserName = followedUserName;
    }
}
