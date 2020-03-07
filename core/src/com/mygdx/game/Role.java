package com.mygdx.game;

public enum Role {
    FIGHTER("core/assets/fighter.txt"),
    CREATURE("core/assets/creature.txt"),
    BOSS("core/assets/boss.txt");

    private String filepath;

    Role(String filepath) {
        this.filepath = filepath;
    }

    public String getFilePath(){
        return this.filepath;
    }
}
