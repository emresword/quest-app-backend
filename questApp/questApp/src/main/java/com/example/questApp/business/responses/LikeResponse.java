package com.example.questApp.business.responses;

import com.example.questApp.entities.concretes.Like;

import lombok.Data;

@Data
public class LikeResponse {
	Long id;
	Long userId;

	Long postId;

	public LikeResponse(Like like) {
		super();
		this.id = like.getId();
		this.userId = like.getUser().getId();

		this.postId = like.getPost().getId();
	}

}
