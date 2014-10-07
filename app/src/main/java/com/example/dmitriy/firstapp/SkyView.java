package com.example.dmitriy.firstapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Pair;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.*;


public class SkyView extends View
{
    private Activity act;
    private List <Point> stars;
    private List <Integer> colors; // r == g == b
    private int numOfStars;
    private int period; // ms

    public SkyView(Context context)
    {
        super(context);
        numOfStars = 20;
        period = 300;
        stars = new ArrayList<Point>();
        colors = new ArrayList<Integer>();
        start();
    }

    public void setActivity(Activity a)
    {
        if(act == null)
            act = a;
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawPaint(paint);
        int n = stars.size();
        for(int i=0; i < n; i++)
        {
            int colour = colors.get(i);
            if(colour < 0) // appearing star
            {
                if(colour <= -255)
                {
                    colors.set(i, 255);
                    colour = 255;
                }
                else
                {
                    colors.set(i, colour - 50);
                    colour = -colour;
                }
            }
            else if(colour != 255) // vanishing star
            {

                if (colour - 50 <= 0) {
                    stars.remove(i);
                    colors.remove(i);
                    n--;
                    i--;
                    continue;
                }
                colors.set(i, colour-50);
            }
            drawStar(canvas, stars.get(i).x, stars.get(i).y, colour);
        }
    }

    public void start()
    {
        Timer t = new Timer();
        StarTimer st = new StarTimer();
        t.schedule(st, 0, period);
    }

    public void newStar(int x, int y)
    {
        //canvas.drawPaint(paint);
        if(stars.size() >= numOfStars)
        {
            Random rand = new Random();
            removeStar(rand.nextInt(stars.size()));
        }
        else
        {
            stars.add(new Point(x, y));
            colors.add(-1);
        }

        //invalidate();
    }

    public void removeStar(int i)
    {
        //stars.remove(i);
        if(stars.size() == 0)
            return;
        if(colors.get(i) == 255)
            colors.set(i, 254);
    }

    public void drawStar(Canvas c, int x, int y, int rgb)
    {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(rgb,rgb,rgb));
        c.drawPoint(x, y, paint);
    }

    class StarTimer extends TimerTask
    {

        public void run()
        {

            act.runOnUiThread(new Runnable() {

                public void run() {
                    Random rand = new Random();
                    int branch = rand.nextInt(25);
                    switch(branch)
                    {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5: newStar(rand.nextInt(act.getWindowManager().getDefaultDisplay().getWidth()), rand.nextInt(act.getWindowManager().getDefaultDisplay().getHeight())); break;
                        case 6: if(stars.size() > 0) removeStar(rand.nextInt(stars.size())); break;
                    }
                    invalidate();
                }
            });
        }

    }
}
