package com.example.bshiffman5629.reeperg;

import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;

public class Player {//width 500 height 1000
    Shape sprite;
    float xvelocity = 0;
    float yvelocity = 0;
    float xPos;
    float yPos;
    float currentHP = 500;
    float maxHP = 500;
    float currentMP = 500;
    float maxMP = 500;
    ArrayList<StatEffect> currentEffects = new ArrayList<>();
    public Player(float x, float y, DisplayMetrics metrics) {
        float shapeCoords[] = { //counter-clockwise
                -250f/(float) metrics.widthPixels, 400f/(float) metrics.heightPixels, 0.0f,   // 0 -- 0, 1, 2
                -250f/(float) metrics.widthPixels, -600f/(float) metrics.heightPixels, 0.0f,  // 1 -- 3, 4, 5
                250f/(float) metrics.widthPixels,  -600f/(float) metrics.heightPixels, 0.0f, // 2 -- 6, 7, 8
                250f/(float) metrics.widthPixels, 400f/(float) metrics.heightPixels, 0.0f }; // 3 -- 9, 10, 11
        short shapeDrawOrder[] = {0, 1, 2,
                0, 2, 3};
        sprite = new Shape(shapeCoords, shapeDrawOrder);
        xPos = x;
        yPos = y;
    }
    public void updatePos(float xacceleration, float yacceleration, boolean onGround) {
        xvelocity += xacceleration;
        yvelocity += yacceleration;
        if (onGround) {
            if (xvelocity > 0.45f) {
                xvelocity -= 0.4f;
            }else if (xvelocity < -0.45f) {
                xvelocity += 0.4f;
            }else {
                xvelocity = 0;
            }
            if (xvelocity > 30) {
                xvelocity = 30;
            }
        }
        if (currentHP < maxHP) {
            currentHP += .05;
        }
        if (currentMP < maxMP) {
            currentMP += 0.2;
        }
        if (currentHP > maxHP) {
            currentHP = maxHP;
        }
        if (currentMP > maxMP) {
            currentMP = maxMP;
        }
        xPos += xvelocity;
        yPos += yvelocity;
    }
    public float hpRatio() {
        return currentHP/maxHP;
    }
    public float mpRatio() {
        return currentMP / maxMP;
    }
    public boolean hasEffect(StatEType type) {
        for (int i = 0; i < currentEffects.size();i++) {
            if (currentEffects.get(i).type == type) {
                return true;
            }
        }
        return false;
    }
    public void removeStat(StatEType type) {
        for (int i = 0; i < currentEffects.size();i++) {
            if (currentEffects.get(i).type == type) {
                currentEffects.remove(i);
            }
        }
    }
}
enum StatEType {
    levitate
}
class StatEffect {
    StatEType type;
    int remFrames;
    public StatEffect(StatEType itype) {
        if (type == StatEType.levitate) {
            remFrames = 0;
        }
        type = itype;
    }
}
