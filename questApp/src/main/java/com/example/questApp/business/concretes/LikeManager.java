package com.example.questApp.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.LikeService;
import com.example.questApp.business.abstracts.PostService;
import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.business.requests.LikeCreateRequest;
import com.example.questApp.business.responses.LikeResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.ErrorDataResult;
import com.example.questApp.core.utilities.results.ErrorResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.core.utilities.results.SuccessDataResult;
import com.example.questApp.core.utilities.results.SuccessResult;
import com.example.questApp.dataAccess.abstracts.LikeDao;
import com.example.questApp.entities.concretes.Like;
import com.example.questApp.entities.concretes.Post;
import com.example.questApp.entities.concretes.User;

@Service
public class LikeManager implements LikeService {
    private final LikeDao likeDao;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public LikeManager(LikeDao likeDao, UserService userService, PostService postService) {
        this.likeDao = likeDao;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public DataResult<List<LikeResponse>> getAll(Optional<Long> userId, Optional<Long> postId) {
        List<Like> likes;
        if (userId.isPresent() && postId.isPresent()) {
            likes = likeDao.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            likes = likeDao.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            likes = likeDao.findByPostId(postId.get());
        } else {
            likes = likeDao.findAll();
        }
        List<LikeResponse> likeResponses = likes.stream().map(LikeResponse::new).collect(Collectors.toList());
        return new SuccessDataResult<>(likeResponses, "Likes retrieved successfully.");
    }

    @Override
    public DataResult<Like> add(LikeCreateRequest likeCreateRequest) {
        DataResult<User> userResult = userService.getById(likeCreateRequest.getUserId());
        DataResult<Post> postResult = postService.getById(likeCreateRequest.getPostId());

        if (!userResult.isSuccess() || !postResult.isSuccess()) {
            return new ErrorDataResult<>(null, "User or post not found.");
        }

        User user = userResult.getData();
        Post post = postResult.getData();

        List<Like> existingLikes = likeDao.findByUserIdAndPostId(likeCreateRequest.getUserId(), likeCreateRequest.getPostId());
        if (!existingLikes.isEmpty()) {
            return new ErrorDataResult<>(null, "You have already liked this post.");
        }

        Like toSaveLike = new Like();
        toSaveLike.setUser(user);
        toSaveLike.setPost(post);
        Like savedLike = likeDao.save(toSaveLike);
        return new SuccessDataResult<>(savedLike, "Like added successfully.");
    }

    @Override
    public DataResult<Like> getById(Long id) {
        Optional<Like> like = likeDao.findById(id);
        if (like.isPresent()) {
            return new SuccessDataResult<>(like.get(), "Like retrieved successfully.");
        }
        return new ErrorDataResult<>("Like not found.");
    }

    @Override
    public Result delete(Long userId, Long postId) {
        List<Like> likes = likeDao.findByUserIdAndPostId(userId, postId);
        if (!likes.isEmpty()) {
            likeDao.delete(likes.get(0));
            return new SuccessResult("Like deleted successfully.");
        }
        return new ErrorResult("Like not found for the specified user and post.");
    }
}
