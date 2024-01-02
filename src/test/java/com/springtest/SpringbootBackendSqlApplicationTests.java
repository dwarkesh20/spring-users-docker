package com.springtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.springtest.model.User;
import com.springtest.repository.UserRepository;
import com.springtest.service.UserService;

@SpringBootTest
class SpringbootBackendSqlApplicationTests {

	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	
	
	@Test
	public void getUserTest() {
		List<User> users = new ArrayList<>();
		users.add(new User(1L,"Dwarkesh","dd","a@a.com"));
		
		when(userRepository.findAll()).thenReturn(users);
		List<User> actualUsers= userService.showAllUsers();
		assertEquals(users, actualUsers);
	}

}
