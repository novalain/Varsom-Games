package com.varsom.system.games.car_game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.varsom.system.VarsomSystem;
import com.varsom.system.games.car_game.gameobjects.Car;
import com.varsom.system.games.car_game.tracks.Track;
import com.varsom.system.games.car_game.tracks.Track1;
import com.varsom.system.games.car_game.tracks.Track2;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.varsom.system.screens.VarsomMenu;
import com.varsom.system.network.NetworkListener;


public class GameScreen implements Screen {

    public static final int STEER_NONE = 0;
    public static final int STEER_RIGHT = 1;
    public static final int STEER_LEFT = 2;

    public static final int ACC_NONE = 0;
    public static final int ACC_ACCELERATE = 1;
    public static final int ACC_BRAKE = 2;

    public static int level;

    // For countdown
    private static float countDownTimer = 1.0f;
    private static boolean paused = true;

    // Class variables
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITY_ITERATIONS = 8,
            POSITION_ITERATIONS = 3;

    private SpriteBatch batch;

    private Track track;

    //    private Pixmap pixmap;
    private Car leaderCar;

    private Stage stage = new Stage();
    private Table table = new Table();

    //TODO Load files from AssetLoader
    private Skin skin = new Skin(Gdx.files.internal("system/skins/menuSkin.json"),
            new TextureAtlas(Gdx.files.internal("system/skins/menuSkin.pack")));

    private TextButton buttonLeave = new TextButton("Win screen", skin);
    private Label labelPause;
    private String pauseMessage = "Paused";

    //temp
    Label carsTraveled;

    protected VarsomSystem varsomSystem;
    protected int NUMBER_OF_PLAYERS;

    public GameScreen(int level, final VarsomSystem varsomSystem) {
        this.varsomSystem = varsomSystem;
        varsomSystem.setActiveStage(stage);
        this.level = level;
        world = new World(new Vector2(0f, 0f), true);
        debugRenderer = new Box2DDebugRenderer();

        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();

        NUMBER_OF_PLAYERS = this.varsomSystem.getServer().getConnections().length;

        //TODO THIS IS ONLY TEMPORARY.. DURING DEVELOPMENT
        if (NUMBER_OF_PLAYERS < 2) {
            NUMBER_OF_PLAYERS = 2;
        }

        // Create objects and select level
        switch (level) {
            case 1:
                track = new Track1(world, NUMBER_OF_PLAYERS);
                break;
            case 2:
                track = new Track2(world, NUMBER_OF_PLAYERS);
                break;
            default:
                System.out.println("Mega Error");

        }
        varsomSystem.getActiveGame().setGameScreen(this);
        varsomSystem.getMPServer().gameRunning(true);
        leaderCar = track.getCars()[0];

        // Init camera
        //TODO /100 should probably be changed
        camera = new OrthographicCamera(SCREEN_WIDTH/100,SCREEN_HEIGHT/100);
        //TODO camera.position.set(leaderCar.getPointOnTrack(), 0);
        camera.position.set(leaderCar.getPointOnTrack(), 0);
        camera.rotate((float)Math.toDegrees(leaderCar.getRotationTrack())-180);
        camera.zoom = 1.75f; // can be used to see the entire track
        camera.update();

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        // pause menu button
        table.top().left();
        table.add(buttonLeave).size(400, 75).row();
        // connected devices text thingys....
        carsTraveled = new Label("CarDist:\n", skin);
        carsTraveled.setFontScaleX(0.75f);
        carsTraveled.setFontScaleY(0.75f);
        table.add(carsTraveled).size(400, 200).row();

        table.setFillParent(true);
        stage.addActor(table);

        BitmapFont fontType = new BitmapFont();
        fontType.scale(2.f);
        Label.LabelStyle style = new Label.LabelStyle(fontType, Color.WHITE);

        labelPause = new Label(pauseMessage, style);
        labelPause.setPosition(0, 0);

        labelPause.setPosition(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2);
        stage.addActor(labelPause);

        //TODO Denna behövs för att man ska kunna klicka på knappen  men gör att vi inte längre kan gasa
        Gdx.input.setInputProcessor(stage);

        buttonLeave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the Done button.");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new WinScreen(varsomSystem));
                varsomSystem.getMPServer().gameRunning(false);
            }
        });
        //end pause menu button

    }

    //TODO handles count down timer and pause, should the name change or pause be moved?
    private void handleCountDownTimer() {
        paused = NetworkListener.pause;

        countDownTimer -= Gdx.graphics.getDeltaTime();
        float secondsLeft = (int) countDownTimer % 60;

        // Render some kick-ass countdown label
        if (secondsLeft > 0) {
            paused = true;
            //Gdx.app.log("COUNTDOWN: ", (int)secondsLeft + "");
        }
        /*else if(secondsLeft == 0){
            paused = false;
        }*/

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.7f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // If Exit was pressed on a client
        if (NetworkListener.goBack) {
            Gdx.app.log("in GameScreen", "go back to main menu");
            NetworkListener.goBack = false;

            //new clients can join now when the game is over
            varsomSystem.getMPServer().setJoinable(true);

            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(varsomSystem));

            //dispose(); ??
        }

        handleCountDownTimer();

        batch.setProjectionMatrix(camera.combined);

        track.addToRenderBatch(batch, camera);

        //debugRenderer.render(world, camera.combined);

        world.step(TIMESTEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        //world.clearForces();
        // Set camera position to same as car

        // Get current angle from body
        //float playerAngle = constrainAngle(car.body.getAngle()*MathUtils.radiansToDegrees);

       // Here goes the all the updating / game logic
        if(!paused){
           String temp = "CarDist:\n";
           for(Car car : track.getCars()) {
               if(car.isActive() && !camera.frustum.boundsInFrustum(car.getPosition().x,car.getPosition().y,0,0.5f,1f,0.1f)){
                   carLost(car.getID());
               }
               car.update(Gdx.app.getGraphics().getDeltaTime());
               temp += car.getTraveledDistance() + "\n";
           }


            carsTraveled.setText(temp);

            updateCamera();
        }

        //If paused pause menu is displayed, else it is not
        displayPauseMenu(true);

        stage.act();
        stage.draw();
    }

    private void updateCamera() {
        float smoothCamConst = 0.1f;
        leaderCar = track.getLeaderCar();
        //leaderCar = track.getCars()[0];
        float newCamPosX = (leaderCar.getPointOnTrack().x - camera.position.x);
        float newCamPosY = (leaderCar.getPointOnTrack().y - camera.position.y);
        Vector2 newPos = new Vector2(camera.position.x + newCamPosX * smoothCamConst, camera.position.y + newCamPosY * smoothCamConst);
        //Gdx.app.log("CAMERA","Camera position: " + camera.position);
        if (newPos.x == Float.NaN || newPos.y == Float.NaN) {
            Gdx.app.log("FUUUUDGE", "ERROR");
        }
        camera.position.set(newPos, 0);

        // Convert camera angle from [-180, 180] to [0, 360]
        float camAngle = -getCurrentCameraAngle(camera) + 180;

        float desiredCamRotation = (camAngle - (float) Math.toDegrees(leaderCar.getRotationTrack()) - 90);

        if (desiredCamRotation > 180) {
            desiredCamRotation -= 360;
        } else if (desiredCamRotation < -180) {
            desiredCamRotation += 360;
        }

        camera.rotate(desiredCamRotation * 0.02f);

        camera.update();

    }

    private float getCurrentCameraAngle(OrthographicCamera cam) {
        return (float) Math.atan2(cam.up.x, cam.up.y) * MathUtils.radiansToDegrees;
    }

    @Override
    public void resize(int width, int height) {
        //Gdx.app.log("GameScreen", "resizing in here");
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        //Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        //Gdx.app.log("GameScreen", "hide called");
        dispose();
    }

    @Override
    public void pause() {
        //Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume() {
        //Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose() {
        // Leave blank
    }

    //makes the pause menu visible if pause == true and invisible if not
    public void displayPauseMenu(boolean pause) {
        //TODO display who paused
        labelPause.setVisible(pause);
        buttonLeave.setVisible(pause);

    }

    public Track getTrack() {
        return track;
    }

    private void carLost(int carID){
        System.out.println("Car # " + carID +" left the screen");
        //TODO This doesn't work.. need to properly say to the correct client that it should stop sending packages
        varsomSystem.getMPServer().gameRunning(false,carID+1);
        track.getCars()[carID].setActive(false);
        track.getCars()[carID].handleDataFromClients(false,false,0);
    }
}