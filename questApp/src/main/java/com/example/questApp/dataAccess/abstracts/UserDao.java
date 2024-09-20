package com.example.questApp.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.questApp.entities.concretes.User;

public interface UserDao extends JpaRepository<User, Long>{

	User findByUserName(String username);
	
	List<User> findByFlirtId( Long flirtId);
	
	List<User> findByUserNameContaining(String username);
	
	


}