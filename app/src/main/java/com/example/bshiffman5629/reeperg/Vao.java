package com.example.bshiffman5629.reeperg;

import android.opengl.GLES20;
import android.opengl.GLES30;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Varas on 06/12/2017.
 */

public class Vao {
    public int vboID, iboID, tcboID;
    private float[] vertices;//don't make public, use getters/setters
    private float[] texCoordinates;//don't make public, use getters/setters
    private int[] indices;//don't make public, use getters/setters
    public static int[] squareIndices() {
        return new int[] {
                0, 1, 3,
                3, 1, 2
        };
    }
    public static float[] squareTC() {
        return new float[] {0f, 0f,
                0f, 1f,
                1f, 1f,
                1f, 0f
        };
    }//-+--+-++//
    public Vao(float[] verts, int[] inds, float[] texCoords) {
        int[] vboIDContainer = new int[1], iboIDContainer = new int[1], tcboIDContainer = new int[1];

        GLES30.glGenBuffers(1, vboIDContainer, 0);
        GLES30.glGenBuffers(1, tcboIDContainer, 0);
        GLES30.glGenBuffers(1, iboIDContainer, 0);

        vboID = vboIDContainer[0];
        tcboID = tcboIDContainer[0];
        iboID = iboIDContainer[0];

        setVertices(verts);
        setTexCoordinates(texCoords);
        setIndices(inds);
    }
    public Vao(float x, float y, float width, float height) {
        int[] vboIDContainer = new int[1], iboIDContainer = new int[1], tcboIDContainer = new int[1];

        GLES30.glGenBuffers(1, vboIDContainer, 0);
        GLES30.glGenBuffers(1, tcboIDContainer, 0);
        GLES30.glGenBuffers(1, iboIDContainer, 0);

        vboID = vboIDContainer[0];
        tcboID = tcboIDContainer[0];
        iboID = iboIDContainer[0];

        setVertices(new float[] {
                x, y + height, 0f,
                x, y, 0f,
                x + width, y, 0f,
                x + width, y + width, 0f
        });
        setTexCoordinates(squareTC());
        setIndices(squareIndices());
    }
    public float[] getVertices() {return vertices;}
    public float[] getTexCoordinates() {return texCoordinates;}
    public int[] getIndices() {return indices;}

    public void setVertices(float[] newVal) {
        vertices = newVal;
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboID);
        FloatBuffer vbuffer = ByteBuffer.allocateDirect(newVal.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vbuffer.put(newVal).position(0);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, newVal.length * 4, vbuffer, GLES30.GL_STATIC_DRAW);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
    }
    public void setTexCoordinates(float[] newVal) {
        texCoordinates = newVal;
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, tcboID);
        FloatBuffer tcbuffer = ByteBuffer.allocateDirect(newVal.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        tcbuffer.put(newVal).position(0);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, newVal.length * 4, tcbuffer, GLES30.GL_STATIC_DRAW);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
    }
    public void setIndices(int[] newVal) {
        indices = newVal;
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, iboID);
        IntBuffer ibuffer = ByteBuffer.allocateDirect(newVal.length * 4).order(ByteOrder.nativeOrder()).asIntBuffer();
        ibuffer.put(newVal).position(0);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, newVal.length * 4, ibuffer, GLES30.GL_STATIC_DRAW);
        GLES30.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
    }
    public void render() {
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glEnableVertexAttribArray(1);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, vboID);
        GLES20.glVertexAttribPointer(0, 3, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, tcboID);
        GLES20.glVertexAttribPointer(1, 2, GLES20.GL_FLOAT, false, 0, 0);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, iboID);
        GLES20.glDrawElements(GLES20.GL_TRIANGLE_STRIP, indices.length, GLES20.GL_UNSIGNED_INT, 0);

        GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);

        GLES20.glDisableVertexAttribArray(0);
        GLES20.glDisableVertexAttribArray(1);
    }
}