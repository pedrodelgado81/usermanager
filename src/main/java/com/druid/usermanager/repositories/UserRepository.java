package com.druid.usermanager.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.druid.usermanager.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	List<User> findByEmail(String email);

	User findFirstByEmail(String email);
	
}
