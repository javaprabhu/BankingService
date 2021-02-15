package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dto.UserService;

public interface UserServiceRepository extends JpaRepository<UserService, Integer>{

	@Query("select u from UserService u where u.token=?1 and u.date=?2")
	public UserService findByToken(String token, String date);
}
