package com.springtest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springtest.exception.UserNotFoundException;
import com.springtest.model.User;
import com.springtest.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public User addUser(User user) {
		if(userRepository.findByEmail(user.getEmail()).isPresent())
		{
			return null;
		}
		return userRepository.save(user);
	}
	
	public List<User> showAllUsers() {
		return userRepository.findAll();
	}
	
	public Optional<User> showUserById(long id) {
		return userRepository.findById(id);
	}
	
	public User updateUserById(User newUser,long id) {
		return userRepository.findById(id).
			map(user->{
				user.setName(newUser.getName());
				user.setUsername(newUser.getUsername());
				user.setEmail(newUser.getEmail());
				return userRepository.save(user);
			}).orElseThrow(()->new UserNotFoundException(id));
	}
	
	public String deleteUser(long id) {
		if (!userRepository.existsById(id)) {
			throw new UserNotFoundException(id);
		}
		userRepository.deleteById(id);
		return "User with id "+id+" has been deleted.";
	}
}
