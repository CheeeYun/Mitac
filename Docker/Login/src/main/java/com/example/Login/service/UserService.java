package com.example.Login.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.example.Login.model.User;
import com.example.Login.repository.UserRepository;



@Service
@Validated
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	
	   public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
	}
	   public void save(User user) {
		userRepository.save(user);
	   }
	   
	   
	
}
