package com.example.demo.model;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.example.demo.controller.HeroController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class HeroAssembler implements RepresentationModelAssembler<Hero, EntityModel<Hero>>{
	@Override
	public EntityModel<Hero>toModel(Hero hero){
		return EntityModel.of(hero,
				linkTo(methodOn(HeroController.class).getHero(hero.getHeroId())).withSelfRel(),
				linkTo(methodOn(HeroController.class).getAllHeroes()).withRel("heroes"));
		
	}

}
