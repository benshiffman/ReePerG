package com.example.bshiffman5629.reeperg;

import android.graphics.Path;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by bshiffman5629 on 9/27/2017.
 */

public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle        mTriangle;
    private Square          mSquare;
    private Quadrilateral   mQuadrilateral;
    private Shape           mShape;
    private Paths           mPaths;

    static float shapeCoords[] = { //counter-clockwise
            0.0f,  0.75f, 0.0f,   // 0
            -0.5f, 0.5f, 0.0f,   // 1
            -0.5f, 0.0f, 0.0f,  // 2
            0.5f,  0.0f, 0.0f, // 3
            0.5f, 0.5f, 0.0f }; // 4
    private short shapeDrawOrder[] = {0, 1, 2,
                                      0, 2, 3,
                                      0, 3, 4};

    static float pathsCoords[] = { //counter-clockwise
            0.0f,  0.75f, 0.0f,   // 0
            -0.5f, 0.5f, 0.0f,   // 1
            -0.5f, 0.0f, 0.0f,  // 2
            0.5f,  0.0f, 0.0f, // 3
            0.5f, 0.5f, 0.0f }; // 4
    private short pathsDrawOrder[] = {0, 1, 2,
            0, 2, 3,
            0, 3, 4};

    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        // Set the background frame color
        GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        // initialize a triangle
        mTriangle = new Triangle();
        // initialize a square
        mSquare = new Square();
        // initialize a quadrilateral
        mQuadrilateral = new Quadrilateral();
        //initialize a custom shape
        mShape = new Shape(shapeCoords, shapeDrawOrder);
        //initialize a new path
        mPaths = new Paths(pathsCoords, pathsDrawOrder);
    }

    public void onDrawFrame(GL10 unused) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        //mTriangle.draw();
        //mSquare.draw();
        //mQuadrilateral.draw();
        //mShape.draw();
        mPaths.draw();
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

}
