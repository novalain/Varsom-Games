//package screens;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.math.MathUtils;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.utils.Array;
//import java.util.Arrays;
//
//
//
///**
// * Created by Alice on 2015-03-18.
// */
//public class WayFinder implements Screen {
//    private ShapeRenderer sr;
//    private SpriteBatch batch;
//    private Sprite carSprite;
//
//    private Array<MoveSprite> moveSprites;
//
//    @Override
//    public void show() {
//        Gdx.gl.glClearColor(0,0,0,1);
//        batch = new SpriteBatch();
//        sr = new ShapeRenderer();
//
//        carSprite = new Sprite(new Texture("img/car.png"));
//        carSprite.setSize(50,50);
//        carSprite.setOrigin(0,0);
//
//        moveSprites = new Array<MoveSprite>();
//        moveSprites.add(new MoveSprite(carSprite, getChosenPath()));
//
//    }
//
//    // Creating an array of 5-10 waypoints anywhere on the screen (between height and width)
//    private Array<Vector2> getRandomPath() {
//        Array<Vector2> path = new Array<Vector2>();
//        for(int i = 0; i < MathUtils.random(5,10); i++){
//            float randX = MathUtils.random(0,Gdx.graphics.getWidth());
//            float randY = MathUtils.random(0,Gdx.graphics.getHeight());
//            path.add(new Vector2(randX,randY));
//        }
//        return path;
//    }
//
//    private Array<Vector2> getChosenPath(){
//        Array<Vector2> path = new Array<Vector2>();
//
//        float[] waypoints = {
//                100, 400, 45, 355, 100, 300,
//                220, 300, 252, 278, 270, 229,
//                252, 170, 204, 153, 79, 154,
//                43, 116, 93, 70, 504, 70,
//                559, 110, 352, 278, 393, 323,
//                560, 249, 581, 387
//        };
//        for(int i = 0; (i+1) < waypoints.length; i+=2){
//            path.add(new Vector2(waypoints[i], Gdx.graphics.getHeight() - waypoints[i+1]));
//            Gdx.app.log("In chosen path", "point : "+waypoints[i] + " and " + waypoints[i+1]);
//            Gdx.app.log("In chosen path", "size: "+i);
//        }
//
//        return path;
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        batch.begin();
//        for(MoveSprite moveSprite: moveSprites)
//            moveSprite.draw(batch);
//        batch.end();
//
//        for(MoveSprite moveSprite: moveSprites){
//            Vector2 previous = moveSprite.getPath().first();
//
//            for(Vector2 waypoint: moveSprite.getPath()){
//                sr.begin(ShapeRenderer.ShapeType.Line);
//                sr.line(previous, waypoint);
//                sr.end();
//
//                sr.begin(ShapeRenderer.ShapeType.Filled);
//                sr.circle(waypoint.x, waypoint.y, 5);
//                sr.end();
//
//                previous = waypoint;
//            }
//
//        }
//
//    }
//
//    @Override
//    public void resize(int width, int height) {
//
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//        batch.dispose();
//
//    }
//
//    @Override
//    public void dispose() {
//        sr.dispose();
//        batch.dispose();
//        carSprite.getTexture().dispose();
//
//    }
//}