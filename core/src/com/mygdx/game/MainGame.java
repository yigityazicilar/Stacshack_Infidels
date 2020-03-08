package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.Arrays;

public class MainGame implements Screen {
	private Game game;

    // Scenes
	private SpriteBatch mainGame;
	// Textures
	private Texture characterTexture;
	private Texture enemyTexture;
	private Texture backgroundTexture;
	private Texture healthBar;
	private Texture healthUnit;
	// Animations
	private Animation<TextureRegion> characterAnimation;
	private Animation<TextureRegion> enemyAnimation;
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
	public static String typedWord = "";
	private int numberOfEnemies = 4;
	public static String[] playerWrote;
	public static String[] enemyWords;
	private String[] playerWords;
	private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("NotoSans-Regular.ttf"));
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
	public static int completedWordCounter = 0;
	private Player player;
	private Enemy enemy;
	private GameDirector director;

	public static int characterHealth = 100;
	private int characterMaxHeath = 100;
	private int enemyHealth;
	private int enemyMaxHealth;

	String highlighting = "";

	public MainGame(com.badlogic.gdx.Game aGame, String picture){
		game = aGame;
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();
		if(picture.equals("warrior.gif")) {
			player = new Player("player", Role.WARRIOR);
			director = new GameDirector(Role.WARRIOR, this.playerWrote, this.enemyWords);
		}else {
			player = new Player("player", Role.MAGICIAN);
			director = new GameDirector(Role.MAGICIAN, this.playerWrote, this.enemyWords);
		}

		enemy = new Enemy("goblin",Role.CREATURE);


		enemyHealth = enemy.getHealth();
		enemyMaxHealth = enemy.getMaxHealth();

		enemyWords = director.requestEnemyWord(5, numberOfEnemies, 10, player);
		playerWrote = new String[numberOfEnemies];
		playerWords = new String[3];
		currentWord = enemyWords[completedWordCounter];
		mainGame = new SpriteBatch();
		characterTexture = new Texture(picture);
		enemyTexture = new Texture("goblin.gif");
		characterAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(picture).read());
		enemyAnimation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("goblin.gif").read());
		backgroundTexture = new Texture("background.png");
		backgroundSprite = new Sprite(backgroundTexture);
		healthBar = new Texture("healthBar.png");
		healthUnit = new Texture("healthUnit.png");
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
						playerWrote[completedWordCounter] = typedWord;
						System.out.println(Arrays.deepToString(playerWrote));
						System.out.println(Arrays.deepToString(enemyWords));
						System.out.println(director.updateDamage(playerWrote, enemyWords));
						characterHealth -= director.updateDamage(playerWrote, enemyWords);
						playerWrote = new String[numberOfEnemies];
						completedWordCounter = 0;
						GameDirector.future.cancel(true);
						generateWords = true;
						playerTurn = true;
					}else if (playerTurn){
						playerTurn = false;
						generateWords = true;
						typedWord = "";
					}else {
						playerWrote[completedWordCounter] = typedWord;
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
					if(typedWord.equals(currentWord)) {
						if (completedWordCounter == numberOfEnemies - 1 && playerTurn == false) {
							playerWrote[completedWordCounter] = typedWord;
							System.out.println(Arrays.deepToString(playerWrote));
							System.out.println(Arrays.deepToString(enemyWords));
							System.out.println(director.updateDamage(playerWrote, enemyWords));
							characterHealth -= director.updateDamage(playerWrote, enemyWords);
							playerWrote = new String[numberOfEnemies];
							completedWordCounter = 0;
							GameDirector.future.cancel(true);
							generateWords = true;
							playerTurn = true;
						} else if (playerTurn == false) {
							playerWrote[completedWordCounter] = typedWord;
							completedWordCounter++;
						}
						wordTyped = true;
						typedWord = "";
					}else if(playerTurn){
						for (String word: playerWords) {
							if(word.equals(typedWord)){
								System.out.println(enemyHealth);
								enemyHealth -= typedWord.length();
								if (enemyHealth <= 0) {
									enemyHealth = 0;
								}
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
		if(characterHealth <= 0) characterHealth = 0;
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

			mainGame.draw(characterAnimation.getKeyFrame(elapsed), screenWidth/2 - characterTexture.getWidth()/2, 20.0f);
			mainGame.draw(healthBar, screenWidth/2 - characterTexture.getWidth()/2 + 115, characterTexture.getHeight(),200,40);
			mainGame.draw(healthUnit, screenWidth/2 - characterTexture.getWidth()/2 + 115, characterTexture.getHeight(),(int) Math.floor((double) characterHealth/characterMaxHeath*200),40);
			elapsed += Gdx.graphics.getDeltaTime();
			for (int i = 0; i < numberOfEnemies; i++) {
				mainGame.draw(enemyAnimation.getKeyFrame(elapsed), (screenWidth - numberOfEnemies*enemyTexture.getWidth())/2 + i*enemyTexture.getWidth(), screenHeight - enemyTexture.getHeight());
				mainGame.draw(healthBar, (screenWidth - numberOfEnemies*enemyTexture.getWidth())/2 + i*enemyTexture.getWidth() + 82, screenHeight - enemyTexture.getHeight() - 50, 100,20);
				mainGame.draw(healthUnit, (screenWidth - numberOfEnemies*enemyTexture.getWidth())/2 + i*enemyTexture.getWidth()+ 82, screenHeight - enemyTexture.getHeight() - 50, ((int) Math.floor(((double) enemyHealth/enemyMaxHealth)*100)),20);
				mainGame.draw(healthUnit, (screenWidth - numberOfEnemies*enemyTexture.getWidth())/2 + i*enemyTexture.getWidth()+ 82, screenHeight - enemyTexture.getHeight() - 50, ((int) Math.floor(((double) enemyHealth/enemyMaxHealth)*100)),20);
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
				word.draw(mainGame, playerWords[i], screenWidth / 2  - layout.width / 2, screenHeight / 2 + 200 - i * 80 );
			}

			mainGame.draw(characterAnimation.getKeyFrame(elapsed), screenWidth/2 - characterTexture.getWidth()/2, 20.0f);
			mainGame.draw(healthBar, screenWidth/2 - characterTexture.getWidth()/2 + 115, characterTexture.getHeight(),200,40);
			mainGame.draw(healthUnit, screenWidth/2 - characterTexture.getWidth()/2 + 115, characterTexture.getHeight(),(int) Math.floor((double) characterHealth/characterMaxHeath*200),40);
			elapsed += Gdx.graphics.getDeltaTime();
			for (int i = 0; i < numberOfEnemies; i++) {
				mainGame.draw(enemyAnimation.getKeyFrame(elapsed), (screenWidth - numberOfEnemies*enemyTexture.getWidth())/2 + i*enemyTexture.getWidth(), screenHeight - enemyTexture.getHeight());
				mainGame.draw(healthBar, (screenWidth - numberOfEnemies*enemyTexture.getWidth())/2 + i*enemyTexture.getWidth() + 82, screenHeight - enemyTexture.getHeight() - 50, 100,20);
				mainGame.draw(healthUnit, (screenWidth - numberOfEnemies*enemyTexture.getWidth())/2 + i*enemyTexture.getWidth()+ 82, screenHeight - enemyTexture.getHeight() - 50, (int) Math.floor(((double) enemyHealth/enemyMaxHealth)*100),20);
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
