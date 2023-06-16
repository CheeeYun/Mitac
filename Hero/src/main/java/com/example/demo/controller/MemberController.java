package com.example.demo.controller;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.MemberService;

@CrossOrigin("*")
@RestController
public class MemberController {
	
	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;
	
	@GetMapping("/members")
	public ResponseEntity<?> getAllmembers(){
		List<Member> members =memberRepository.findAll();
		return ResponseEntity.ok(members);
	}
	
	@GetMapping("/members/{id}")
	public ResponseEntity<?> getMemberById(@PathVariable Integer id){
		Optional<Member> member =memberRepository.findById(id);
		if(member.isPresent()) {
			return ResponseEntity.ok(member);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("/checkMember")
	public boolean checkMember(@RequestBody Member member) {
		String username=member.getUsername();
		String password=member.getPassword();
		String pwd = memberRepository.findPasswordByUsername(username);
		if(pwd !=null&& pwd.equals(password)) {
			return true;
		}
		return false;
	}
	
	@PostMapping("/signIn")
	public ResponseEntity<?> addMember(@RequestBody Member member){
		Member newMember=new Member();
		if(memberService.allowMember(member.getUsername())){
			newMember=memberRepository.save(member);
			return ResponseEntity.ok(newMember);
		}
		else {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists!");
		}
		
	}
	

}
