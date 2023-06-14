package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.demo.model.Hero;
import com.example.demo.repository.HeroRepository;



 	
@Service
public class HeroService {
	@Autowired
	private  HeroRepository heroRepository;
	
	public Integer getMaxHeroId() {
		Integer maxId =heroRepository.MaxHeroId();
		if(maxId== null) {
			maxId=10;
			return maxId;
		}
		else {
			return maxId+1;
		}
		
	}
	public void deleteByHeroId(int heroId) {
		heroRepository.deleteByHeroId(heroId);
	}
	
	public boolean existsHeroById(int heroId) {
		List<Hero> heroes=heroRepository.findAll();
		return heroes.stream()
				.anyMatch(hero->hero.getHeroId()== heroId);
	}
}
