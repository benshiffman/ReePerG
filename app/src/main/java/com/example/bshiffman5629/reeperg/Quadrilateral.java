package com.example.bshiffman5629.reeperg;

import android.opengl.GLES30;
import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by bshiffman5629 on 10/9/2017.
 */

public class Quadrilateral {

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    private int mPositionHandle;
    private int mColorHandle;

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    public void draw() {
        // Add program to OpenGL ES environment
        GLES30.glUseProgram(mProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES30.glGetAttribLocation(mProgram, "vPosition");

        // Enable a handle to the triangle vertices
        GLES30.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the quadrilateral coordinate data
        GLES30.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES30.GL_FLOAT, false, vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES30.glGetUniformLocation(mProgram, "vColor");

        // Set color for drawing the quadrilateral
        GLES30.glUniform4fv(mColorHandle, 1, color, 0);

        // Draw the quadrilateral
        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP, quadrilateralDrawOrder.length, GLES30.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES30.glDisableVertexAttribArray(mPositionHandle);
    }

    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    static float quadrilateralCoords[] = { //counter-clockwise
            0.0f,  1.0f, 0.0f,   // 0
            -1.0f, 0.0f, 0.0f,   // 1
            0.0f, -1.0f, 0.0f,   // 2
            1.0f,  0.0f, 0.0f }; // 3

    
    private short quadrilateralDrawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices (0, 1, 2, 0, 2, 3)

    float color[] = { 1.0f, 0.0f, 0.0f, 1.0f };

    private final int mProgram;

    public Quadrilateral() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 4 bytes per float)
                quadrilateralCoords.length * 4);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(quadrilateralCoords);
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                quadrilateralDrawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(quadrilateralDrawOrder);
        drawListBuffer.position(0);

        int vertexShader = MyGLRenderer.loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = MyGLRenderer.loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES30.glCreateProgram();

        // add the vertex shader to program
        GLES30.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES30.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES30.glLinkProgram(mProgram);
    }

}
