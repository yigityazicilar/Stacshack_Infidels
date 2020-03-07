package com.mygdx.game;

import java.util.HashMap;

public class Enemy extends Entity{

    private static int baseHP = 20;

    public Enemy(String name, Role role){
        super(name, role);
    }

    public void setBaseHp(int hp){
        baseHP = hp;
    }

    @Override
    public void importRole(Role role){
        System.out.println("runs");
        switch(role){
            case CREATURE :
                this.health = baseHP * 5;
                break;
            case BOSS :
                this.health = baseHP * 10;
                break;
            default :
                System.out.println("Could not import enemy role");
        }
    }



}
