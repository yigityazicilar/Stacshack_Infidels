package com.mygdx.game;

import java.util.HashMap;

public abstract class Entity {
    protected String name;
    protected int health;
    protected HashMap<String, Integer> attacks;
    protected double multiplier;

    public Entity(String name, int health, double multiplier){
        this.name = name;
        this.health = health;
        this.multiplier = multiplier;
    }



//    public Entity(String name, int health, HashMap<String, Integer> attacks){
//        this(name, health, attacks, 1.0);
//    }


    public int attack(String attack){
        return (int)Math.floor(attack.length() * multiplier);
    }

    public void reduce(int damage){
        this.health -= damage;
    }

    public void setAttacks(){

    }
}
