package com.example.Login.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.Login.model.User;

import jakarta.persistence.Id;

public interface UserRepository extends JpaRepository<User,Integer>{

	@Query(value = "SELECT * FROM login.User where username =?1",nativeQuery = true)
	 User findByUsername(String username);
	
	@Query(value = "SELECT * FROM login.User where username =?1",nativeQuery = true)
	 User findUserByUsername(String username);
	
	@Query(value="select * from User", nativeQuery = true)
	List<User> findAll();
	

	
}
