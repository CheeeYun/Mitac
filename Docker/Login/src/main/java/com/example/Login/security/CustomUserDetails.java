package com.example.Login.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.Login.model.User;

public class CustomUserDetails implements UserDetails{
	private  String username;
	private  String password;
	private  String role;
	
	public CustomUserDetails(String username, String password, String role) {
		super();
		this.username = username;
		this.password = password;
		this.role = role;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println("@@"+this.role);
		return Arrays.asList(new SimpleGrantedAuthority("ROLE_"+this.role));
	}
	
	@Override
	public String getPassword() {
		
		return this.password;
	}

	@Override
	public String getUsername() {
		
		return this.username;
	}
	
	public String getRole() {
		return this.role;
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	
		return true;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

}
