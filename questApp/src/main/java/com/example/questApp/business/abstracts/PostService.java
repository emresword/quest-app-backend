package com.example.questApp.business.abstracts;

import java.util.List;
import java.util.Optional;

import com.example.questApp.business.requests.PostCreateRequest;
import com.example.questApp.business.requests.PostUpdateRequest;
import com.example.questApp.business.responses.PostResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.entities.concretes.Post;

public interface PostService {
    DataResult<List<PostResponse>> getAll(Optional<Long> userId);
    DataResult<Post> add(PostCreateRequest postCreateRequest);
    DataResult<Post> getById(Long id);
    DataResult<Post> update(Long id, PostUpdateRequest postUpdateRequest);
    Result delete(Long id);
}
