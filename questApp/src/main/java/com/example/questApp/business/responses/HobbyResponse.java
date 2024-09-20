package com.example.questApp.business.responses;

import com.example.questApp.entities.concretes.Hobby;

import lombok.Data;
@Data
public class HobbyResponse {
	private Long id;
	private String hobbyName;
	private Long userId;
	private Long hobbyId;
	public HobbyResponse(Hobby hobby) {
		
		this.id = hobby.getId();
		this.hobbyName = hobby.getHobbyName();
		this.userId = hobby.getUser().getId();
		this.hobbyId=hobby.getHobbyId();
	}
	

}
