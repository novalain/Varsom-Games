package com.controller_app.helper;

public class InputHandler {

    // order of data:
    // up -> down -> left -> right -> select

    private boolean upPressed = false, downPressed = false, leftPressed = false,
            rightPressed = false, selectPressed = false;

    public InputHandler() {

    }

    public void setUpPressed(){
        upPressed = true;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        selectPressed = false;
    }

    public void setDownPressed(){
        upPressed = false;
        downPressed = true;
        leftPressed = false;
        rightPressed = false;
        selectPressed = false;
    }

    public void setLeftPressed(){
        upPressed = false;
        downPressed = false;
        leftPressed = true;
        rightPressed = false;
        selectPressed = false;
    }

    public void setRightPressed(){
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = true;
        selectPressed = false;
    }

    public void setSelectPressedPressed(){
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        selectPressed = true;
    }

    public String getDataString() {

        // order of data:
        // up -> down -> left -> right -> select

        String s = upPressed + " " + downPressed + " " + leftPressed + " " + rightPressed
                + " " + selectPressed;

        return s;
    }


}
