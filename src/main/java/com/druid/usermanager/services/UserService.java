package com.druid.usermanager.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.druid.usermanager.entities.User;
import com.druid.usermanager.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	// El email del usuario debe ser unico, si no lo es devolvemos nulo
	public List<User> findUserByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
	
	public List<User> listUsers() {
		return this.userRepository.findAll();
	}
	

}
