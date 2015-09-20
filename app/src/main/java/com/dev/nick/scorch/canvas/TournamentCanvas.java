package com.dev.nick.scorch.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Nick on 9/15/2015.
 */
public class TournamentCanvas extends View {
    private int count;
    public int yOffset = 0;
    public int scrolledOffset = 0;
    private Paint p;

    public TournamentCanvas(Context context, int games) {
        super(context);
        //it's best not to create any new objects in the on draw
        //initialize them as class variables here
        p = new Paint();
        count = games;
    }

    @Override
    synchronized public void onDraw(Canvas canvas) {
        //create a black canvas
        p.setColor(Color.parseColor("#CCCCCC"));
        p.setAlpha(255);
        p.setStrokeWidth(4);

        float x1 = (float)canvas.getWidth() - 95;
        float x2 = (float)canvas.getWidth();

        int first = 114;
        int second = 326;
        int diff = second - first;
        float lasty = 114;

        for(int i = 1; i <= count; i++) {
            float y1 = (i == 1 ? first : (i > 2 ? second + (diff * (i - 2)) : second ));
            float y2 = y1;

            if(i % 2 == 0) {
                y2 = lasty;
            }
            else {
                //lasty = y1;

                if(i > 2){
                    lasty += diff;
                    y2 = lasty;
                }
            }

            canvas.drawLine(x1, y1 - yOffset - (scrolledOffset), x2 - 10, y2, p);
            //canvas.drawCircle(x1, y1, 10, p);
            canvas.drawCircle(x2 - 10, y2, 10, p);
            canvas.drawLine(0, y1 - yOffset - (scrolledOffset), 40, y1 - yOffset - (scrolledOffset), p);
            //canvas.drawCircle(0, y1 - yOffset, 10, p);
            //canvas.drawCircle(30, y1, 10, p);
        }

/*
        canvas.drawRect(0, 0, getWidth(), getHeight(), p);
        //initialize the starfield if needed
        if (starField==null) {
            initializeStars(canvas.getWidth(), canvas.getHeight());
        }
        //draw the stars
        p.setColor(Color.CYAN);
        p.setAlpha(starAlpha+=starFade);
        //fade them in and out
        if (starAlpha>=252 || starAlpha <=80) starFade=starFade*-1;
        p.setStrokeWidth(5);
        for (int i=0; i<NUM_OF_STARS; i++) {
            canvas.drawPoint(starField.get(i).x, starField.get(i).y, p);
        }
        */
    }
}
