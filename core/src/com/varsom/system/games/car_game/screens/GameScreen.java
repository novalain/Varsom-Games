package com.varsom.system.games.car_game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.varsom.system.VarsomSystem;
import com.varsom.system.games.car_game.gameobjects.Car;
import com.varsom.system.games.car_game.helpers.AssetLoader;
import com.varsom.system.games.car_game.tracks.Track;
import com.varsom.system.games.car_game.tracks.Track1;
import com.varsom.system.games.car_game.tracks.Track2;
import com.varsom.system.network.NetworkListener;

import java.util.ArrayList;
import java.util.Collections;

// TODO Should be an abstract class
public class GameScreen implements Screen {

    public static int level;

    // For countdown
    private static float countDownTimer = 7.0f;
    private static boolean paused = true;

    // Class variables
    private World world;
    private Box2DDebugRenderer debugRenderer;

    //CAMERA
    private OrthographicCamera camera;
    final float CAMERA_POS_INTERPOLATION = 0.1f;
    final float CAMERA_ROT_INTERPOLATION = 0.015f;

    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITY_ITERATIONS = 8, POSITION_ITERATIONS = 3;

    private SpriteBatch batch, redlightBatch;

    private Track track;
    private float trackLength;
    private Car leaderCar;
    private ArrayList<Car> activeCars, sortedCars;

    private Stage stage = new Stage();
    private Table table = new Table();

    //TODO Load files from AssetLoader
    private Skin skin = new Skin(Gdx.files.internal("system/skins/menuSkin.json"),
            new TextureAtlas(Gdx.files.internal("system/skins/menuSkin.pack")));

    private TextButton buttonLeave = new TextButton("Win screen", skin);
    private Label labelPause;
    private String pauseMessage = "Paused";

    // Only for development - carlbaum
    private Label carsTraveled;
    private Label lapsLabel;
    private int maxLaps = 1, currentLap = 1;

    protected VarsomSystem varsomSystem;
    protected int NUMBER_OF_PLAYERS;
    private String diePulse;

   // variables for redlights
    private Animation redlightAnimation;
    private Image redLight;
    private float stateTime;

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
                track = new Track1(world, NUMBER_OF_PLAYERS,varsomSystem);
                break;
            case 2:
                track = new Track2(world, NUMBER_OF_PLAYERS,varsomSystem);
                break;
            default:
                System.out.println("Mega Error");

        }
        varsomSystem.getActiveGame().setGameScreen(this);
        varsomSystem.getMPServer().gameRunning(true);

        trackLength = track.getTrackLength();

        leaderCar = track.getCars()[0];
        activeCars = new ArrayList<Car>();
        sortedCars = new ArrayList<Car>();
        addActiveCars();
        createDiePulse();

        // Init camera
        //TODO /100 should probably be changed
        camera = new OrthographicCamera(SCREEN_WIDTH/100,SCREEN_HEIGHT/100);
        //TODO camera.position.set(leaderCar.getPointOnTrack(), 0);
        camera.position.set(leaderCar.getPointOnTrack(), 0);
        camera.rotate((float)Math.toDegrees(leaderCar.getRotationTrack())-180);
        camera.zoom = 2.f; // can be used to see the entire track
        camera.update();

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        // pause menu button
        table.top().left();
        table.add(buttonLeave).size(400, 75).row();
        // connected devices text things....
        lapsLabel = new Label("Lap:\n", skin);
        lapsLabel.setFontScaleX(0.75f);
        lapsLabel.setFontScaleY(0.75f);
        table.add(lapsLabel).size(400, 200).row();

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

        Gdx.input.setInputProcessor(stage);

        buttonLeave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the Done button.");
                weHaveAWinner();
            }
        });

        // handling batch and animation time for redlights
        stateTime = 0f; // setting a timer for accessing the images from assetloader
        redlightAnimation = new Animation(1.f, AssetLoader.redlightsFrames); // importing redlight images to animation
        redLight = new Image(); // this is the actor that will put animations on the stage
    }

    private void handleCountdownAndPause() {
        paused = NetworkListener.pause;
        stateTime += Gdx.graphics.getDeltaTime();

        countDownTimer -= Gdx.graphics.getDeltaTime();
        float secondsLeft = (int) countDownTimer % 60;

        // rendering the trafficlight
        if (secondsLeft > 0 && secondsLeft < 5) {
            paused = true;
          //  Gdx.app.log("Secondsleft: ",""+ secondsLeft);
            redLight.remove(); // remove the previous image if there is any
            redLight = new Image(redlightAnimation.getKeyFrame(stateTime-1, true));
            stage.addActor(redLight);
        }
        else
        {
            redLight.setVisible(false);
            paused = false;
        }

        /*else if(secondsLeft == 0){
        paused = false;
    }*/

    }

    // TODO keep render loop clean...
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.7f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // If Exit was pressed on a client
        if (NetworkListener.goBack) {
         //   Gdx.app.log("in GameScreen", "go back to main menu");
            NetworkListener.goBack = false;

            //new clients can join now when the game is over
            varsomSystem.getMPServer().setJoinable(true);

            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(varsomSystem));

            //dispose(); ??
        }

      //  handleCountdownAndPause();
       // batch.setProjectionMatrix(camera.combined);
        track.addToRenderBatch(batch, camera);
        handleCountdownAndPause();
        batch.setProjectionMatrix(camera.combined);
        //debugRenderer.render(world, camera.combined);

        // Here goes the all the updating / game logic
        if(!paused){
            redLight.setVisible(false);;
            world.step(TIMESTEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

            String temp = "Standings:\n";

            // Check if cars is inside the boundaries of the camera
            for(int i = 0; i < activeCars.size(); i++){

                //TODO Fix hardcoded values of frustum
                if(!camera.frustum.boundsInFrustum(activeCars.get(i).getPosition().x,activeCars.get(i).getPosition().y,0,0.5f,1f,0.1f)){
                    carLost(i);

                    if(i != 0){
                        i--;
                    }
                }
                else {
                    activeCars.get(i).update(Gdx.app.getGraphics().getDeltaTime());
                }
            }
            sortCars();
            temp += sortedCars2String();

            // Next lap incoming
            if(leaderCar.getTraveledDistance() > trackLength * currentLap){
                currentLap++;
                if(currentLap == maxLaps+1){
                    weHaveAWinner();
                }
            }

            String lapText = "Lap " + currentLap + "/" + maxLaps;
            System.out.println();
            lapsLabel.setText(lapText);
            carsTraveled.setText(temp);
            updateCamera();
        }

        //If paused pause menu is displayed, else it is not
        displayPauseMenu(paused);

        stage.act();
        stage.draw();
    }

    private void updateCamera() {
        leaderCar = track.getLeaderCar();
        float newCamPosX = (leaderCar.getPointOnTrack().x - camera.position.x);
        float newCamPosY = (leaderCar.getPointOnTrack().y - camera.position.y);
        Vector2 newPos = new Vector2(camera.position.x + newCamPosX * CAMERA_POS_INTERPOLATION, camera.position.y + newCamPosY * CAMERA_POS_INTERPOLATION);
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

        camera.rotate(desiredCamRotation * CAMERA_ROT_INTERPOLATION);

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
        System.out.println("GameScreen was disposed");
        varsomSystem.getMPServer().gameRunning(false);
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

    private void carLost(int activeCarIndex){
        Car car = activeCars.get(activeCarIndex);
        int conID = car.getConnectionID();
        try {
            System.out.println(varsomSystem.getServer().getConnections()[car.getID()].toString() + " left the screen");
        } catch (Exception e) {
            System.out.println("unconnected car left the screen");
        }
        varsomSystem.getMPServer().PulseVibrateClient(diePulse,-1,conID);
        varsomSystem.getMPServer().gameRunning(false,conID);
        car.setActive(false);

        //System.out.println("activeCars contains " + activeCars.size() + " items");
        car.handleDataFromClients(false,false,0);
        activeCars.remove(activeCarIndex);
        if(activeCars.size() == 1) {
            weHaveAWinner();
        }
    }

    private void weHaveAWinner(){
        varsomSystem.getMPServer().gameRunning(false);
        ((Game) Gdx.app.getApplicationListener()).setScreen(new ResultScreen(varsomSystem,sortedCars2String()));

        //new clients can join now when the game is over
        varsomSystem.getMPServer().setJoinable(true);

    }

    private void addActiveCars(){
        for(Car car : track.getCars()){
            activeCars.add(car);
            sortedCars.add(car);
        }
        System.out.println("activeCars contains " + activeCars.size() + " items");
    }
    private void sortCars(){
        Collections.sort(sortedCars);
        String a = "Car order";
        for(int i = 0; i < sortedCars.size(); i++){
            a += sortedCars.get(i).getID() + " ";
        }
    //    System.out.println(a);
    }

    private String sortedCars2String(){
        String temp = "";
        for(int i = 0; i < sortedCars.size() ; i++){
            try{
                temp += /*i+1 + ". " +*/ sortedCars.get(i).getConnectionName()+"\n";

            } catch(Exception e){
                temp += /*i+1 + ". */"*NoConnection*\n";
            }
        }
        return temp;
    }

    private void createDiePulse(){
        diePulse = "0 125 125 125 350 125 125 125 350";
        int pause = 0;
        for(int i = 0 ; i < 200; i++){
            if(i % 10 == 0 ){
                pause++;
            }
            diePulse += " 5 " + pause;
        }
        System.out.println("PULSE: = " + diePulse);
    }

}