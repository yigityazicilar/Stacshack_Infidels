package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uwsoft.editor.renderer.utils.MySkin;

public class MainMenu implements Screen {

    private Game game;
    private Stage stage;
    final int buttonWidth = 200;
    final int buttonHeight = 100;

    public MainMenu(Game aGame){
        game = aGame;
        stage = new Stage(new ScreenViewport());

        ImageButton start = new ImageButton(new ImageButton.ImageButtonStyle());
        start.setSize(buttonWidth, buttonHeight);
        start.getStyle().imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("startbutton.png")));
        start.getStyle().imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("startbutton.png")));
        start.setPosition(960, 500);
        start.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainGame(game));
            }
        });



    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
