package com.example.questApp.business.concretes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.questApp.business.abstracts.LikeService;
import com.example.questApp.business.abstracts.PostService;
import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.business.requests.PostCreateRequest;
import com.example.questApp.business.requests.PostUpdateRequest;
import com.example.questApp.business.responses.LikeResponse;
import com.example.questApp.business.responses.PostResponse;
import com.example.questApp.dataAccess.abstracts.PostDao;
import com.example.questApp.entities.concretes.Like;
import com.example.questApp.entities.concretes.Post;
import com.example.questApp.entities.concretes.User;

@Service
public class PostManager implements PostService {
	private PostDao postDao;
	private UserService userService;
	
	 @Autowired
	    @Lazy
	    private LikeService likeService;
	
	@Autowired
	public PostManager(PostDao postDao, UserService userService) {
		super();
	
		this.postDao = postDao;
		this.userService = userService;
	}
	 
	
	
	@Override
	public List<PostResponse> getAll(Optional<Long> userId) {
		List<Post> posts;
		if (userId.isPresent()) {
			posts =postDao.findByUserId(userId.get());
		}
			
		posts= postDao.findAll();
		return posts.stream().map(p->
		{List<LikeResponse> likes=   likeService.getAll(Optional.ofNullable(null), Optional.of(p.getId()));
	return	new PostResponse(p,likes) ;}).collect(Collectors.toList());
		
	}
	
	@Override
	public Post add(PostCreateRequest postCreateRequest) {
		User user = this.userService.getById(postCreateRequest.getUserId());
		if (user == null) 
			return null;
		
		Post toSavePost = new Post();
		toSavePost.setText(postCreateRequest.getText());
		toSavePost.setTitle(postCreateRequest.getTitle());
		toSavePost.setUser(user);
		return postDao.save(toSavePost);
	}
	
	@Override
	public Post getById(Long id) {
		return postDao.findById(id).orElse(null);
	}
	
	@Override
	public Post update(Long id, PostUpdateRequest postUpdateRequest) {
		Optional<Post> post = postDao.findById(id);
		if (post.isPresent()) {
			Post toUpdate = post.get();
			toUpdate.setText(postUpdateRequest.getText());
			toUpdate.setTitle(postUpdateRequest.getTitle());
			 postDao.save(toUpdate);
			 return toUpdate;
		}
		return null;
	}
	
	@Override
	public void delete() {
	
	}
}
