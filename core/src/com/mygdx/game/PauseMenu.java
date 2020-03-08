package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class PauseMenu implements Screen {
    private final int buttonWidth = 300;
    private final int buttonHeight = 200;
    Stage stage;
    Game game;

    public PauseMenu(Game aGame) {
        game = aGame;
        stage = new Stage(new ScreenViewport());

        ImageButton exit = new ImageButton(new ImageButton.ImageButtonStyle());
        exit.setSize(buttonWidth, buttonHeight);
        exit.getStyle().imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("exit_button.png")));
        exit.getStyle().imageDown = new TextureRegionDrawable(new Texture(Gdx.files.internal("exit_button.png")));
        exit.setPosition(Gdx.graphics.getWidth() / 2 - exit.getWidth() / 2 , Gdx.graphics.getHeight() / 2  - exit.getHeight() / 2 - 350);
        exit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen( new MainMenu(game) );
            }
        });

        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fillRectangle(0, 0, 1, 1);
        Texture transparency = new Texture(pixmap);
        pixmap.dispose();

        Image semiTL = new Image(transparency);
        semiTL.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        semiTL.getColor().a = 0.1f;

        stage.addActor(semiTL);
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
