package com.mygdx.game;

public enum Role {
    WARRIOR("core/assets/warrior.txt"),
    MAGICIAN("core/assets/magician.txt"),
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
