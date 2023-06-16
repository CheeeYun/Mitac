package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MemberRepository;

@Service
public class MemberService {
	@Autowired
	MemberRepository memberRepository;
	
	public boolean allowMember(String username) {
			if(	memberRepository.allowMember(username).equals(0)) {
				return true;
			}
			return false;
	}
	
	
}
