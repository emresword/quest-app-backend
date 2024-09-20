package com.example.questApp.business.requests;



import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class FollowerRequest {
    private Long followerUserId;
    private Long followedUserId;
}
