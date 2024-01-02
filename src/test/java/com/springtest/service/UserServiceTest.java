package com.springtest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.springtest.model.User;
import com.springtest.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testAddUser() {
		// Arrange
		User user = new User();
		user.setEmail("test@example.com");

		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
		when(userRepository.save(user)).thenReturn(user);

		// Act
		User addedUser = userService.addUser(user);

		// Assert
		assertEquals(user, addedUser);
		verify(userRepository, times(1)).findByEmail("test@example.com");
		verify(userRepository, times(1)).save(user);
	}

	@Test
	void testAddUser_EmailAlreadyExists() {
		// Arrange
		User user = new User();
		user.setEmail("test@example.com");

		when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

		// Act
		User addedUser = userService.addUser(user);

		// Assert
		assertNull(addedUser);
		verify(userRepository, times(1)).findByEmail("test@example.com");
		verify(userRepository, never()).save(user);
	}

	@Test
	void testShowAllUsers() {
		// Arrange
		List<User> users = new ArrayList<>();
		users.add(new User());
		users.add(new User());

		when(userRepository.findAll()).thenReturn(users);

		// Act
		List<User> result = userService.showAllUsers();

		// Assert
		assertEquals(users, result);
		verify(userRepository, times(1)).findAll();
	}

	@Test
	void testShowUserById() {
		// Arrange
		long id = 1;
		User user = new User();
		user.setId(id);

		when(userRepository.findById(id)).thenReturn(Optional.of(user));

		// Act
		Optional<User> result = userService.showUserById(id);

		// Assert
		assertTrue(result.isPresent());
		assertEquals(user, result.get());
		verify(userRepository, times(1)).findById(id);
	}

	@Test
	void testShowUserById_UserNotFound() {
		// Arrange
		long id = 1;

		when(userRepository.findById(id)).thenReturn(Optional.empty());

		// Act
		Optional<User> result = userService.showUserById(id);

		// Assert
		assertFalse(result.isPresent());
		verify(userRepository, times(1)).findById(id);
	}

}
