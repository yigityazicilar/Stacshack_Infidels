package com.mygdx.game;

import java.util.HashMap;

public class Enemy extends Entity{

    private int baseHP = 5;

    public Enemy(String name, Role role){
        super(name, role);
    }

    public void setBaseHp(int HP){
        this.baseHP = HP;
    }

    public void importRole(Role role){

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
