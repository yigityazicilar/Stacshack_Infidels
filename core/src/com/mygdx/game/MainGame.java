package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGame implements Screen {
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
	private int wordCounter = 0;
	private String currentWord;
	private Boolean wordTyped = false;
	private Boolean generateWords = false;
	private String typedWord = "";
	private int numberOfEnemies = 5;
	private String[] enemyWords;
	private String[] playerWords;
	private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("NotoSans-Regular.ttf"));
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	private int completedWordCounter = 0;
	public Player player;
	public GameDirector director = new GameDirector();

	public MainGame(com.badlogic.gdx.Game aGame, String picture){
		game = aGame;
		player = new Player("wizard", Role.FIGHTER);
		enemyWords = director.requestEnemyWord(5, numberOfEnemies, 6, player);
		playerWords = new String[numberOfEnemies];
		currentWord = enemyWords[completedWordCounter];
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		mainGame = new SpriteBatch();
		characterTexture = new Texture(picture);
		enemyTexture = new Texture("sans.gif");
		character = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(picture).read());
		enemy = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("sans.gif").read());
		backgroundTexture = new Texture("background.png");
		backgroundSprite = new Sprite(backgroundTexture);
		parameter.size = 84;
		word = generator.generateFont(parameter);
		word.setColor(Color.WHITE);
		word.getData().setScale(1);
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean keyTyped(char character) {
				if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
					game.setScreen(new PauseMenu(game));
				}else if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
					if(completedWordCounter == numberOfEnemies - 1){
						playerWords = new String[numberOfEnemies];
						completedWordCounter = 0;
						generateWords = true;
					}else{
						playerWords[completedWordCounter] = typedWord;
						completedWordCounter++;
					}
					wordTyped = true;
					typedWord = "";
					System.out.println(typedWord);
				}else if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){
					if (typedWord.length() >= 1) {
						typedWord = typedWord.substring(0, typedWord.length() - 1);
						System.out.println(typedWord);
					}
				} else {
					typedWord = typedWord + character;
					System.out.println(typedWord);
				}
				return super.keyTyped(character);
			}
		});
	}


	@Override
	public void render(float delta) {
		mainGame.begin();
		mainGame.draw(backgroundSprite, 0, 0, screenWidth, screenHeight);

		if(generateWords) {
			enemyWords = director.requestEnemyWord(5, numberOfEnemies, 6, player);
			generateWords = false;
		}

		if (wordTyped) {
			currentWord = enemyWords[completedWordCounter];
			wordTyped = false;
		}

		displayText(currentWord, screenWidth/2, screenHeight - enemyTexture.getHeight() - 200);

		mainGame.draw(character.getKeyFrame(elapsed), screenWidth/2 - characterTexture.getWidth()/2, 20.0f);
		elapsed += Gdx.graphics.getDeltaTime();
		//Fix it so it gets centered with lower amount of enemies.
		for (int i = 0; i < numberOfEnemies; i++) {
			mainGame.draw(enemy.getKeyFrame(elapsed), (screenWidth - (5)*enemyTexture.getWidth())/2 + i*enemyTexture.getWidth(), screenHeight - enemyTexture.getHeight() - 100);
		}
		mainGame.end();
	}

	private void displayText(String text, float x, float y) {
		CharSequence charSequence = text;
		GlyphLayout layout =new GlyphLayout();
		layout.setText(word, charSequence);
		word.draw(mainGame, text, x - layout.width/2,y);
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
