package com.example.Login.security;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.Login.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JwtProvider {

	
	private String jwtSerect="myFirstSpringSecurityProject";
	
	private int Expiration=60*60;
	
//	public String generateJwtToken(Authentication authentication) {
//		CustomUserDetails userDetails=(CustomUserDetails) authentication.getPrincipal();
//		Map<String, Object> claimsMap =new HashMap<>();
//	
//		claimsMap.put("role",userDetails.getRole());
//
//		 		
//		
//			return Jwts.builder()
//					.setClaims(claimsMap)
//					.setSubject(userDetails.getUsername())
//					.setIssuedAt(new Date())
//					.setExpiration(new Date(new Date().getTime()+Expiration*1000))
//					.signWith(SignatureAlgorithm.HS512,jwtSerect)
//					.compact();
//		
//	}
	public String generateJwtToken(CustomUserDetails customUserDetails) {
		
		Map<String, Object> claimsMap =new HashMap<>();
	
		claimsMap.put("role",customUserDetails.getRole());

		 		
		
			return Jwts.builder()
					.setClaims(claimsMap)
					.setSubject(customUserDetails.getUsername())
					.setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime()+Expiration*1000))
					.signWith(SignatureAlgorithm.HS512,jwtSerect)
					.compact();
		
	}
	public String getJwtAuthentication(String jwt) {
		
		Claims claims=Jwts.parser().setSigningKey("myFirstSpringSecurityProject")
				.parseClaimsJws(jwt).getBody();
		System.out.println(claims.get("role"));
		String role=claims.get("role").toString();
		return role;
	}

}
