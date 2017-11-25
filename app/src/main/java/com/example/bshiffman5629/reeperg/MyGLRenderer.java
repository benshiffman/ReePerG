package com.example.bshiffman5629.reeperg;

import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.shapes.PathShape;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;



public class MyGLRenderer implements GLSurfaceView.Renderer {
    public static MyGLRenderer mainInstance;

    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    public float xv = (float)metrics.heightPixels/((float)metrics.widthPixels);
    public  Shape           mShape;
    private Paths           mPaths;
    public  Paths           gesturePath;
    public ArrayList<Integer> gesture = new ArrayList<Integer>();

    public Player mainPlayer;

    Paths floor;

    Point map[] = {
            new Point(-3250, 3250),
            new Point(0, 3250),
            new Point(0, 1500),
            new Point(1500, 1500),
            new Point(1500, 2250),
            new Point(3000, 2250),
            new Point(3000, 1750),
            new Point(5750, 1750),
            new Point(5750, -250),
            new Point(6500, -250),
            new Point(6500, 1750),
            new Point(7750, 1750),
            new Point(7750, 4000),
            new Point(10000, 4000)
    };

    static float shapeCoords[] = { //counter-clockwise
            -0.25f, 0.5f, 0.0f,   // 0 -- 0, 1, 2
            -0.25f, -0.5f, 0.0f,  // 1 -- 3, 4, 5
            0.25f,  -0.5f, 0.0f, // 2 -- 6, 7, 8
            0.25f, 0.5f, 0.0f }; // 3 -- 9, 10, 11
    private short shapeDrawOrder[] = {0, 1, 2,// I would encourage moving these variables to inside the constructor
                                      0, 2, 3/*,
                                      0, 3, 4*/};

    float pathsCoords[] = {
            //Dpad
            -0.4f-xv,           -1f/3f,     0f,
            -0.4f-(2f/3f)*xv,   -1f/3f,     0f,
            -0.4f-(1f/3f)*xv,   -1f/3f,     0f,
            -0.4f,              -1f/3f,     0f,
            -0.4f-xv,           -2f/3f,     0f,
            -0.4f,              -2f/3f,     0f,
            -0.4f-xv,           -1f,        0f,
            -0.4f-(2f/3f)*xv,   -1f,        0f,
            -0.4f-(1f/3f)*xv,   -1f,        0f,
            -0.4f,              -1f,        0f,

            //Spell Grid
            1f-xv,              0f,         0f,
            1f-(2f/3f)*xv,      0f,         0f,
            1f-(1f/3f)*xv,      0f,         0f,
            1f,                 0f,         0f,
            1f-xv,              -1f/3f,     0f,
            1f,                 -1f/3f,     0f,
            1f-xv,              -2f/3f,     0f,
            1f,                 -2f/3f,     0f,
            1f-xv,              -1f,        0f,
            1f-(2f/3f)*xv,      -1f,        0f,
            1f-(1f/3f)*xv,      -1f,        0f,
            1f,                 -1f,        0f };
    private short pathsDrawOrder[] = {0, 6, 1, 7, 2, 8, 3, 9, 0, 3, 4, 5, 6, 9, 10, 18, 11, 19, 12, 20, 13, 21, 10, 13, 14, 15, 16, 17, 18, 21};

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        mainInstance = this;
        // Set the background frame color
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        //initialize a custom shape
        mShape = new Shape(shapeCoords, shapeDrawOrder);
        //initialize a new path
        mPaths = new Paths(pathsCoords, pathsDrawOrder);

        gesturePath = new Paths(new float[]{0f, 0f, 0f}, new short[]{0, 0});
        gesturePath.color = new float[]{1f, 0f, 0f, 1f};

        mainPlayer = new Player(4000, 1850);

        float mapCoords[] = new float[map.length*3];
        short order[] = {0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13};
        //14 pts
        for (int i = 0; i<map.length;i++) {
            mapCoords[i*3] = 2f*((float) map[i].x - mainPlayer.xPos)/(float) metrics.widthPixels;
            mapCoords[i*3 + 1] = 2f*((float) map[i].y - mainPlayer.yPos)/(float) metrics.heightPixels;
            mapCoords[i*3 + 2] = 0f;
        }
        floor = new Paths(mapCoords, order);
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        //14 pts
        float mapCoords[] = new float[map.length*3];
        for (int i = 0; i<map.length;i++) {
            mapCoords[i*3] = 2f*((float) map[i].x - mainPlayer.xPos)/(float) metrics.widthPixels;
            mapCoords[i*3 + 1] = 2f*((float) map[i].y - mainPlayer.yPos - 300)/(float) metrics.heightPixels;
            mapCoords[i*3 + 2] = 0f;
        }
        floor.coords = mapCoords;

        floor.draw();
        mainPlayer.sprite.draw();

        mPaths.draw();
        gesturePath.draw();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES30.glViewport(0, 0, width, height);

        mShape = new Shape(shapeCoords, shapeDrawOrder);
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES30.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES30.GL_FRAGMENT_SHADER)
        int shader = GLES30.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES30.glShaderSource(shader, shaderCode);
        GLES30.glCompileShader(shader);

        return shader;
    }
}
