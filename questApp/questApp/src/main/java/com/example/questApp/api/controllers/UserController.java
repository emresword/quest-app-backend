package com.example.questApp.api.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.questApp.business.abstracts.UserService;
import com.example.questApp.entities.concretes.User;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
	@Autowired
	private UserService userService;


	@PostMapping("/add")
	public User add(@RequestBody User user) {
		return this.userService.add(user);
	}
	@GetMapping("/getAll")
	public List<User> getAll(){
		System.out.print(this.userService.getAll());
		return this.userService.getAll();
		
	}
	@GetMapping("/{userId}")
	public User getById(Long id) {
		// TODO Auto-generated method stub
		return this.userService.getById(id);
	}
	
	
	
	@PutMapping("/{userId}")
	public User update(@PathVariable Long id,@RequestBody User user) {
		return this.userService.update(id, user);
		
		
	}
	@DeleteMapping("/{userId}")
	public void delete(@PathVariable Long id) {
		 this.userService.delete(id);
		
	}
}