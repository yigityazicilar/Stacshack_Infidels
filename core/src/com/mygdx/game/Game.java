package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Game extends ApplicationAdapter {
	SpriteBatch mainGame;
	Texture img;
	float elapsed;
	Animation<TextureRegion> animation;
	TextButton startButton;
	
	@Override
	public void create () {
		mainGame = new SpriteBatch();
		img = new Texture("wizard.gif");
		animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("wizard.gif").read());\
	}

	@Override
	public void render () {
		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0.376f, 0.502f,0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		mainGame.begin();
		mainGame.draw(animation.getKeyFrame(elapsed), Gdx.graphics.getWidth()/2 - img.getWidth()/2, 20.0f);
		mainGame.end();
	}
	
	@Override
	public void dispose () {
		mainGame.dispose();
		img.dispose();
	}
}
