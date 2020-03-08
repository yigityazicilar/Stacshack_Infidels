package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uwsoft.editor.renderer.utils.MySkin;

public class MainMenu extends ApplicationAdapter implements Screen {

    private Game game;
    private Stage stage;
    final int buttonWidth = 300;
    final int buttonHeight = 200;
    private Music music;

    public MainMenu(Game aGame){
        game = aGame;
        stage = new Stage(new ScreenViewport());

        music =  Gdx.audio.newMusic(Gdx.files.internal("MAINmenu.mp3"));
        music.setVolume(0.8f);
        music.setLooping(true);
        music.play();

        Image background = new Image(new TextureRegionDrawable(new Texture(Gdx.files.internal("background.png"))));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setPosition(0, 0);

        Image logo = new Image(new TextureRegionDrawable(new Texture(Gdx.files.internal("logoTypeWarriors.png"))));
        logo.setSize(300, 300);
        logo.setPosition(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 2, (Gdx.graphics.getHeight() / 5) * 4 - logo.getHeight() / 2);

        Image logoText = new Image(new TextureRegionDrawable(new Texture(Gdx.files.internal("KeyboardWarriors.png"))));
        logoText.setSize(702, 86);
        logoText.setPosition(Gdx.graphics.getWidth() / 2 - logoText.getWidth() / 2,  (Gdx.graphics.getHeight() / 5) * 4 - logoText.getHeight() / 2 - logo.getHeight() / 2 - 50);

        ImageButton start = new ImageButton(new ImageButton.ImageButtonStyle());
        start.setSize(buttonWidth, buttonHeight);
        start.getStyle().imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("start_button.png")));
        start.getStyle().imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("start_button.png")));
        start.setPosition(Gdx.graphics.getWidth() / 2 - start.getWidth() / 2 , Gdx.graphics.getHeight() / 2 - start.getHeight() / 2 - 100 );
        start.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new CharacterSelection(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        ImageButton exit = new ImageButton(new ImageButton.ImageButtonStyle());
        exit.setSize(buttonWidth, buttonHeight);
        exit.getStyle().imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("exit_button.png")));
        exit.getStyle().imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("exit_button.png")));
        exit.setPosition(Gdx.graphics.getWidth() / 2 - start.getWidth() / 2 , Gdx.graphics.getHeight() / 2 - start.getHeight() / 2 - 350);
        exit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(0);
            }
        });

        stage.addActor(background);
        stage.addActor(logo);
        stage.addActor(logoText);
        stage.addActor(start);
        stage.addActor(exit);
    }


    @Override
    public void show() {
       Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
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

    @Override
    public void dispose() {
        stage.dispose();
    }
}
