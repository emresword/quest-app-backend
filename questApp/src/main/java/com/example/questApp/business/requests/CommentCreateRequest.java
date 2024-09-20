package com.example.questApp.business.requests;

import lombok.Data;

@Data
public class CommentCreateRequest {
	Long id;
	String text;
	Long userId;
	Long postId;
}