package com.mygdx.game;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Entity {
    protected String name;
    protected int health = 100;
    protected HashMap<String, Attack> attacks;
    protected double multiplier;

    public Entity(String name, Role role){
        this.name = name;
        this.attacks = new HashMap<>();
        importRole();
        importAttacks(role.getFilePath());

    }

    public String getName(){
        return this.name;
    }

    public int getHealth(){
        return this.health;
    }


    public int attack(String word){
        if(this.attacks.containsKey(word)){
            System.out.println("word is contained");
            return (int)Math.floor(word.length() * multiplier);
        }else{
            return 0;
        }

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

    public void importRole(){
    }

    public void importAttacks(String filepath){
        try{
            List<String> moves = Files.readAllLines(Paths.get(filepath));

            String[] components;

            for(int i = 0; i < moves.size(); i++){
                components = moves.get(i).split(" ");
                this.attacks.put(components[0], new Attack(components[0].length(),
                                                Integer.parseInt(components[1]),
                                                Integer.parseInt(components[2])));
            }

        }catch(IOException e){
            System.out.println("Cannot get " + e.getMessage());
        }


    }
}
