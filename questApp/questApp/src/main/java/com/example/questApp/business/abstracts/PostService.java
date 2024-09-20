package com.example.questApp.business.abstracts;

import java.util.List;
import java.util.Optional;

import com.example.questApp.business.requests.PostCreateRequest;
import com.example.questApp.business.requests.PostUpdateRequest;
import com.example.questApp.business.responses.PostResponse;
import com.example.questApp.entities.concretes.Post;

public interface PostService {
	List<PostResponse> getAll(Optional<Long> userId);
	Post add(PostCreateRequest postCreateRequest);
	Post getById(Long id);
	Post update(Long id,PostUpdateRequest postUpdateRequest);
	void delete();
}