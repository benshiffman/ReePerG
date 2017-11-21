package com.example.bshiffman5629.reeperg;

import android.app.Activity;
import android.content.res.Resources;
import android.opengl.GLSurfaceView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();

    private GestureDetectorCompat mDetector;
    private GLSurfaceView mGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        float x = (float)(event.getX()/metrics.widthPixels);
        float y = (float)(event.getY()/metrics.heightPixels);
        if(x > 0f && x < 1f/10f && y > 2f/3f && y < 5f/6f){                                              //Top Left
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
            for(int y2 = 1; y2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; y2 += 3){
                MyGLRenderer.mainInstance.mShape.shapeCoords[y2] += 0.1f;
            }
        }
        if(x > 2f/10f && x < 3f/10f && y > 2f/3f && y < 5f/6f){                                         //Top Right
            for(int x2 = 0; x2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; x2 += 3){
                MyGLRenderer.mainInstance.mShape.shapeCoords[x2] += 0.0707f;
            }
            for(int y2 = 1; y2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; y2 += 3){
                MyGLRenderer.mainInstance.mShape.shapeCoords[y2] += 0.0707f;
            }
        }
        if(x > 0f && x < 1f/10f && y > 5f/6f && y < 1f){                                                 //Left
            for(int x2 = 0; x2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; x2 += 3){
                MyGLRenderer.mainInstance.mShape.shapeCoords[x2] += -0.1f;
            }
        }
        if(x > 2f/10f && x < 3f/10f && y > 5f/6f && y < 1f){                                             //Right
            for(int x2 = 0; x2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; x2 += 3){
                MyGLRenderer.mainInstance.mShape.shapeCoords[x2] += 0.1f;
            }
        }
        if(x >  1f/10f && x < 2f/10f && y > 5f/6f && y < 1f){                                              //Down (Temporary)
            for(int y2 = 1; y2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; y2 += 3) {
                MyGLRenderer.mainInstance.mShape.shapeCoords[y2] += -0.1f;
            }
        }
        return true;
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        //coordinates for e start in top left corner of screen
        //up
        if(y<getHeight()/4 && getWidth()/4<x && x<getWidth()*3/4 && MotionEvent.ACTION_DOWN == e.getAction()){
            for(int i=1; i<=MyGLRenderer.shapeCoords.length; i+=3){
                MyGLRenderer.shapeCoords[i]+=0.05f;
                //MyGLRenderer.mainInstance.mShape.shapeCoords[i]+=0.05f;
            }
            requestRender();
            Log.d("Up x",""+MyGLRenderer.shapeCoords[0]);
            Log.d("Up y",""+MyGLRenderer.shapeCoords[1]);
        }
        //down
        else if(y>getHeight()*3/4 && getWidth()/4<x && x<getWidth()*3/4 && MotionEvent.ACTION_DOWN == e.getAction()){
            for(int i=1; i<=MyGLRenderer.shapeCoords.length; i+=3){
                MyGLRenderer.shapeCoords[i]-=0.05f;
            }
            requestRender();
            Log.d("Down x",""+MyGLRenderer.shapeCoords[0]);
            Log.d("Down y",""+MyGLRenderer.shapeCoords[1]);
        }
        //left
        else if(x<getWidth()/4 && getHeight()/4<y && y<getHeight()*3/4 && MotionEvent.ACTION_DOWN == e.getAction()){
            for(int i=0; i<=MyGLRenderer.shapeCoords.length-1; i+=3){
                MyGLRenderer.shapeCoords[i]-=0.025f;
            }
            requestRender();
            Log.d("Left x",""+MyGLRenderer.shapeCoords[0]);
            Log.d("Left y",""+MyGLRenderer.shapeCoords[1]);
        }
        //right
        else if(x>getWidth()*3/4 && getHeight()/4<y && y<getHeight()*3/4 && MotionEvent.ACTION_DOWN == e.getAction()){
            for(int i=0; i<=MyGLRenderer.shapeCoords.length-1; i+=3){
                MyGLRenderer.shapeCoords[i]+=0.025f;
            }
            requestRender();
            Log.d("Right x",""+MyGLRenderer.shapeCoords[0]);
            Log.d("Right y",""+MyGLRenderer.shapeCoords[1]);
        }

        return true;
    }*/

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
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
