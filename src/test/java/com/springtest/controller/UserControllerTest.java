package com.springtest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.springtest.model.User;
import com.springtest.service.UserService;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserService userService;
	
	List<User> users;
	
	@BeforeEach
	void initUsers() {
		users = new ArrayList<>();
		users.add(new User(1L,"Dwarkesh","dd","a@a.com"));
	}
	
//	@Test
//	void testAddUser() {
//		fail("Not yet implemented");
//	}

	@Test
	void testShowaAllUser() {
				
		when(userService.showAllUsers()).thenReturn(users);
		List<User> actUser = userController.showaAllUser();
		
		assertEquals(users, actUser);
	}

	@Test
	void testShowaAllUserById() {
		when(userService.showUserById(anyLong())).thenReturn(Optional.of(users.get(0)));
		ResponseEntity<User> actualUser = userController.showUserById(1L);
		assertEquals(users.get(0), actualUser.getBody());
	}
	@Test
	void testShowaAllUserById_Exception() throws Exception {
//		when(userService.showUserById(1L)).thenReturn(null);
//	    assertNull(userService.showUserById(1L));
		
	    MvcResult andReturn = mockMvc.perform(get("/user/1")).andExpect(status().isNotFound()).andReturn();
	    assertEquals(404, andReturn.getResponse().getStatus());
	}
 
}
