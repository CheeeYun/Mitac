package com.example.demo.controller;

import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Hero;
import com.example.demo.model.HeroAssembler;
import com.example.demo.model.Status;
import com.example.demo.repository.HeroRepository;
import com.example.demo.service.HeroService;


@CrossOrigin("*")
@RequestMapping(value="home")
@RestController
public class HeroController {

	@Autowired
	private HeroService heroService;
	@Autowired
	private HeroRepository heroRepository;
	@Autowired
	private HeroAssembler assembler;
	
	
	@GetMapping("/heroes")
	public CollectionModel<?> getAllHeroes(){
		List<EntityModel<Hero>> heroes=heroRepository.findAll()
				.stream().map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(heroes);
	}

	@GetMapping("/heroes/{heroId}")
	 public ResponseEntity<?> getHero(@PathVariable Integer heroId){
		
		if(heroService.existsHeroById(heroId)) {
			Hero hero=heroRepository.findByHeroId(heroId);
			EntityModel<Hero> newHero=assembler.toModel(hero);
			return ResponseEntity.accepted().body(newHero);
		}
		return ResponseEntity.noContent().build();
	}

	
	
	@PutMapping("/heroes/{heroId}")
	public ResponseEntity<Hero> updateHero(@RequestBody Hero hero,@PathVariable int heroId) {
		 	Hero usedHero =heroRepository.findByHeroId(heroId);
		if(heroService.existsHeroById(heroId)) {
			usedHero.setName(hero.getName());
			Hero updateHero=heroRepository.save(usedHero);
			return ResponseEntity.ok(updateHero);
		}
		
		return ResponseEntity.notFound().build();
	}

	
	
	@PostMapping("/heroes")
	public ResponseEntity<?> addHero(@RequestBody Hero hero){
	    Integer maxHeroId = heroService.getMaxHeroId();
	    System.out.println(maxHeroId);
	  
	    Hero newHero = new Hero();
	    newHero.setName(hero.getName());
	    newHero.setHeroId(maxHeroId);
	    newHero.setStatus(Status.AVAILABLE);
	    heroRepository.save(newHero);
	    
	    EntityModel<Hero> entityHero= assembler.toModel(newHero);
	    
	    return ResponseEntity.created(entityHero.getRequiredLink(IanaLinkRelations.SELF).toUri())
	    		.body(entityHero);
	}
	
	
	
	@DeleteMapping("/heroes/{heroId}")
	public ResponseEntity<?> deleteHero(@PathVariable Integer heroId){
		
		if(heroService.existsHeroById(heroId)) {
			heroService.deleteByHeroId(heroId);
			System.out.println("heroId: "+heroId+" delete success!");
			return ResponseEntity.noContent().build();
		}
		else {
			System.out.println("unexists heroId!");
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@PutMapping("/heroes/cancel/{heroId}")
	public ResponseEntity<?> cancelHero(@PathVariable Integer heroId){
		Hero hero =heroRepository.findByHeroId(heroId);
		hero.setStatus(Status.CANCEL);
		return ResponseEntity.ok(assembler.toModel(heroRepository.save(hero)));
	}
	

	@GetMapping("/heroes/search/{name}")
	public List<Hero> searchHero(@PathVariable String name){
		List<Hero> matchHeroes=heroRepository.findByNameContaining(name);
		return matchHeroes;
	}
}
