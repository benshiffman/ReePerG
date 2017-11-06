package com.example.bshiffman5629.reeperg;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.text.method.Touch;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by bshiffman5629 on 9/27/2017.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    public MyGLSurfaceView(Context context){
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        //coordinates for e start in top left corner of screen
        //up
        if(y<getHeight()/4 && getWidth()/4<x && x<getWidth()*3/4 && MotionEvent.ACTION_DOWN == e.getAction()){
            for(int i=1; i<=(MyGLRenderer.shapeCoords.length+1)/3-1; i+=3){
                MyGLRenderer.shapeCoords[i]+=0.05f;
            }
            Log.d("Up x",""+MyGLRenderer.shapeCoords[0]);
            Log.d("Up y",""+MyGLRenderer.shapeCoords[1]);
            requestRender();
        }
        //down
        else if(y>getHeight()*3/4 && getWidth()/4<x && x<getWidth()*3/4 && MotionEvent.ACTION_DOWN == e.getAction()){
            for(int i=1; i<=(MyGLRenderer.shapeCoords.length+1)/3-1; i+=3){
                MyGLRenderer.shapeCoords[i]-=0.05f;
            }
            requestRender();
            Log.d("Down x",""+MyGLRenderer.shapeCoords[0]);
            Log.d("Down y",""+MyGLRenderer.shapeCoords[1]);
        }
        //left
        else if(x<getWidth()/4 && getHeight()/4<y && y<getHeight()*3/4 && MotionEvent.ACTION_DOWN == e.getAction()){
            for(int i=0; i<=(MyGLRenderer.shapeCoords.length+1)/3-1; i+=3){
                MyGLRenderer.shapeCoords[i]-=0.05f;
            }
            requestRender();
            Log.d("Left x",""+MyGLRenderer.shapeCoords[0]);
            Log.d("Left y",""+MyGLRenderer.shapeCoords[1]);
        }
        //right
        else if(x>getWidth()*3/4 && getHeight()/4<y && y<getHeight()*3/4 && MotionEvent.ACTION_DOWN == e.getAction()){
            for(int i=0; i<=(MyGLRenderer.shapeCoords.length+1)/3-1; i+=3){
                MyGLRenderer.shapeCoords[i]+=0.05f;
            }
            requestRender();
            Log.d("Right x",""+MyGLRenderer.shapeCoords[0]);
            Log.d("Right y",""+MyGLRenderer.shapeCoords[1]);
        }
        /*switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }

                mRenderer.setAngle(
                        mRenderer.getAngle() +
                                ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
        }*/

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}
