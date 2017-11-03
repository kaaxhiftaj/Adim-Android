package com.adim.techease.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by kaxhiftaj on 10/21/17.
 */

public class CustomRecyclerView extends RecyclerView {
    public CustomRecyclerView(Context context) {
        super(context);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    int lastEvent = -1;

    boolean isLastEventIntercepted = false;
    private float xDistance, yDistance, lastX, lastY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                lastX = e.getX();
                lastY = e.getY();

                break;

            case MotionEvent.ACTION_MOVE:
                final float curX = e.getX();
                final float curY = e.getY();
                xDistance += Math.abs(curX - lastX);
                yDistance += Math.abs(curY - lastY);
                lastX = curX;
                lastY = curY;

                if (isLastEventIntercepted && lastEvent == MotionEvent.ACTION_MOVE) {
                    return false;
                }

                if (xDistance > yDistance) {

                    isLastEventIntercepted = true;
                    lastEvent = MotionEvent.ACTION_MOVE;
                    return false;
                }
        }

        lastEvent = e.getAction();

        isLastEventIntercepted = false;
        return super.onInterceptTouchEvent(e);
    }
}
