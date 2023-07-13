package com.example.Login.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Login.model.User;
import com.example.Login.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	 private final UserRepository userRepository;

	 public CustomUserDetailsService(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }
	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		CustomUserDetails userDetails=new CustomUserDetails(
				user.getUsername(),user.getPassword(),user.getRole());
		
		if(user == null) {
			throw new UsernameNotFoundException("此帳號不存在");
		}
		return userDetails;
	}

}
