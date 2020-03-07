package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Texture wizardTexture;
	Texture enemyTexture;
	float elapsed;
	Animation<TextureRegion> character;
	Animation<TextureRegion> enemy;
	public static Texture backgroundTexture;
	public static Sprite backgroundSprite;

	@Override
	public void create () {
		batch = new SpriteBatch();
		wizardTexture = new Texture("wizard.gif");
		enemyTexture = new Texture("sans.gif");
		character = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("wizard.gif").read());
		enemy = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("sans.gif").read());
		backgroundTexture = new Texture("background2.png");
		backgroundSprite = new Sprite(backgroundTexture);
	}

	@Override
	public void render () {
		elapsed += Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0.376f, 0.502f,0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(backgroundSprite, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(character.getKeyFrame(elapsed), Gdx.graphics.getWidth()/2 - wizardTexture.getWidth()/2, 20.0f);
		for (int i = 0; i < 5; i++) {
			batch.draw(enemy.getKeyFrame(elapsed), (Gdx.graphics.getWidth() - (5)*enemyTexture.getWidth())/2 + i*enemyTexture.getWidth(), Gdx.graphics.getHeight() - enemyTexture.getHeight() - 100);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		wizardTexture.dispose();
		enemyTexture.dispose();
		backgroundTexture.dispose();
	}
}
