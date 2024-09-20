package com.example.questApp.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.CommentService;
import com.example.questApp.business.abstracts.PostService;
import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.business.requests.CommentCreateRequest;
import com.example.questApp.business.requests.CommentUpdateRequest;
import com.example.questApp.business.responses.CommentResponse;
import com.example.questApp.core.utilities.results.DataResult;
import com.example.questApp.core.utilities.results.Result;
import com.example.questApp.core.utilities.results.SuccessDataResult;
import com.example.questApp.core.utilities.results.SuccessResult;
import com.example.questApp.core.utilities.results.ErrorDataResult;
import com.example.questApp.core.utilities.results.ErrorResult;
import com.example.questApp.dataAccess.abstracts.CommentDao;
import com.example.questApp.entities.concretes.Comment;
import com.example.questApp.entities.concretes.Post;
import com.example.questApp.entities.concretes.User;

@Service
public class CommentManager implements CommentService {

    private CommentDao commentDao;
    private UserService userService;
    private PostService postService;

    @Autowired
    public CommentManager(CommentDao commentDao, UserService userService, PostService postService) {
        this.commentDao = commentDao;
        this.userService = userService;
        this.postService = postService;
    }

    @Override
    public DataResult<List<CommentResponse>> getAll(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> comments;
        if (userId.isPresent() && postId.isPresent()) {
            comments = commentDao.findByUserIdAndPostId(userId.get(), postId.get());
        } else if (userId.isPresent()) {
            comments = commentDao.findByUserId(userId.get());
        } else if (postId.isPresent()) {
            comments = commentDao.findByPostId(postId.get());
        } else {
            comments = commentDao.findAll();
        }
        List<CommentResponse> responseList = comments.stream()
                .map(c -> new CommentResponse(c))
                .collect(Collectors.toList());
        return new SuccessDataResult<>(responseList, "Comments retrieved successfully.");
    }

    @Override
    public DataResult<Comment> getById(Long id) {
        Optional<Comment> comment = commentDao.findById(id);
        if (comment.isPresent()) {
            return new SuccessDataResult<>(comment.get(), "Comment retrieved successfully.");
        } else {
            return new ErrorDataResult<>("Comment not found.");
        }
    }

    @Override
    public Result delete(Long id) {
        if (commentDao.existsById(id)) {
            commentDao.deleteById(id);
            return new SuccessResult("Comment deleted successfully.");
        } else {
            return new ErrorResult("Comment not found.");
        }
    }

    @Override
    public DataResult<Comment> add(CommentCreateRequest commentCreateRequest) {
        DataResult<User> userResult = userService.getById(commentCreateRequest.getUserId());
        DataResult<Post> postResult = postService.getById(commentCreateRequest.getPostId());
        
        if (userResult.isSuccess() && postResult.isSuccess()) {
            Comment toSaveComment = new Comment();
            toSaveComment.setText(commentCreateRequest.getText());
            toSaveComment.setUser(userResult.getData());
            toSaveComment.setPost(postResult.getData());
            Comment savedComment = commentDao.save(toSaveComment);
            return new SuccessDataResult<>(savedComment, "Comment added successfully.");
        } else {
            return new ErrorDataResult<>("User or Post not found.");
        }
    }

    @Override
    public DataResult<Comment> update(Long id, CommentUpdateRequest commentUpdateRequest) {
        Optional<Comment> comment = commentDao.findById(id);
        if (comment.isPresent()) {
            Comment toUpdateComment = comment.get();
            toUpdateComment.setText(commentUpdateRequest.getText());
            Comment updatedComment = commentDao.save(toUpdateComment);
            return new SuccessDataResult<>(updatedComment, "Comment updated successfully.");
        } else {
            return new ErrorDataResult<>("Comment not found.");
        }
    }
}
