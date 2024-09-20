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
import com.example.questApp.dataAccess.abstracts.LikeDao;
import com.example.questApp.entities.concretes.Like;
import com.example.questApp.entities.concretes.Post;
import com.example.questApp.entities.concretes.User;

@Service
public class LikeManager implements LikeService {
	private LikeDao likeDao;
	private UserService userService;
	private PostService postService;

	@Autowired
	public LikeManager(LikeDao likeDao, UserService userService, PostService postService) {
		super();
		this.likeDao = likeDao;
		this.userService = userService;
		this.postService = postService;
	}

	@Override
	public List<LikeResponse> getAll(Optional<Long> userId, Optional<Long> postId) {

		List<Like> likes;
		if (userId.isPresent()&&postId.isPresent()) {
			likes=this.likeDao.findByUserIdAndPostId(userId.get(), postId.get());

		} else if (userId.isPresent()) {
			likes=this.likeDao.findByUserId(userId.get());
		} else if (postId.isPresent()) {
			likes=this.likeDao.findByPostId( postId.get());
		}

		else {
			likes=this.likeDao.findAll();
		}

		return likes.stream().map(l -> new LikeResponse(l)).collect(Collectors.toList());
	}

	@Override
	public Like add(LikeCreateRequest likeCreateRequest) {
		User user = this.userService.getById(likeCreateRequest.getUserId());
		Post post = this.postService.getById(likeCreateRequest.getPostId());
		if (user == null && post == null) {
			Like toSaveLike = new Like();
			toSaveLike.setId(likeCreateRequest.getId());
			toSaveLike.setUser(user);
			toSaveLike.setPost(post);
			  return likeDao.save(toSaveLike);
			
		}
		return null;
		

	}

	@Override
	public Like getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Like update(Long id, Like like) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		 if (likeDao.existsById(id)) {
	            likeDao.deleteById(id);
	        }

	}

}