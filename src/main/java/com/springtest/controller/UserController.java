package com.springtest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springtest.exception.UserExistException;
import com.springtest.exception.UserNotFoundException;
import com.springtest.model.User;
import com.springtest.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin("http://localhost:3000")
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	@PostMapping
	public ResponseEntity<String> addUser(@RequestBody @Valid User newUser) {
			User addUser = userService.addUser(newUser);
			
			if(addUser==null) {
				throw new UserExistException();
			}
			
			return ResponseEntity.status(HttpStatus.CREATED).
			body("New user added.");
	}
	
	@GetMapping
	public List<User> showaAllUser() {
		return userService.showAllUsers();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> showUserById(@PathVariable long id) {
		return ResponseEntity.status(HttpStatus.OK).body(
				userService.showUserById(id).
				orElseThrow(()-> new UserNotFoundException(id)));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@RequestBody @Valid User user, @PathVariable long id) {
		return ResponseEntity.status(HttpStatus.OK).body(
				userService.updateUserById(user, id));
	}
	
	@DeleteMapping("/{id}")
	public String updateUser(@PathVariable long id) {
		return userService.deleteUser(id);
	}
		
}
