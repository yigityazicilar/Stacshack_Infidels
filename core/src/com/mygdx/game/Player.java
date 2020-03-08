package com.mygdx.game;

public class Player extends Entity{

    public Player(String name, Role role){
        super(name, role);
    }

    public void importRole(Role role){

        switch(role){
            case WARRIOR:
                this.health = 50;
                break;
            case MAGICIAN:
                this.health = 25;
                break;
            default :
                System.out.println("Could not import role");
        }
    }



}
