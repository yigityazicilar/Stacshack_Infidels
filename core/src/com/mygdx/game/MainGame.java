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
import java.util.concurrent.TimeUnit;

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
	final String red = "[RED]";
	final String green = "[GREEN]";
	final String white = "[WHITE]";
	static Boolean playerTurn = false;
	private float elapsed;
	private float screenWidth;
	private float screenHeight;
	private int wordCounter = 0;
	private String currentWord;
	private Boolean wordTyped = false;
	static Boolean generateWords = false;
	static Boolean sceneChanged = false;
	private String typedWord = "";
	private int numberOfEnemies = 4;
	private String[] enemyWords;
	private String[] playerWords;
	private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("NotoSans-Regular.ttf"));
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	private int completedWordCounter = 0;
	public Player player;
	public GameDirector director;
	String highlighting = "";

	public MainGame(com.badlogic.gdx.Game aGame, String picture){
		game = aGame;
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		if(picture.equals("warrior.gif")) {
			player = new Player("player", Role.WARRIOR);
			director = new GameDirector(Role.WARRIOR);
		}else {
			player = new Player("player", Role.MAGICIAN);
			director = new GameDirector(Role.MAGICIAN);
		}
		enemyWords = director.requestEnemyWord(5, numberOfEnemies, 10, player);
		playerWords = new String[3];
		currentWord = enemyWords[completedWordCounter];
		mainGame = new SpriteBatch();
		characterTexture = new Texture(picture);
		enemyTexture = new Texture("goblin.gif");
		character = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(picture).read());
		enemy = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("goblin.gif").read());
		backgroundTexture = new Texture("background.png");
		backgroundSprite = new Sprite(backgroundTexture);
		parameter.size = 84;
		word = generator.generateFont(parameter);
		word.getData().markupEnabled = true;
		word.setColor(Color.WHITE);
		word.getData().setScale(1);
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean keyTyped(char character) {
				if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
					game.setScreen(new PauseMenu(game));
				} else if(Gdx.input.isKeyPressed(Input.Keys.ENTER)){
					if(completedWordCounter == numberOfEnemies - 1 && playerTurn == false){
						playerWords = new String[numberOfEnemies];
						completedWordCounter = 0;
						GameDirector.future.cancel(true);
						generateWords = true;
						playerTurn = true;
					}else if (playerTurn){
						playerTurn = false;
						generateWords = true;
						typedWord = "";
					}else {
						enemyWords[completedWordCounter] = typedWord;
						completedWordCounter++;
					}
					wordTyped = true;
					typedWord = "";
				} else if(Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)){
					if (typedWord.length() >= 1) {
						typedWord = typedWord.substring(0, typedWord.length() - 1);
					}
				} else {
					if(typedWord.length() <= currentWord.length() || playerTurn) {
						typedWord = typedWord + character;
					}
					if(typedWord.equals(currentWord)){
						if(completedWordCounter == numberOfEnemies - 1 && playerTurn == false){
							enemyWords = new String[numberOfEnemies];
							completedWordCounter = 0;
							GameDirector.future.cancel(true);
							generateWords = true;
							playerTurn = true;
						}else if ( playerTurn == false ){
							enemyWords[completedWordCounter] = typedWord;
							completedWordCounter++;
						}
						wordTyped = true;
						typedWord = "";
					}
					if(playerTurn){
						for (String word: playerWords) {
							if(word.equals(typedWord)){
								typedWord = "";
								generateWords = true;
								playerTurn = false;
							}
						}
					}
				}
				return super.keyTyped(character);
			}
		});
	}


	@Override
	public void render(float delta) {
		mainGame.begin();
		if(!playerTurn) {
			highlighting = "";
			mainGame.draw(backgroundSprite, 0, 0, screenWidth, screenHeight);

			if (generateWords) {
				enemyWords = director.requestEnemyWord(5, numberOfEnemies, 6, player);
				generateWords = false;
			}

			if (wordTyped) {
				currentWord = enemyWords[completedWordCounter];
				wordTyped = false;
			}

			for (int i = 0; i < Math.min(typedWord.length(), currentWord.length()); i++) {
				if (typedWord.charAt(i) == currentWord.charAt(i)) {
					highlighting = highlighting + green;
				} else {
					highlighting = highlighting + red;
				}
				highlighting = highlighting + currentWord.charAt(i);
			}
			highlighting = highlighting + white + currentWord.substring(Math.min(typedWord.length(), currentWord.length()));
			if (typedWord.length() > currentWord.length()) {
				highlighting = red + currentWord;
			}

			displayText(highlighting, word, screenWidth / 2, screenHeight - enemyTexture.getHeight() - 200);

			mainGame.draw(character.getKeyFrame(elapsed), screenWidth / 2 - characterTexture.getWidth() / 2, 20.0f);
			elapsed += Gdx.graphics.getDeltaTime();
			for (int i = 0; i < numberOfEnemies; i++) {
				mainGame.draw(enemy.getKeyFrame(elapsed), (screenWidth - numberOfEnemies * enemyTexture.getWidth()) / 2 + i * enemyTexture.getWidth(), screenHeight - enemyTexture.getHeight() - 100);
			}
		}else {
			mainGame.flush();

			mainGame.draw(backgroundSprite, 0, 0, screenWidth, screenHeight);

			if (generateWords) {
				playerWords = director.requestPlayerWordBatch();
				generateWords = false;
			}

			word.setColor(Color.RED);

			for (int i = 0; i < 3; i++) {
				CharSequence charSequence = playerWords[i];
				GlyphLayout layout = new GlyphLayout();
				layout.setText(word, charSequence);
				word.draw(mainGame, playerWords[i], screenWidth / 2  - layout.width / 2, screenHeight / 2 + 100 - i * 80 );
			}

			mainGame.draw(character.getKeyFrame(elapsed), screenWidth / 2 - characterTexture.getWidth() / 2, 20.0f);
			elapsed += Gdx.graphics.getDeltaTime();
			for (int i = 0; i < numberOfEnemies; i++) {
				mainGame.draw(enemy.getKeyFrame(elapsed), (screenWidth - numberOfEnemies * enemyTexture.getWidth()) / 2 + i * enemyTexture.getWidth(), screenHeight - enemyTexture.getHeight() - 100);
			}
		}
		mainGame.end();
	}

	private void displayText(String text, BitmapFont label, float x, float y) {
		CharSequence charSequence = text;
		GlyphLayout layout =new GlyphLayout();
		layout.setText(label, charSequence);
		label.draw(mainGame, text, x - layout.width / 2, y);
	}

	@Override
	public void dispose () {
		mainGame.dispose();
		characterTexture.dispose();
		enemyTexture.dispose();
		backgroundTexture.dispose();
		word.dispose();
		// TODO dispose of everything
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
