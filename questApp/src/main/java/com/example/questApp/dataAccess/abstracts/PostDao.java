package com.example.questApp.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.questApp.entities.concretes.Post;

public interface PostDao  extends JpaRepository<Post, Long>{

	List<Post> findByUserId(Long userId);

}