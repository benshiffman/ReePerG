package com.example.bshiffman5629.reeperg;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

public class Gestures {
    public Rect bounds;
    public Point firstPt;
    public Point lastPt;
    public double firstDir;
    public boolean started = false;
    public ArrayList<Integer> gesture = new ArrayList<Integer>();//this is the gesture
    public Gestures(Rect from) {//from should be the coordinates of the box that the spells will be cast in.
        bounds = from;
    }
    public void reInit(Point with) {
        gesture = new ArrayList<Integer>();
        started = true;
        gesture.add(cordOf(with));
        lastPt = with;
    }
    public static double findDir(Point from, Point to) {
        double deg = Math.toDegrees(Math.atan(((double)from.x - (double)to.x)/((double)from.y - (double)to.y)));
        if (from.x < to.x) {
            return (deg + 90);
        }else {
            return (deg - 90);
        }
    }
    public static double compare(double a, double b) {
        double diff = a-b;
        if (Math.abs(diff) > 180) {
            return (diff - 360);
        }else {
            return diff;
        }
    }
    //0 1 2
    //3 4 5
    //6 7 8
    //
    //fail: -1
    public int cordOf(Point pt) {
        if(bounds.contains(pt.x, pt.y)) {
            if ((double)pt.x < (double)bounds.left + (double)bounds.width()/3) {//left
                if ((double)pt.y < (double)bounds.top + (double)bounds.height()/3) {
                    return 0;//top
                }else if ((double)pt.y < (double)bounds.top + (double)2*bounds.height()/3) {
                    return 1;//mid
                }else {
                    return 2;//bot
                }
            }else if ((double)pt.x < (double)bounds.left + (double)2*bounds.width()/3) {//middle
                if ((double)pt.y < (double)bounds.top + (double)bounds.height()/3) {
                    return 3;//top
                }else if ((double)pt.y < (double)bounds.top + (double)2*bounds.height()/3) {
                    return 4;//mid
                }else {
                    return 5;//bot
                }
            }else {//right
                if ((double)pt.y < (double)bounds.top + (double)bounds.height()/3) {
                    return 6;//top
                }else if ((double)pt.y < (double)bounds.top + (double)2*bounds.height()/3) {
                    return 7;//mid
                }else {
                    return 8;//bot
                }
            }
        }else {
            return -1;
        }
    }
    public void update(Point with) {//call this every time a new point is given
        if (started) {
            int newCord = cordOf(with);
            if (newCord == -1) {
                lastPt = with;
                return;
            }
            //Log.d("size: ", Integer.toString(gesture.size()));
            if (gesture.size() > 1) {
                if (gesture.get(gesture.size() - 2) != newCord) {
                    if (Math.abs(compare(firstDir, findDir(firstPt, with))) > 20) {
                        gesture.add(newCord);
                        firstDir = findDir(lastPt, with);
                        firstPt = with;
                    } else {
                        gesture.remove(gesture.size() - 1);
                        gesture.add(newCord);
                        firstDir = findDir(lastPt, with);
                        firstPt = with;
                    }
                }
            } else {
                if (gesture.get(0) != newCord) {
                    gesture.add(newCord);
                    firstDir = findDir(lastPt, with);
                    firstPt = with;
                }
            }
            lastPt = with;
        }else {
            reInit(with);
        }
    }
    public ArrayList<Integer> end() {
        if (gesture.size() > 2) {
            if (gesture.get(gesture.size()-1) == gesture.get(gesture.size() - 2)) {
                gesture.remove(gesture.size() - 1);
            }
        }
        for (int i = 0; i + 1<gesture.size();i++) {
            if (gesture.get(i) == gesture.get(i + 1)) {
                gesture.remove(i);
                i--;
            }
        }
        started = false;
        return gesture;
    }
}