package com.example.questApp.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.questApp.business.abstracts.CommentService;
import com.example.questApp.business.requests.CommentCreateRequest;
import com.example.questApp.business.requests.CommentUpdateRequest;
import com.example.questApp.business.responses.CommentResponse;
import com.example.questApp.dataAccess.abstracts.CommentDao;
import com.example.questApp.entities.concretes.Comment;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	
	@GetMapping
	public List<CommentResponse> getAll(@RequestParam Optional<Long> userId,@RequestParam Optional<Long> postId) {
		// TODO Auto-generated method stub
		return commentService.getAll(userId,postId);
	}
	

	@PostMapping("/add")
	public Comment add(@RequestBody CommentCreateRequest commentCreateRequest) {
		// TODO Auto-generated method stub
		return commentService.add(commentCreateRequest);
	}

	@GetMapping("/{commentId}")
	public Comment getById(@PathVariable("commentId") Long id) {
		
		return commentService.getById(id);
	}

	@PutMapping("/{commentId}")
	public Comment update(@PathVariable("commentId" )  Long id,@RequestBody CommentUpdateRequest commentUpdateRequest) {
		// TODO Auto-generated method stub
		return commentService.update(id, commentUpdateRequest);
	}

	@DeleteMapping("/{commentId}")
	public void delete(@PathVariable("commentId" )  Long id) {
		commentService.delete(id);
		
	}

}