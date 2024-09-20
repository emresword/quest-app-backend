package com.example.questApp.business.responses;

import java.util.List;

import com.example.questApp.entities.concretes.Like;
import com.example.questApp.entities.concretes.Post;

import lombok.Data;

@Data
public class PostResponse {
	Long id;
	Long userId;
	String userName;
	String text;
	String title;
	List<LikeResponse> postLikes;
	//this is mapping but it is a little bit wrong you must use mapper service for these thing otherwise you must do this every single object
	public PostResponse(Post post,List<LikeResponse> likes) {
		this.id=post.getId();
		this.userId=post.getUser().getId();
		this.text=post.getText();
		this.title=post.getTitle();
		this.userName=post.getUser().getUserName();
		this.postLikes=likes;
	}
}