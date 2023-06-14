package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Hero;

import jakarta.transaction.Transactional;
import java.util.List;


public interface HeroRepository extends JpaRepository<Hero,Integer>{
	 
	@Query(value="SELECT MAX(heroId)FROM hero",nativeQuery=true)
	Integer MaxHeroId();
	
	Hero findByHeroId(int heroId);
	
	List<Hero> findByName(String name);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM hero WHERE heroId = :heroId", nativeQuery = true)
	void deleteByHeroId(@Param("heroId") int heroId);
	
	List<Hero> findByNameContaining(String name);
}
