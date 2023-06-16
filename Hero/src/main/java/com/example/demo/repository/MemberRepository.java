package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{
	@Query(value="SELECT password FROM hero.member where username=?1",nativeQuery=true)
	String findPasswordByUsername(String username);
	
	@Query(value="SELECT count(*) FROM hero.member where username=?1",nativeQuery =true)
	Integer allowMember (String username);
}