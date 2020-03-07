package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Game extends ApplicationAdapter {
	final int gap = 50;
	final int buttonWidth = 200;
	final int buttonHeight = 100;
	SpriteBatch mainGame;
	SpriteBatch mainMenu;
	Texture img;
	Texture wizardTexture;
	Texture enemyTexture;
	float elapsed;
	Animation<TextureRegion> animation;
	ImageButton start;
	Animation<TextureRegion> character;
	Animation<TextureRegion> enemy;
	public static Texture backgroundTexture;
	public static Sprite backgroundSprite;

	@Override
	public void create () {
		mainGame = new SpriteBatch();
		img = new Texture("wizard.gif");
		animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("wizard.gif").read());
		start = new ImageButton(new ImageButtonStyle());
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
		mainMenu.begin();
		start.setSize(buttonWidth, buttonHeight);
		start.getStyle().imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("startbutton.png")));
		start.getStyle().imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("startbutton.png")));
		start.setPosition(960, 500);
		start.addListener(new InputListener(){
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Game.setScreen()
			}
		});
		mainMenu.end();
		mainMenu.begin();
		mainGame.draw(animation.getKeyFrame(elapsed), Gdx.graphics.getWidth()/2 - img.getWidth()/2, 20.0f);
		mainGame.draw(backgroundSprite, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		mainGame.draw(character.getKeyFrame(elapsed), Gdx.graphics.getWidth()/2 - wizardTexture.getWidth()/2, 20.0f);
		for (int i = 0; i < 5; i++) {
			mainGame.draw(enemy.getKeyFrame(elapsed), (Gdx.graphics.getWidth() - (5)*enemyTexture.getWidth())/2 + i*enemyTexture.getWidth(), Gdx.graphics.getHeight() - enemyTexture.getHeight() - 100);
		}
		mainGame.end();
	}
	
	@Override
	public void dispose () {
		mainGame.dispose();
		img.dispose();
		wizardTexture.dispose();
		enemyTexture.dispose();
		backgroundTexture.dispose();
	}
}
