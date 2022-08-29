package com.druid.usermanager.backbeans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.druid.usermanager.entities.User;
import com.druid.usermanager.services.UserService;

@Named(value = "userManagerBackbean")
@ViewScoped
public class UserManagerBackbean implements Serializable {
	
	
	@Autowired
	private UserService userService;
	
	private List<User> usersList;
	
	@PostConstruct
	private void init() {
		usersList = this.userService.listUsers();
	}

	public List<User> getUsersList() {
		return usersList;
	}

	public void setUsersList(List<User> usersList) {
		this.usersList = usersList;
	}
	
	

}
