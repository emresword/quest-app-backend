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
import com.example.questApp.dataAccess.abstracts.CommentDao;
import com.example.questApp.entities.concretes.Comment;
import com.example.questApp.entities.concretes.Post;
import com.example.questApp.entities.concretes.User;
@Service
public class CommentManager implements CommentService{
	private CommentDao commentDao;
	private UserService userService;
	private PostService postService;
	
	@Autowired
	public CommentManager(CommentDao commentDao, UserService userService,PostService postService) {
		super();
		this.commentDao = commentDao;
		this.userService=userService;
		this.postService=postService;
	}

	@Override
	public List<CommentResponse> getAll(Optional<Long> userId,Optional<Long> postId) {
		List<Comment> comments;
		if(userId.isPresent()&&postId.isPresent()) {
			comments= commentDao.findByUserIdAndPostId(userId.get(),postId.get());
		}else if (userId.isPresent()) {
			comments= commentDao.findByUserId(userId.get());
		}else if (postId.isPresent()) {
			comments= commentDao.findByPostId(postId.get());
		}else {
			comments= commentDao.findAll();
		}
		return comments.stream().map(c-> new CommentResponse(c)).collect(Collectors.toList());
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public Comment getById(Long id) {
		
		return commentDao.findById(id).orElse(null);
	}

	

	@Override
	public void delete(Long id) {
		commentDao.deleteById(id);
		
	}

	@Override
	public Comment add(CommentCreateRequest commentCreateRequest) {
		User user = this.userService.getById(commentCreateRequest.getUserId());
		Post post=this.postService.getById(commentCreateRequest.getPostId());
		if (user !=null && post!=null) {
			Comment toSaveComment= new Comment();
			toSaveComment.setText(commentCreateRequest.getText());
			toSaveComment.setUser(user);
			toSaveComment.setPost(post);
			toSaveComment.setId(commentCreateRequest.getId());
			return commentDao.save(toSaveComment);
			
		}
		return null;
	
	}

	@Override
	public Comment update(Long id, CommentUpdateRequest commentUpdateRequest) {
		Optional<Comment> comment=commentDao.findById(id);
		if (comment.isPresent()) {
			Comment toUpdateComment=comment.get();
			toUpdateComment.setText(commentUpdateRequest.getText());
			commentDao.save(toUpdateComment);
			return toUpdateComment;
		}
		return null;
	}



}