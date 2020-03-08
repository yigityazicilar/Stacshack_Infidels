package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class CharacterSelection implements Screen {
    private Stage stage;
    private Game game;

    public CharacterSelection(Game aGame){
        game = aGame;
        stage = new Stage(new ScreenViewport());

        Image background = new Image(new TextureRegionDrawable(new Texture(Gdx.files.internal("background.png"))));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background.setPosition(0, 0);

        ImageButton warrior = new ImageButton(new ImageButtonStyle());
        warrior.setSize(640, 640);
        warrior.getStyle().imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("warrior.png")));
        warrior.getStyle().imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("warrior.png")));
        warrior.setPosition(Gdx.graphics.getWidth() / 4 - warrior.getWidth() / 2, Gdx.graphics.getHeight() / 2 - warrior.getHeight() / 2);
        warrior.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainGame(game, "warrior.gif"));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        ImageButton magician = new ImageButton(new ImageButtonStyle());
        magician.setSize(640, 640);
        magician.getStyle().imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("magician.png")));
        magician.getStyle().imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("magician.png")));
        magician.setPosition(Gdx.graphics.getWidth() / 4 * 3 - magician.getWidth() / 2, Gdx.graphics.getHeight() / 2 - magician.getHeight() / 2);
        magician.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainGame(game, "magician.gif"));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(background);
        stage.addActor(warrior);
        stage.addActor(magician);
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
