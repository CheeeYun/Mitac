package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Hero;
import com.example.demo.repository.HeroRepository;
import com.example.demo.service.HeroService;


@RestController
public class HeroController {

	@Autowired
	private HeroService heroService;
	@Autowired
	private HeroRepository heroRepository;
	
	
	@GetMapping("/home/heroes")
	public List<Hero> getAllHeroes(){
		List<Hero> heroes=heroRepository.findAll();
		System.out.print(heroes.toString());
		return heroes;
	}
	
	@GetMapping("/home/heroes/{heroId}")
	 public Hero getHero(@PathVariable Integer heroId) {
		Hero hero=heroRepository.findByHeroId(heroId);
		return hero;
	}
	
	@PutMapping("/home/heroes/{heroId}")
	public ResponseEntity<Hero> updateHero(@RequestBody Hero hero,@PathVariable int heroId) {
		 	Hero usedHero =heroRepository.findByHeroId(heroId);
		if(heroService.existsHeroById(heroId)) {
			usedHero.setName(hero.getName());
			Hero updateHero=heroRepository.save(usedHero);
			return ResponseEntity.ok(updateHero);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/home/heroes")
	public ResponseEntity<Hero> addHero(@RequestBody Hero hero){
	    Integer maxHeroId = heroService.getMaxHeroId();
	    System.out.print(maxHeroId);
	  
	    Hero newHero = new Hero();
	    newHero.setName(hero.getName());
	    newHero.setHeroId(maxHeroId);
	    heroRepository.save(newHero);
	    
	    return ResponseEntity.ok(newHero);
	}

	@DeleteMapping("/home/heroes/{heroId}")
	public ResponseEntity<Hero> deleteHero(@PathVariable Integer heroId){
		
		if(heroService.existsHeroById(heroId)) {
			heroService.deleteByHeroId(heroId);
			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
