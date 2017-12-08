package com.example.bshiffman5629.reeperg;

import android.content.Context;

/**
 * Created by Varas on 08/12/2017.
 */

public class TestShader extends Shader {
    public TestShader(Context ctx) {
        super(ctx, "res/testvs", "res/testfs.fs");
    }
    void setUniforms() {}
}
