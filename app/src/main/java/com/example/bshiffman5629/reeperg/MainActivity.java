package com.example.bshiffman5629.reeperg;

import android.app.Activity;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();

    Timer timer = new Timer();

    private GestureDetectorCompat mDetector;
    private GLSurfaceView mGLView;
    class GameLoopTask extends TimerTask {
        public void run() {
            //GAME LOOP
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int FPS = 40;
        TimerTask updateBall = new GameLoopTask();
        timer.scheduleAtFixedRate(updateBall, 0, 1000/FPS);
        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onDown(MotionEvent event) {
        Log.d("Event", "ONDOWN");
        float x = (float)(event.getX()/metrics.widthPixels);
        float y = (float)(event.getY()/metrics.heightPixels);
        if(x > 0f && x < 1f/10f && y > 2f/3f && y < 5f/6f){                                              //Top Left
            Log.d("top", "Left");
            for(int x2 = 0; x2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; x2 += 3){
                //float yeet = (float) Math.sqrt();
                MyGLRenderer.mainInstance.mShape.shapeCoords[x2] += -0.0707f;
            }
            for(int y2 = 1; y2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; y2 += 3) {
                //float yeet = (float) Math.sqrt();
                MyGLRenderer.mainInstance.mShape.shapeCoords[y2] += 0.0707f;
            }
        }
        if(x > 1f/10f && x < 2f/10f && y > 2f/3f && y < 5f/6f){                                          //Top Middle
            Log.d("top", "mid");
            for(int y2 = 1; y2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; y2 += 3){
                MyGLRenderer.mainInstance.mShape.shapeCoords[y2] += 0.1f;
            }
        }
        if(x > 2f/10f && x < 3f/10f && y > 2f/3f && y < 5f/6f){                                         //Top Right
            Log.d("top", "right");
            for(int x2 = 0; x2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; x2 += 3){
                MyGLRenderer.mainInstance.mShape.shapeCoords[x2] += 0.0707f;
            }
            for(int y2 = 1; y2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; y2 += 3){
                MyGLRenderer.mainInstance.mShape.shapeCoords[y2] += 0.0707f;
            }
        }
        if(x > 0f && x < 1f/10f && y > 5f/6f && y < 1f){                                                 //Left
            Log.d("mid", "Left");
            for(int x2 = 0; x2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; x2 += 3){
                MyGLRenderer.mainInstance.mShape.shapeCoords[x2] += -0.1f;
            }
        }
        if(x > 2f/10f && x < 3f/10f && y > 5f/6f && y < 1f){                                             //Right
            Log.d("mid", "right");
            for(int x2 = 0; x2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; x2 += 3){
                MyGLRenderer.mainInstance.mShape.shapeCoords[x2] += 0.1f;
            }
        }
        if(x >  1f/10f && x < 2f/10f && y > 5f/6f && y < 1f){                                              //Down (Temporary)
            Log.d("down", "blyat");
            for(int y2 = 1; y2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; y2 += 3) {
                MyGLRenderer.mainInstance.mShape.shapeCoords[y2] += -0.1f;
            }
        }
        return true;
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }
    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }
    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }
    @Override
    public void onShowPress(MotionEvent motionEvent) {}
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {return false;}
    @Override
    public void onLongPress(MotionEvent motionEvent) {}
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {return false;}
}
