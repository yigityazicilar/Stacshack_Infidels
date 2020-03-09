package com.mygdx.game;

public enum Role {
    WARRIOR("core/assets/warrior.txt", 50),
    MAGICIAN("core/assets/magician.txt", 40),
    CREATURE("core/assets/creature.txt", 10),
    BOSS("core/assets/boss.txt", 80);

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
