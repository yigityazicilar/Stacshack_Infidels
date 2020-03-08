package com.mygdx.game;

public abstract class Entity {
    protected String name;
    protected int health = 100;

    public Entity(String name, Role role){
        this.name = name;
	}

    public String getName(){
        return this.name;
    }

    public int getHealth(){
        return this.health;
    }

    public void hurt(int damage){
        this.health -= damage;
    }

    public void heal(int hp){
        this.health += hp;
    }

    public void setHP(int health){
        this.health = health;
    }

    public abstract void importRole(Role role);

}
