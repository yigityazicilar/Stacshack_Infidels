package com.mygdx.game;

import java.util.ArrayList;

public class Game {


    public static void startExample(){
        Team team = new Team();

        Player player = new Player("Player", Role.FIGHTER);

        Enemy creature = new Enemy("Creature", Role.CREATURE);

        Enemy boss = new Enemy("Boss", Role.BOSS);

        team.add(player);


        System.out.println(player.getHealth());

        player.hurt(creature.sendAttack("grr"));
        System.out.println(creature.sendAttack("grr"));

        System.out.println(player.getHealth());
    }

}
