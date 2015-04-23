package com.varsom.system.abstract_gameobjects;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Connection;

public abstract class VarsomGame extends Game {
    //thumbnail
    protected Game varsomSystem;

    public void handleDataFromClients(Connection c, String s) {}
}
