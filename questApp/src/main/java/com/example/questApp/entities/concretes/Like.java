package com.example.questApp.entities.concretes;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="likes")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id",nullable=false)
	@OnDelete(action =OnDeleteAction.CASCADE)
	User user;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="post_id",nullable=false)
	@OnDelete(action =OnDeleteAction.CASCADE)
	Post post;
}