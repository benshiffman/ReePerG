package com.example.bshiffman5629.reeperg;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    private GLSurfaceView mGLView;

    /*private EditText point0X;
    private EditText point0Y;

    private EditText point1X;
    private EditText point1Y;

    private EditText point2X;
    private EditText point2Y;

    private EditText point3X;
    private EditText point3Y;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
        //setContentView(R.layout.activity_main);
        //mGLView = findViewById(R.id.glSurfaceViewID);
    }
}
