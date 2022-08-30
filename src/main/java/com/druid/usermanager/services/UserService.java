package com.druid.usermanager.services;

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
	public User findUserByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
	
	public List<User> listUsers() {
		return this.userRepository.findAll();
	}
	
	public void persistUser(User user) {
		//Ciframos el password del usuario
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		this.userRepository.saveAndFlush(user);
	}
	

}
