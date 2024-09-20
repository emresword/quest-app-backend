package com.example.questApp.business.abstracts;

import java.util.List;
import java.util.Optional;

import com.example.questApp.business.requests.LikeCreateRequest;
import com.example.questApp.business.responses.LikeResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.entities.concretes.Like;

public interface LikeService {
    DataResult<List<LikeResponse>> getAll(Optional<Long> userId, Optional<Long> postId);
    DataResult<Like> add(LikeCreateRequest likeCreateRequest);
    DataResult<Like> getById(Long id);
    Result delete(Long userId, Long postId);
}
