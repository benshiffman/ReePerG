package com.example.bshiffman5629.reeperg;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
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

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();

    Timer timer = new Timer();

    Gestures curGesture;

    boolean up = false;
    boolean left = false;
    boolean right = false;

    private GestureDetectorCompat mDetector;//I really want to delete this
    private GLSurfaceView mGLView;
    class GameLoopTask extends TimerTask {
        public void run() {
            //GAME LOOP
            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    float acceleration = 0.0f;
                    if (right) {
                        acceleration = 0.5f;
                    }else if (left) {
                        acceleration = -0.5f;
                    }
                    for (int i = 1;i < MyGLRenderer.mainInstance.map.length;i += 2) {
                        if (MyGLRenderer.mainInstance.mainPlayer.xPos < MyGLRenderer.mainInstance.map[i].x) {
                            if (MyGLRenderer.mainInstance.mainPlayer.xPos > MyGLRenderer.mainInstance.map[i - 1].x) {
                                Log.d(Integer.toString((int)MyGLRenderer.mainInstance.mainPlayer.yPos), Integer.toString((int)MyGLRenderer.mainInstance.map[i].y));
                                if (MyGLRenderer.mainInstance.mainPlayer.yPos > MyGLRenderer.mainInstance.map[i].y) {
                                    MyGLRenderer.mainInstance.mainPlayer.updatePos(acceleration, -1f, false);
                                }else {
                                    if (up) {
                                        MyGLRenderer.mainInstance.mainPlayer.updatePos(acceleration, 20f, false);
                                    }else {
                                        MyGLRenderer.mainInstance.mainPlayer.updatePos(acceleration, 0.0f, true);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int FPS = 40;
        TimerTask update = new GameLoopTask();
        timer.scheduleAtFixedRate(update, 500, 1000/FPS);

        super.onCreate(savedInstanceState);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);

        Rect spellBox = new Rect(metrics.widthPixels - metrics.heightPixels/2, metrics.heightPixels/2, metrics.widthPixels, metrics.heightPixels);
        curGesture = new Gestures(spellBox);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        checkGesture(event);
        checkMovement(event);
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onDown(MotionEvent event) {

        return true;
    }
    private void checkGesture(MotionEvent event) {//WIP
        if (event.getAction() == MotionEvent.ACTION_UP) {
            ArrayList<Integer> gestureValues = curGesture.end();
            float pos[] = new float[gestureValues.size()*3];
            short order[] = new short[gestureValues.size()*2];
            float xv = (float)metrics.heightPixels/(float)metrics.widthPixels;
            for (int i = 0; i < gestureValues.size(); i++) {
                int v = gestureValues.get(i);
                Log.d("gestureVal[" + Integer.toString(i) + "] = ", Integer.toString(v));
                if (v == 0) {
                    pos[i*3] = 1f - 5f*xv/6f;
                    pos[i*3 + 1] = -1f/6f;
                }else if (v == 1) {
                    pos[i*3] = 1f - 5f*xv/6f;
                    pos[i*3 + 1] = -0.5f;
                }else if (v == 2) {
                    pos[i*3] = 1f - 5f*xv/6f;
                    pos[i*3 + 1] = -5f/6f;
                }else if (v == 3) {
                    pos[i*3] = 1f - xv/2f;
                    pos[i*3 + 1] = -1f/6f;
                }else if (v == 4) {
                    pos[i*3] = 1f - xv/2f;
                    pos[i*3 + 1] = -0.5f;
                }else if (v == 5) {
                    pos[i*3] = 1f - xv/2f;
                    pos[i*3 + 1] = -5f/6f;
                }else if (v == 6) {
                    pos[i*3] = 1f - 1f*xv/6f;
                    pos[i*3 + 1] = -1f/6f;
                }else if (v == 7) {
                    pos[i*3] = 1f - 1f*xv/6f;
                    pos[i*3 + 1] = -0.5f;
                }else if (v == 8) {
                    pos[i*3] = 1f - 1f*xv/6f;
                    pos[i*3 + 1] = -5f/6f;
                }
                pos[i*3 + 2] = 0.0f;
                if (i>0) {
                    order[i*2] = (short) (i-1);
                    order[i*2 + 1] = (short) i;
                }
            }
            MyGLRenderer.mainInstance.gesturePath.coords = pos;
            MyGLRenderer.mainInstance.gesturePath.drawOrder = order;
        }else {
            if (curGesture.bounds.contains((int) event.getX(), (int) event.getY())) {
                curGesture.update(new Point((int) event.getX(), (int) event.getY()));
            }else if (curGesture.started) {
                ArrayList<Integer> gestureValues = curGesture.end();
                float pos[] = new float[gestureValues.size()*3];
                short order[] = new short[gestureValues.size()*2];
                float xv = (float)metrics.heightPixels/(float)metrics.widthPixels;
                for (int i = 0; i < gestureValues.size(); i++) {
                    int v = gestureValues.get(i);
                    Log.d("gestureVal[" + Integer.toString(i) + "] = ", Integer.toString(v));
                    if (v == 0) {
                        pos[i*3] = 1f - 5f*xv/6f;
                        pos[i*3 + 1] = -1f/6f;
                    }else if (v == 1) {
                        pos[i*3] = 1f - 5f*xv/6f;
                        pos[i*3 + 1] = -0.5f;
                    }else if (v == 2) {
                        pos[i*3] = 1f - 5f*xv/6f;
                        pos[i*3 + 1] = -5f/6f;
                    }else if (v == 3) {
                        pos[i*3] = 1f - xv/2f;
                        pos[i*3 + 1] = -1f/6f;
                    }else if (v == 4) {
                        pos[i*3] = 1f - xv/2f;
                        pos[i*3 + 1] = -0.5f;
                    }else if (v == 5) {
                        pos[i*3] = 1f - xv/2f;
                        pos[i*3 + 1] = -5f/6f;
                    }else if (v == 6) {
                        pos[i*3] = 1f - 1f*xv/6f;
                        pos[i*3 + 1] = -1f/6f;
                    }else if (v == 7) {
                        pos[i*3] = 1f - 1f*xv/6f;
                        pos[i*3 + 1] = -0.5f;
                    }else if (v == 8) {
                        pos[i*3] = 1f - 1f*xv/6f;
                        pos[i*3 + 1] = -5f/6f;
                    }
                    pos[i*3 + 2] = 0.0f;
                    if (i>0) {
                        order[i*2] = (short) (i-1);
                        order[i*2 + 1] = (short) i;
                    }
                }
                MyGLRenderer.mainInstance.gesturePath.coords = pos;
                MyGLRenderer.mainInstance.gesturePath.drawOrder = order;
            }
        }
    }
    private void checkMovement(MotionEvent event) {
        //slightly easier and more efficient to go by x or y first, ex. below
        //I moved this to a seperate function so it is easier to read. We will probably be doing a lot of things on touch event so it helps to compartmentalize.
        /*
           if x < 100 {
               if y < 100 {
                    //bot left
               }else if y < 200 {
                    //top left
               }
           }else if x < 200 {
               if y < 100 {
                    //bot mid
               }else if y < 200 {
                    //top mid
               }
           }else if x < 300 {
               if y < 100 {
                    //bot right
               }else if y < 200 {
                    //top right
               }
           }
         */
        up = false;
        right = false;
        left = false;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            return;
        }
        float x = (float)(event.getX()/metrics.widthPixels);
        float y = (float)(event.getY()/metrics.heightPixels);
        if(x > 0f && x < 1f/10f && y > 2f/3f && y < 5f/6f){                                              //Top Left
            //Log.d("top", "Left");
            up = true;
            left = true;

        }
        if(x > 1f/10f && x < 2f/10f && y > 2f/3f && y < 5f/6f){                                          //Top Middle
            //Log.d("top", "mid");
            up = true;
        }
        if(x > 2f/10f && x < 3f/10f && y > 2f/3f && y < 5f/6f){                                         //Top Right
            //Log.d("top", "right");
            up = true;
            right = true;
        }
        if(x > 0f && x < 1f/10f && y > 5f/6f && y < 1f){                                                 //Left
            //Log.d("mid", "Left");
            left = true;
        }
        if(x > 2f/10f && x < 3f/10f && y > 5f/6f && y < 1f){                                             //Right
            //Log.d("mid", "right");
            right = true;
        }
        if(x >  1f/10f && x < 2f/10f && y > 5f/6f && y < 1f){                                              //Down (Temporary)
            //Log.d("down", "blyat");
            for(int y2 = 1; y2 < MyGLRenderer.mainInstance.mShape.shapeCoords.length; y2 += 3) {
                MyGLRenderer.mainInstance.mShape.shapeCoords[y2] += -0.1f;
            }
        }
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
