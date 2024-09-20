package com.example.questApp.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.questApp.entities.concretes.Hobby;

public interface HobbyDao extends JpaRepository<Hobby, Long>{
	List<Hobby> findByUserId(Long userId);

}
