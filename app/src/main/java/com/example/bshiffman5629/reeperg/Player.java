package com.example.bshiffman5629.reeperg;

/**
 * Created by Varas on 24/11/2017.
 */

public class Player {
    Shape sprite;
    float xvelocity = 0;
    float yvelocity = 0;
    float xPos;
    float yPos;
    public Player(float x, float y) {
        float shapeCoords[] = { //counter-clockwise
                -0.15f, 0.5f, 0.0f,   // 0 -- 0, 1, 2
                -0.15f, -0.5f, 0.0f,  // 1 -- 3, 4, 5
                0.15f,  -0.5f, 0.0f, // 2 -- 6, 7, 8
                0.15f, 0.5f, 0.0f }; // 3 -- 9, 10, 11
        short shapeDrawOrder[] = {0, 1, 2,
                0, 2, 3};
        sprite = new Shape(shapeCoords, shapeDrawOrder);
        xPos = x;
        yPos = y;
    }
    public void updatePos(float xacceleration, float yacceleration, boolean haltYV) {
        xvelocity += xacceleration;
        yvelocity += yacceleration;
        if (haltYV) {
            yvelocity = 0;
            if (xvelocity > 0.25f) {
                xvelocity -= 0.2f;
            }else if (xvelocity < -0.25f) {
                xvelocity += 0.2f;
            }else {
                xvelocity = 0;
            }
        }
        xPos += xvelocity;
        yPos += yvelocity;
    }
}
