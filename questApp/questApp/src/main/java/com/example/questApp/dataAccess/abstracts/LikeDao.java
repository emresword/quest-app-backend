package com.example.questApp.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.questApp.entities.concretes.Like;

public interface LikeDao  extends JpaRepository<Like, Long>{
	List<Like> findByUserIdAndPostId(Long userId,Long postId);

	List<Like> findByUserId(Long userId);
	List<Like> findByPostId(Long postId);
}