package com.mygdx.game;

public abstract class Entity {
    protected String name;
    protected int health;
	protected Role entityRole;

    public Entity(String name, Role role){
        this.name = name;
        this.health = role.getMaxHealth();
		this.entityRole = role;
	}

    public String getName(){
        return this.name;
    }

    public int getHealth(){
        return this.health;
    }

	public int getMaxHealth()
	{
		return entityRole.getMaxHealth();
	}

	public int getHealthFraction()
	{
		return getHealth() / getMaxHealth();
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

    public abstract void importRole(Role role);

}
