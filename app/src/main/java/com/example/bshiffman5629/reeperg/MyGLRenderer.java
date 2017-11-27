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
    private Paths           mPaths;
    public  Paths           gesturePath;
    //private Shape movementCover;//these are supposed to make the environment covered my ui stuffs, but couldn't figure out opacity/was too lazy
    //private Shape spellCover;

    public ArrayList<Integer> gesture = new ArrayList<Integer>();

    public Player mainPlayer;

    Paths floor;

    Point map[] = {
            new Point(-100000, 3250),
            new Point(0, 3250),
            new Point(0, 1700),
            new Point(1500, 1700),
            new Point(1500, 1900),
            new Point(3000, 1900),
            new Point(3000, 1750),
            new Point(5750, 1750),
            new Point(5750, -250),
            new Point(6250, -250),
            new Point(6250, 1750),
            new Point(7750, 1750),
            new Point(7750, 4000),
            new Point(100000, 4000)
    };

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

        /*float gridCoverCoord[] = {
                1f-xv, 0f, 0f,
                1f-xv, -1f, 0f,
                1f, -1f, 0f,
                1f, 0f, 0f
        };
        float mvmtCoverCoord[] = {
                -0.4f-xv, -1f, 0f,
                -0.4f-xv, -1f/3f, 0f,
                -0.4f, -1f/3f, 0f,
                -0.4f, -1f, 0f
        };
        short sqrOrd[] = {
                0, 1, 2,
                0, 2, 3
        };

        spellCover = new Shape(gridCoverCoord, sqrOrd);
        movementCover = new Shape(mvmtCoverCoord, sqrOrd);
        spellCover.color = new float[] {1.0f, 1.0f, 1.0f, 0.5f};
        movementCover.color = new float[] {1.0f, 1.0f, 1.0f, 0.5f};*/

        //initialize a new path
        mPaths = new Paths(pathsCoords, pathsDrawOrder);

        gesturePath = new Paths(new float[]{0f, 0f, 0f}, new short[]{0, 0});
        gesturePath.color = new float[]{1f, 0f, 0f, 1f};

        mainPlayer = new Player(4000, 1850, metrics);

        float mapCoords[] = new float[map.length*3];
        short order[] = {0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13};
        //14 pts
        for (int i = 0; i<map.length;i++) {
            mapCoords[i*3] = 2f*((float) map[i].x - mainPlayer.xPos)/(float) metrics.widthPixels;//mapPos - playerPos/width (or height)
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


        //movementCover.draw();
        //spellCover.draw();
        mPaths.draw();
        gesturePath.draw();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
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
    public boolean inGround(float x, float y) {
        int crosses = 0;
        for (int i = 1; i < map.length; i++) {
            float fx = (float) map[i - 1].x-x;
            float fy = (float) map[i - 1].y-y;
            float sx = (float) map[i].x-x;
            float sy = (float) map[i].y-y;
            float intersect = (-10*fx)/(sx-fx);
            if (intersect > 0 && intersect < 10) {
                if (((sy - fy)*intersect)/10 + fy > 0) {
                    crosses++;
                }
            }
        }
        return crosses % 2 == 1;
    }
}
