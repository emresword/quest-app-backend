package com.example.questApp.business.abstracts;

import java.util.List;

import com.example.questApp.entities.concretes.User;

public interface UserService {
	List<User> getAll();
	User add(User user);
	User getById(Long id);
	User update(Long id,User user);
	void delete(Long id);
	User getByUserName(String userName);
	
}
