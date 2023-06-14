package com.example.demo.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class Hero {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int heroId;
	private String name;
	private Status status;
	public Hero(){}
	public Hero(int heroId,String name,Status status) {
		super();
		this.heroId = heroId;
		this.name = name;
		this.status=status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getHeroId() {
		return heroId;
	}
	public void setHeroId(int heroId) {
		this.heroId = heroId;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Hero [id=" + id + ", heroId=" + heroId + ", name=" + name + ", status=" + status + "]";
	}

	
}
