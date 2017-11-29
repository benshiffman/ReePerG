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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
                    updatePlayerPos();

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
            if (curGesture.started) {
                ArrayList<Integer> gestureValues = curGesture.end();
                float pos[] = new float[gestureValues.size() * 3];
                short order[] = new short[gestureValues.size() * 2];
                float xv = (float) metrics.heightPixels / (float) metrics.widthPixels;
                short[] rgestVal = new short[gestureValues.size()];
                for (int i = 0; i < gestureValues.size(); i++) {
                    int v = gestureValues.get(i);
                    //Log.d("gestureVal[" + Integer.toString(i) + "] = ", Integer.toString(v));
                    if (v == 0) {
                        pos[i * 3] = 1f - 5f * xv / 6f;
                        pos[i * 3 + 1] = -1f / 6f;
                    } else if (v == 1) {
                        pos[i * 3] = 1f - 5f * xv / 6f;
                        pos[i * 3 + 1] = -0.5f;
                    } else if (v == 2) {
                        pos[i * 3] = 1f - 5f * xv / 6f;
                        pos[i * 3 + 1] = -5f / 6f;
                    } else if (v == 3) {
                        pos[i * 3] = 1f - xv / 2f;
                        pos[i * 3 + 1] = -1f / 6f;
                    } else if (v == 4) {
                        pos[i * 3] = 1f - xv / 2f;
                        pos[i * 3 + 1] = -0.5f;
                    } else if (v == 5) {
                        pos[i * 3] = 1f - xv / 2f;
                        pos[i * 3 + 1] = -5f / 6f;
                    } else if (v == 6) {
                        pos[i * 3] = 1f - 1f * xv / 6f;
                        pos[i * 3 + 1] = -1f / 6f;
                    } else if (v == 7) {
                        pos[i * 3] = 1f - 1f * xv / 6f;
                        pos[i * 3 + 1] = -0.5f;
                    } else if (v == 8) {
                        pos[i * 3] = 1f - 1f * xv / 6f;
                        pos[i * 3 + 1] = -5f / 6f;
                    }
                    pos[i * 3 + 2] = 0.0f;
                    rgestVal[i] = (short) v;
                    if (i > 0) {

                        order[i * 2] = (short) (i - 1);
                        order[i * 2 + 1] = (short) i;
                    }
                }
                gestureCompleted(rgestVal);
                MyGLRenderer.mainInstance.gesturePath.coords = pos;
                MyGLRenderer.mainInstance.gesturePath.drawOrder = order;
            }
        }else {
            if (curGesture.bounds.contains((int) event.getX(), (int) event.getY())) {
                curGesture.update(new Point((int) event.getX(), (int) event.getY()));
            }else if (curGesture.started) {
                ArrayList<Integer> gestureValues = curGesture.end();
                float pos[] = new float[gestureValues.size()*3];
                short order[] = new short[gestureValues.size()*2];
                float xv = (float)metrics.heightPixels/(float)metrics.widthPixels;
                short[] rgestVal = new short[gestureValues.size()];
                for (int i = 0; i < gestureValues.size(); i++) {
                    int v = gestureValues.get(i);
                    //Log.d("gestureVal[" + Integer.toString(i) + "] = ", Integer.toString(v));
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
                    rgestVal[i] = (short) v;
                    if (i>0) {

                        order[i*2] = (short) (i-1);
                        order[i*2 + 1] = (short) i;
                    }
                }
                gestureCompleted(rgestVal);
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
        if(x >  1f/10f && x < 2f/10f && y > 5f/6f && y < 1f){                                              //Down (Temporary) (blyat)
            //Log.d("down", "blyat");
        }
    }
    private void updatePlayerPos() {
        Player mPlayer = MyGLRenderer.mainInstance.mainPlayer;
        float uPush = 0f;
        boolean onGround = false;
        float acceleration = 0.0f;
        if (new GroundData(mPlayer.xPos, mPlayer.yPos).odd) {
            if (mPlayer.yvelocity < 0) {
                if (mPlayer.yvelocity  < -50) {
                    mPlayer.currentHP += (50 + mPlayer.yvelocity)*2;
                }
                mPlayer.yvelocity = 0f;
            }
            onGround = true;
            while (new GroundData(mPlayer.xPos, mPlayer.yPos + 5).odd) {
                mPlayer.yPos += 5;
            }
            if (up) {
                uPush += 27.5f;
            }
        }else {
            uPush -= 1.75f;
        }
        if (onGround) {
            if (right) {
                acceleration = 1f;
            }else if (left) {
                acceleration = -1f;
            }
        }
        if (new GroundData(mPlayer.xPos - 125, mPlayer.yPos + 20).odd) {
            if (acceleration < 0) {
                acceleration = 0;
            }
            mPlayer.xPos += 5;
            if (mPlayer.xvelocity < 0) {
                mPlayer.xvelocity = 0f;
            }
        }
        if (new GroundData(mPlayer.xPos + 125, mPlayer.yPos + 20).odd) {
            if (acceleration > 0) {
                acceleration = 0;
            }
            mPlayer.xPos -= 5;
            if (mPlayer.xvelocity > 0) {
                mPlayer.xvelocity = 0f;
            }
        }
        if (MyGLRenderer.mainInstance.mainPlayer.hasEffect(StatEType.levitate)) {
            if (Math.abs(mPlayer.xvelocity) > 5) {
                uPush = 0;
                if (mPlayer.xvelocity > 0 && left) {
                    acceleration = -1;
                }else if (mPlayer.xvelocity < 0 && right) {
                    acceleration = 1;
                }else {
                    acceleration = 0;
                }
                acceleration += mPlayer.xvelocity * -0.007f;
            }else {
                MyGLRenderer.mainInstance.mainPlayer.removeStat(StatEType.levitate);
            }
            if (uPush < 0) {
                uPush = 0;
            }
        }
        mPlayer.updatePos(acceleration, uPush, onGround);
    }
    public void gestureCompleted(short[] gesture) {
        if (Arrays.equals(gesture, new short[] {1, 4, 7}) || Arrays.equals(gesture, new short[] {1, 7})) {
            if (MyGLRenderer.mainInstance.mainPlayer.xvelocity < 15) {
                MyGLRenderer.mainInstance.mainPlayer.xvelocity = 15;
            }
            MyGLRenderer.mainInstance.mainPlayer.yvelocity = 0;
            MyGLRenderer.mainInstance.mainPlayer.currentEffects.add(new StatEffect(StatEType.levitate));
            MyGLRenderer.mainInstance.mainPlayer.currentMP -= 20;
            return;
        }
        if (Arrays.equals(gesture, new short[] {7, 4, 1}) || Arrays.equals(gesture, new short[] {7, 1})) {
            if (MyGLRenderer.mainInstance.mainPlayer.xvelocity < 15) {
                MyGLRenderer.mainInstance.mainPlayer.xvelocity = -15;
            }
            MyGLRenderer.mainInstance.mainPlayer.currentEffects.add(new StatEffect(StatEType.levitate));
            MyGLRenderer.mainInstance.mainPlayer.currentMP -= 20;
            return;
        }
        if (Arrays.equals(gesture, new short[] {4, 7, 3})) {
            MyGLRenderer.mainInstance.mainPlayer.xvelocity = 40;
            if (MyGLRenderer.mainInstance.mainPlayer.hasEffect(StatEType.levitate)) {
                MyGLRenderer.mainInstance.mainPlayer.currentMP -= 40;
            }
            MyGLRenderer.mainInstance.mainPlayer.currentMP -= 10;
            return;
        }
        if (Arrays.equals(gesture, new short[] {4, 1, 3})) {
            MyGLRenderer.mainInstance.mainPlayer.xvelocity = -40;
            if (MyGLRenderer.mainInstance.mainPlayer.hasEffect(StatEType.levitate)) {
                MyGLRenderer.mainInstance.mainPlayer.currentMP -= 40;
            }
            MyGLRenderer.mainInstance.mainPlayer.currentMP -= 10;
            return;
        }
        if (Arrays.equals(gesture, new short[] {2, 7, 3, 1})) {
            MyGLRenderer.mainInstance.mainPlayer.yvelocity = 30;
            if (MyGLRenderer.mainInstance.mainPlayer.hasEffect(StatEType.levitate)) {
                MyGLRenderer.mainInstance.mainPlayer.currentMP -= 50;
            }
            MyGLRenderer.mainInstance.mainPlayer.currentMP -= 15;
            return;
        }
        /*String pval = "";
        for (int i = 0; i<gesture.length;i++) {
            pval += Short.toString(gesture[i]);
        }
        Log.d("GestureFailwith:", pval);*/
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
