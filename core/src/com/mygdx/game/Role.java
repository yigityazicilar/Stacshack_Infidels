package com.mygdx.game;

public enum Role {
    WARRIOR("warrior.txt", 50),
    MAGICIAN("magician.txt", 40),
    CREATURE("creature.txt", 10),
    BOSS("boss.txt", 80);

    private String filepath;
	private int maxHealth;

    Role(String filepath, int maxHealth) {
        this.filepath = filepath;
		this.maxHealth = maxHealth;
    }

    public String getFilePath(){
        return this.filepath;
    }

	public int getMaxHealth()
	{
		return maxHealth;
	}
}
