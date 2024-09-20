package com.example.questApp.business.abstracts;

import java.util.List;
import java.util.Optional;

import com.example.questApp.business.requests.CommentCreateRequest;
import com.example.questApp.business.requests.CommentUpdateRequest;
import com.example.questApp.business.responses.CommentResponse;
import com.example.questApp.entities.concretes.Comment;

public interface CommentService {
	List<CommentResponse> getAll(Optional<Long> userId,Optional<Long> postId);

	Comment add(CommentCreateRequest commentCreateRequest);
	Comment getById(Long id);
	Comment update(Long id,CommentUpdateRequest commentUpdateRequest);
	void delete(Long id);

}