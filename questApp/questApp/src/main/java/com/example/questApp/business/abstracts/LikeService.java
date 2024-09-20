package com.example.questApp.business.abstracts;

import java.util.List;
import java.util.Optional;

import com.example.questApp.business.requests.LikeCreateRequest;
import com.example.questApp.business.responses.LikeResponse;
import com.example.questApp.entities.concretes.Like;

public interface LikeService {
	List<LikeResponse> getAll(Optional<Long> userId,Optional<Long> postId);
	Like add(LikeCreateRequest likeCreateRequest);
	Like getById(Long id);
	Like update(Long id,Like like);
	void delete(Long id);
}