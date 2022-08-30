package com.druid.usermanager;

import java.util.Date;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.druid.usermanager.entities.User;
import com.druid.usermanager.services.UserService;

@SpringBootTest
//Indica que el orden de los test esta establecido por anotacion @Order
@TestMethodOrder(OrderAnnotation.class)
//Configuracion del contexto
public class UserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	@Order(1)
	public void userInsertion() {
		User user = new User();
		user.setEmail("testUser@testuser.com");
		user.setName("Test User Name");
		user.setSurname("Test User Surname");
		user.setBirthDate(new Date());
		user.setCreationDate(new Date());
		user.setPassword("asd");
		user.setRol("ROLE_USER");
		this.userService.persistUser(user);
		
		User insertedUser = this.userService.findUserByEmail("testUser@testuser.com");
		Assert.notNull(insertedUser, "The inserted user is not null");
		Assert.isTrue("testUser@testuser.com".equals(insertedUser.getEmail()), "The insertedUser searched by email");
	}
	
	@Test
	@Order(2)
	public void userDeletion() {				
		User toDeleteUser = this.userService.findUserByEmail("testUser@testuser.com");
		this.userService.removeUser(toDeleteUser);
		User deletedUser = this.userService.findUserByEmail("testUser@testuser.com");
		Assert.isNull(deletedUser, "The deletedUser user is deleted");
		
	}

}
