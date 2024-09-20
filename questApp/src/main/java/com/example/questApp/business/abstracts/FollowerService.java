package com.example.questApp.business.abstracts;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;

import com.example.questApp.business.requests.FollowerRequest;
import com.example.questApp.business.responses.FollowerResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.entities.concretes.User;

public interface FollowerService {
	FollowerResponse add(FollowerRequest request);

	DataResult<List<FollowerResponse>> getAll(Optional<Long> userId);

	Result delete(Long followerId);

	Result followUser(Long followerUserId, Long followedUserId);

	Result unfollowUser(Long followerUserId, Long followedUserId);

	DataResult<List<FollowerResponse>> getFollowers(Long userId);

	DataResult<List<FollowerResponse>> getFollowing(Long userId);
	
	Result setFlirtIdIfFollowing(Long userId, Long flirtId);
	
	DataResult<List<User>>  findFlirt(Long userId);
	
	DataResult<Boolean> findMutualFlirt( Long userId,  Long flirtId);
	DataResult<Boolean> isMatchedFlirt(Long userId);

}
