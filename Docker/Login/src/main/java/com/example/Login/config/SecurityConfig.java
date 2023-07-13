package com.example.Login.config;

import org.springframework.aop.Advisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.example.Login.security.CustomUserDetails;
import com.example.Login.security.CustomUserDetailsService;
import com.example.Login.security.JwtTokenFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	AuthenticationConfiguration authenticationConfiguration;
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	     http
        .csrf().disable()
        .cors().and()
  
        .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
 
        		.requestMatchers("/api/login").permitAll()
        		.requestMatchers("/api/register").permitAll()
        		.requestMatchers("/api/find").hasAnyRole("PRO")
        		.requestMatchers("/api/find/*").hasAnyRole("PRO","admin")
        		.anyRequest().authenticated());

	    
	    		return http.build();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean 		
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	 @Bean
	 public AuthenticationManager authenticationManager() throws Exception {
		 
	    return authenticationConfiguration.getAuthenticationManager();
	 }
	 @Bean
	 public DaoAuthenticationProvider authProvider() {
		 DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		 authProvider.setPasswordEncoder(passwordEncoder());
		 authProvider.setUserDetailsService(customUserDetailsService);
		 return authProvider;
	 }
	
}