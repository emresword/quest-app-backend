package com.example.questApp.business.requests;

import java.util.List;

import lombok.Data;

@Data
public class HobbyCreateRequest {
	Long id;
	String hobbyName;
	Long userId;
	Long hobbyId;

}
