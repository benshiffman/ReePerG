package com.example.bshiffman5629.reeperg;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Varas on 04/12/2017.
 */

public abstract class Shader {
    public int programID;
    public Shader(Context ctx, int vertID, int fragID) {
        String shaderCode = "";
        Resources resources = MainActivity.mainInstance.getResources();
        Scanner vScanner = new Scanner(resources.openRawResource(vertID));
        Scanner fScanner = new Scanner(resources.openRawResource(fragID));
        while (vScanner.hasNextLine()) {
            shaderCode += vScanner.nextLine();
        }
        int vShader = GLES30.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES30.glShaderSource(vShader, shaderCode);
        GLES30.glCompileShader(vShader);

        shaderCode = "";
        while (fScanner.hasNextLine()) {
            shaderCode += fScanner.nextLine();
        }
        int fShader = GLES30.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES30.glShaderSource(fShader, shaderCode);
        GLES30.glCompileShader(fShader);

        programID = GLES30.glCreateProgram();
        GLES30.glAttachShader(programID, vShader);
        GLES30.glAttachShader(programID, fShader);
        GLES30.glLinkProgram(programID);
        //bindtexture
    }
    abstract void setUniforms();
}
