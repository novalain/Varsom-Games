import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import gameobjects.Car;

import static org.junit.Assert.assertTrue;

/**
 * Created by michaelnoven on 15-03-12.
 */

public class CarTest {

    private Car car;
    private Texture texture;

    @BeforeClass
    public static void setUpClass(){

        System.out.println("== Set up class");

    }

    @Before
    public void setUp(){

        System.out.println("= Setup");
        car = new Car(new Vector2(0,0), new Vector2(0,0), new Vector2(0,0), null);
      //  car = new Car(null);
    }

    @After
    public void tearDown(){

        System.out.println("= TearDown");
        //car = null;

    }

    @AfterClass
    public static void tearDownClass(){

        System.out.println("== Tear down class");

    }

    @Test
    public void carHasHitBox(){

       // assertTrue(car.getObjectHitbox().getWidth() > 0);
       // assertTrue(car.getObjectHitbox().getHeight() > 0);

    }

}
