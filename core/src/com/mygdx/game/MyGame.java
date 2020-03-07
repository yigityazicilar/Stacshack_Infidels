package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.MainMenu;

public class MyGame extends Game{

    @Override
    public void create() {
        this.setScreen(new MainMenu(this));
    }

    @Override
    public void render(){
        super.render();
    }

    @Override
    public void dispose() {

    }
}
