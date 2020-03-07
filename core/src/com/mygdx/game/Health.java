package com.mygdx.game;

public class Health {
    private int hp;

    public Health(int hp){
        this.hp = hp;
    }

    public void reduce(int damage){
        this.hp -= damage;
    }

    public void heal(int hp){
        this.hp += hp;
    }
}
