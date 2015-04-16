package com.varsom.system;

import com.badlogic.gdx.Game;
import com.varsom.system.screens.VarsomSplash;

public class VarsomSystem extends /*ApplicationAdapter*/Game {
    public static final String TITLE= "Varsom-System";

    public static final int WIDTH = 800;
    public static final int HEIGHT= 480; // used later to set window size

    public VarsomSystem(){

    }

    @Override
	public void create () {
        setScreen(new VarsomSplash());
        //setScreen(new VarsomMenu());
	}

    @Override
    public void dispose() {
        super.dispose();
    }
}
