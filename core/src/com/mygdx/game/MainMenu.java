package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uwsoft.editor.renderer.utils.MySkin;

public class MainMenu extends ApplicationAdapter implements Screen {

    private Game game;
    private Stage stage;
    final int buttonWidth = 200;
    final int buttonHeight = 100;

    public MainMenu(Game aGame){
        game = aGame;
        stage = new Stage(new ScreenViewport());

        ImageButton start = new ImageButton(new ImageButton.ImageButtonStyle());
        start.setSize(buttonWidth, buttonHeight);
        start.getStyle().imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("start_button.png")));
        start.getStyle().imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("start_button.png")));
        start.setPosition(Gdx.graphics.getWidth() / 2 - start.getWidth() / 2, Gdx.graphics.getHeight() / 2 - start.getHeight() / 2);
        start.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainGame(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(start);
    }


    @Override
    public void show() {
       Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
