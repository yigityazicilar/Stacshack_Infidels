package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.*;

public class MainGame implements Screen {
//	private Stage stage;
	private Game game;

    // Scenes
	private SpriteBatch mainGame;
	// Textures
	private Texture characterTexture;
	private Texture enemyTexture;
	private Texture backgroundTexture;
	// Animations
	private Animation<TextureRegion> character;
	private Animation<TextureRegion> enemy;
	// Bitmaps
	private BitmapFont word;
	// Sprites
	private static Sprite backgroundSprite;
	//Layouts
	GlyphLayout layout;
	// Variables
	private float elapsed;
	private float screenWidth;
	private float screenHeight;
	final int gap = 50;
	final int buttonWidth = 200;
	final int buttonHeight = 100;

	public MainGame(com.badlogic.gdx.Game game){
		this.game = game;
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		mainGame = new SpriteBatch();
		characterTexture = new Texture("wizard.gif");
		enemyTexture = new Texture("sans.gif");
		character = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("wizard.gif").read());
		enemy = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("sans.gif").read());
		backgroundTexture = new Texture("background2.png");
		backgroundSprite = new Sprite(backgroundTexture);
		word = new BitmapFont();
		word.setColor(Color.WHITE);
		word.getData().setScale(5);
		Gdx.gl.glClearColor(0.376f, 0.502f,0.22f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}


	@Override
	public void render(float delta) {
		CharSequence charSequence = "Words pop up here";
	    GlyphLayout layout = new GlyphLayout();
	    layout.setText(word, charSequence);
		mainGame.begin();
		mainGame.draw(backgroundSprite, 0, 0, screenWidth, screenHeight);
		word.draw(mainGame, "Words pop up here", screenWidth/2 - layout.width/2, screenHeight/2);
		mainGame.draw(character.getKeyFrame(elapsed), screenWidth/2 - characterTexture.getWidth()/2, 20.0f);
		elapsed += Gdx.graphics.getDeltaTime();
		for (int i = 0; i < 5; i++) {
			mainGame.draw(enemy.getKeyFrame(elapsed), (screenWidth - (5)*enemyTexture.getWidth())/2 + i*enemyTexture.getWidth(), screenHeight - enemyTexture.getHeight() - 100);
		}
		mainGame.end();
	}

	@Override
	public void dispose () {
		mainGame.dispose();
		characterTexture.dispose();
		enemyTexture.dispose();
		backgroundTexture.dispose();
		word.dispose();
	}

	@Override
	public void show() {
	}
	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
	@Override
	public void hide() {
	}
}
