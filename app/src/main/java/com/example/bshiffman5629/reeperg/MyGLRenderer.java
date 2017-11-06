package com.example.bshiffman5629.reeperg;

import android.graphics.Path;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by bshiffman5629 on 9/27/2017.
 */

public class MyGLRenderer implements GLSurfaceView.Renderer {

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    /*private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private float[] mRotationMatrix = new float[16];*/

    private Triangle        mTriangle;
    private Square          mSquare;
    private Quadrilateral   mQuadrilateral;
    private Shape           mShape;
    private Paths           mPaths;

    static float shapeCoords[] = { //counter-clockwise
            /*0.0f,  0.65f, 0.0f,*/   // 0
            -0.25f, 0.5f, 0.0f,   // 1
            -0.25f, -0.5f, 0.0f,  // 2
            0.25f,  -0.5f, 0.0f, // 3
            0.25f, 0.5f, 0.0f }; // 4
    private short shapeDrawOrder[] = {0, 1, 2,
                                      0, 2, 3/*,
                                      0, 3, 4*/};

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
        //float[] scratch = new float[16];

        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
        /*Matrix.setLookAtM(mViewMatrix, 0, //Matrix?, wut
                            0,      0,      -3,     //X tilt        Y tilt      Z tilt (zoom?) only -3
                            0f,     0f,     0f,     //smush X       smush Y     smush Z
                            0.0f,   1.0f,   0.0f);  //X vector      Y vector    Z vector*/

        // Calculate the projection and view transformation
        //Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Create a rotation transformation for the triangle
        //long time = SystemClock.uptimeMillis() % 4000L;
        //
        // float angle = 0.090f * ((int) time);
        //Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        //Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        //mTriangle.draw();
        //mSquare.draw();
        //mQuadrilateral.draw();
        mShape.draw(/*mMVPMatrix, scratch*/);
        //mPaths.draw();
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        //float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        //Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
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

    public volatile float mAngle;

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

}
