package com.mygdx.game;

public enum Role {
    FIGHTER("fighter.txt"),
    ENEMY("enemy.txt");

    private String filepath;

    Role(String filepath) {
        this.filepath = filepath;
    }

    public String getFilePath(){
        return this.filepath;
    }
}
