package com.example.gasutilityproject.system.uiTools.Animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CircularRotateAnimation extends Animation {

    private final View view;
    private float cx, cy;
    private float prevX, prevY;
    private final float r;
    private float prevDx = 0, prevDy = 0;
    private final int defaultDeg;

    public CircularRotateAnimation(View view, float r, int defaultDeg) {
        this.view = view;
        this.r = r;
        this.defaultDeg = defaultDeg;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {

        cx = view.getWidth() / 2.0f;
        cy = view.getHeight() / 2.0f;

        setRepeatCount(android.view.animation.Animation.INFINITE);
        setRepeatMode(Animation.RESTART);
        setDuration(20000);

        prevX = cx;
        prevY = cy;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        if (interpolatedTime == 0) {
            t.getMatrix().setTranslate(prevDx, prevDy);
            return;
        }

        float angleDeg = (interpolatedTime * 360f + defaultDeg) % 360;
        float angleRad = (float) Math.toRadians(angleDeg);

        float x = (float) (cx + r * Math.cos(angleRad));
        float y = (float) (cy + r * Math.sin(angleRad));

        float dx = prevX - x;
        float dy = prevY - y;

        prevX = x;
        prevY = y;

        prevDx = dx;
        prevDy = dy;

        t.getMatrix().setTranslate(dx, dy);
    }

}
