package com.example.Login.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtTokenFilter extends OncePerRequestFilter{
@Autowired
CustomUserDetailsService customUserDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(!(request.getServletPath().equals("/api/login")||request.getServletPath().equals("/api/register"))) { //登入和註冊不須驗證jwt
			String authorHeader =request.getHeader(AUTHORIZATION);
			String bearer ="Bearer ";
			System.out.println(request.getServletPath());
			System.out.println( authorHeader);
			if(authorHeader!=null && authorHeader.startsWith(bearer)) { 
				try {
					String token =authorHeader.substring(bearer.length());
					Claims claims=Jwts.parser().setSigningKey("myFirstSpringSecurityProject")
							.parseClaimsJws(token).getBody();
					System.out.println("Jwt payload="+claims.toString());
					
					CustomUserDetails userDetails=customUserDetailsService.loadUserByUsername(claims.getSubject());
					if(userDetails !=null) {
						UsernamePasswordAuthenticationToken authenticationToken=
							new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
						SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					}
					filterChain.doFilter(request, response);
				}catch(Exception e){
					System.out.println("Error "+e);
					response.setStatus(FORBIDDEN.value()); //403
					
					Map<String,String> err=new HashMap<>();
					err.put("jwt_err", e.getMessage());
					response.setContentType(APPLICATION_JSON_VALUE);
					new ObjectMapper().writeValue(response.getOutputStream(), err);
				}
			}else {
				response.setStatus(UNAUTHORIZED.value()); //401
				System.out.println("You dont have jwt!!");
			}
		}else {
			filterChain.doFilter(request, response);
		}
		
	}

}
