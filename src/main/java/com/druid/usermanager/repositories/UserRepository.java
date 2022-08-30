package com.druid.usermanager.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.druid.usermanager.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByEmailAndDeletionDateIsNull(String email);
	
	List<User> findByDeletionDateNull();
	
}
