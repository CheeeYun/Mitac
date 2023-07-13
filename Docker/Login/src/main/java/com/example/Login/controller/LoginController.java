package com.example.Login.controller;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Login.model.User;
import com.example.Login.model.loginRequest;
import com.example.Login.repository.UserRepository;
import com.example.Login.security.CustomUserDetails;
import com.example.Login.security.CustomUserDetailsService;
import com.example.Login.security.JwtProvider;
import com.example.Login.service.UserService;


@RestController
@RequestMapping("api")
public class LoginController {
	
	 	@Autowired
	    AuthenticationManager authenticationManager;
	 	@Autowired
	 	PasswordEncoder passwordEncoder;
	 	@Autowired
	 	UserService userService;
	 	@Autowired
	 	UserRepository userRepository;
	 	@Autowired
	 	CustomUserDetailsService customUserDetailsService;
	 	@Autowired
	 	JwtProvider jwtProvider;
	 	
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody loginRequest requestBody)throws AuthenticationException {
		
			Authentication authentication=new UsernamePasswordAuthenticationToken(requestBody.getUsername(),requestBody.getPassword());
			String msg="";
			Authentication authenticated=authenticationManager.authenticate(authentication);
			SecurityContextHolder.getContext().setAuthentication(authenticated);
			System.out.println(SecurityContextHolder.getContext());
			
			String jwt=jwtProvider.generateJwtToken(authenticated);//製作jwt
			User user=userService.findUserByUsername(requestBody.getUsername());//更新jwt到資料庫
			user.setToken(jwt);
			userService.save(user);
			
			HttpHeaders headers = new HttpHeaders(); 
		    headers.add("Custom-Header", jwt);
		    
		    CustomUserDetails customUserDetails=customUserDetailsService.loadUserByUsername(requestBody.getUsername());
		    System.out.println("!!!"+customUserDetails.getAuthorities());
		    msg=requestBody.getUsername()+" "+"login success";
		    System.out.println(msg);
		    String role= jwtProvider.getJwtAuthentication(jwt);
		  
		    Map responseMap=new HashMap<>();
		    responseMap.put("jwt", jwt);
		    responseMap.put("username",requestBody.getUsername());
		   
			return new ResponseEntity(responseMap,HttpStatus.OK);
		
	}
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User user) {
		user.setUsername(user.getUsername());
		user.setRole("admin");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		System.out.println(user.getPassword());
		System.out.println(user.getUsername());
		userService.save(user);
		return user;
	}
	
//	@PreAuthorize("hasRole('NORMAL')")這個跟config擇一寫
	@GetMapping("/find")
	public ResponseEntity<?> findUser() {
		List<User> userList=userRepository.findAll();
		Map<String, Object> responseMap=new HashMap<>();
		responseMap.put("userList", userList);
		return new ResponseEntity(responseMap,HttpStatus.OK);
	}
	
	@PostMapping("/find/{username}")
	public ResponseEntity<?> findOne(@PathVariable String username){
		
		User user=userRepository.findUserByUsername(username);
		System.out.println(user);
		Map<String, Object> responseMap=new HashMap<>();
		responseMap.put("user", user);
		System.out.println(responseMap);
		return new ResponseEntity(responseMap,HttpStatus.OK);
	}
	
}
