package screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.varsomgames.cargame.CarGame;

import java.util.ArrayList;

import gameobjects.BackgroundObject;

public class MainMenu implements Screen {

    private Stage stage = new Stage();
    private Table table = new Table();

    private final int WIDTH = Gdx.graphics.getWidth();
    private final int HEIGHT = Gdx.graphics.getHeight();

    private SpriteBatch spriteBatch = new SpriteBatch();
    private ArrayList<BackgroundObject> objectList;

    private Skin skin = new Skin(Gdx.files.internal("skins/menuSkin.json"),
            new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack")));

    private TextButton buttonPlay = new TextButton("Play level 1", skin),
            buttonPlay2 = new TextButton("Play level 2", skin),
            buttonSettings = new TextButton("Settings", skin),
            buttonAbout = new TextButton("About", skin),
            buttonExit = new TextButton("Exit", skin);

    private Label title = new Label(CarGame.TITLE, skin);

    public MainMenu() {
        objectList = new ArrayList<BackgroundObject>();

        for (int i = 0; i < 10; i++) {
            Vector2 temp = new Vector2( (float) ( Math.random()*WIDTH), (float) ( Math.random()*HEIGHT) );
            objectList.add(new BackgroundObject(temp, "img/cloud.png"));
        }
    }

    @Override
    public void show() {
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the PLAY button.");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(1));
            }
        });

        buttonPlay2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the PLAY button.");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2));
            }
        });

        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the SETTINGS button.");
            }
        });

        buttonAbout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the ABOUT button.");
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the EXIT button.");
                Gdx.app.exit();
                // or System.exit(0);
            }
        });

        // Elements are displayed in the order they're added, top to bottom
        table.add(title).padBottom(40).row();
        table.add(buttonPlay).size(400, 50).padBottom(20).row();
        table.add(buttonPlay2).size(400, 50).padBottom(20).row();
        table.add(buttonSettings).size(400, 50).padBottom(20).row();
        table.add(buttonAbout).size(400, 50).padBottom(20).row();
        table.add(buttonExit).size(400, 50).padBottom(20).row();

        table.setFillParent(true);
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        updateBackground();

        Gdx.gl.glClearColor(122 / 255.0f, 209 / 255.0f, 255 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawBackground();
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
        skin.dispose();
    }

    public void updateBackground() {
        
        for (int i = 0; i < objectList.size(); i++) {
            BackgroundObject temp = objectList.get(i);
            temp.update();

            if(temp.getPos().x > WIDTH + temp.getWidth() + 10){
                temp.setX(-10 - temp.getWidth());
                temp.setY( (float) ( Math.random()*HEIGHT) );
            }
        }
    }

    public void drawBackground() {
        spriteBatch.begin();

        for (int i = 0; i < objectList.size(); i++) {
            objectList.get(i).draw(spriteBatch);
        }

        spriteBatch.end();
    }
}
