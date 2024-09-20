package com.example.questApp.business.abstracts;

import java.util.List;
import java.util.Optional;

import com.example.questApp.business.requests.CommentCreateRequest;
import com.example.questApp.business.requests.CommentUpdateRequest;
import com.example.questApp.business.responses.CommentResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.entities.concretes.Comment;

public interface CommentService {

	DataResult<List<CommentResponse>> getAll(Optional<Long> userId, Optional<Long> postId);

	DataResult<Comment> add(CommentCreateRequest commentCreateRequest);

	DataResult<Comment> getById(Long id);

	DataResult<Comment> update(Long id, CommentUpdateRequest commentUpdateRequest);

	Result delete(Long id);

}