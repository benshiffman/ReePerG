package com.example.bshiffman5629.reeperg;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Varas on 04/12/2017.
 */

public abstract class Shader {
    public int programID;
    public Shader(Context ctx, String vertPath, String fragPath) {
        String shaderCode = "";
        try {
            InputStream is = ctx.getAssets().open(vertPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderCode += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int vShader = GLES30.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES30.glShaderSource(vShader, shaderCode);
        GLES30.glCompileShader(vShader);
        try {
            InputStream is = ctx.getAssets().open(fragPath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderCode += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        int fShader = GLES30.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES30.glShaderSource(fShader, shaderCode);
        GLES30.glCompileShader(fShader);
        programID = GLES30.glCreateProgram();
        GLES30.glAttachShader(programID, vShader);
        GLES30.glAttachShader(programID, fShader);
        //bindtexture
    }
    abstract void setUniforms();
}
