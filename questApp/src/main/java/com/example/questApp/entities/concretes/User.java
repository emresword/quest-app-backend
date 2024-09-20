package com.example.questApp.entities.concretes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "userName")
	private String userName;
	@Column(name = "password")
	private String password;
	
	@Column(name="description")
	private String description;

	@Column(name = "avatar_id",nullable = true)
	private Integer avatarId;
	
	@Column(name = "email")
	private String email;
	@Column(name = "flirt_id")
	private Long flirtId;

}