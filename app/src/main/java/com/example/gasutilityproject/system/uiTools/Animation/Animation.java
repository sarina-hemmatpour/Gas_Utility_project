package com.example.gasutilityproject.system.uiTools.Animation;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.example.gasutilityproject.system.uiTools.Theme;

public class Animation {
    public static void alwaysCircularRotateAnimation(View view) {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(2000);
        animator.setRepeatCount(android.view.animation.Animation.INFINITE);
        animator.addUpdateListener(valueAnimator -> view.setRotation((float) valueAnimator.getAnimatedValue()));
        animator.start();
    }

    public static void alwaysCircularRotateAnimation(View view, boolean rotation) { //true=> right | false=>left
        ValueAnimator animator;
        if (rotation)
            animator = ValueAnimator.ofFloat(0, 360);
        else
            animator = ValueAnimator.ofFloat(360, 0);

        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setDuration(2000);
        animator.setRepeatCount(android.view.animation.Animation.INFINITE);
        animator.addUpdateListener(valueAnimator -> view.setRotation((float) valueAnimator.getAnimatedValue()));
        animator.start();
    }

    public static android.view.animation.Animation alwaysTranslateYAnimation(float fromY, float toY, int duration) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, 0, 0, fromY, 0, toY);
        animation.setDuration(duration);
        animation.setFillAfter(false);
        animation.setRepeatCount(android.view.animation.Animation.INFINITE);
        animation.setRepeatMode(android.view.animation.Animation.REVERSE);
        return animation;
    }

    public static void translateInY(View view, int fromY, int toY) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(fromY, toY);
        valueAnimator.setDuration((850 - Theme.getAf(482)) > 0 ? 850 - Theme.getAf(482) :Theme.getAf(278));
        valueAnimator.addUpdateListener(valueAnimatorr -> view.setTranslationY((int) valueAnimatorr.getAnimatedValue()));
        valueAnimator.start();
    }

    public static void translateInY(View view, int duration, int fromY, int toY) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(fromY, toY);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(valueAnimatorr -> view.setTranslationY((int) valueAnimatorr.getAnimatedValue()));
        valueAnimator.start();
    }

    public static android.view.animation.Animation translateXAnimation(float from, float to) {
        TranslateAnimation translateAnimation = new TranslateAnimation(from, to, 0, 0);
        translateAnimation.setDuration(100);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    public static android.view.animation.Animation translateXAndAlphaAnimation(View view, float from, float to, int beginAlpha, int endAlpha, int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        animationSet.addAnimation(new TranslateAnimation(from, to, 0, 0));
        animationSet.addAnimation(new AlphaAnimation(beginAlpha, endAlpha));
        view.startAnimation(animationSet);
        return animationSet;
    }

    public static android.view.animation.Animation translateYAndAlphaAnimation(float fromY, float toY, float fromAlpha, float toAlpha, int duration) {
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        animationSet.addAnimation(new TranslateAnimation(0, 0, fromY, toY));
        animationSet.addAnimation(new AlphaAnimation(fromAlpha, toAlpha));
        return animationSet;
    }

    public static android.view.animation.Animation fadeInFadeOut() {
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        animation.setDuration(1000);
        animation.setFillAfter(false);
        animation.setRepeatCount(android.view.animation.Animation.INFINITE);
        animation.setRepeatMode(android.view.animation.Animation.REVERSE);
        return animation;
    }

    public static void circularRotateAnimation(View parent, View view, float radius, int defaultDeg) {
        android.view.animation.Animation animListening = new CircularRotateAnimation(parent, radius, defaultDeg);
        view.startAnimation(animListening);
    }

    public static void scaleVisible(View view, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(valueAnimatorr -> {
            float value = (float) valueAnimatorr.getAnimatedValue();
            view.setAlpha(value);
            view.setScaleX(value);
            view.setScaleY(value);
            view.setTranslationY(value * Theme.getAf(38));
        });
        valueAnimator.start();
    }

    public static void scaleGone(View view, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(valueAnimatorr -> {
            float value = (float) valueAnimatorr.getAnimatedValue();
            view.setAlpha(value);
            view.setScaleX(value);
            view.setScaleY(value);
            view.setTranslationY(value * Theme.getAf(38));
        });
        valueAnimator.start();
    }

    public static void rotateAnimation(View view) {
        RotateAnimation rotateAnimation = new RotateAnimation(20, -20, android.view.animation.Animation.RELATIVE_TO_SELF, 1, android.view.animation.Animation.RELATIVE_TO_SELF, 1);
        rotateAnimation.setRepeatMode(android.view.animation.Animation.REVERSE);
        rotateAnimation.setDuration(2000);
        rotateAnimation.setRepeatCount(android.view.animation.Animation.INFINITE);
        view.startAnimation(rotateAnimation);
    }

    public static void focusEditText(TextView txtTitle, int startSize, int endSize, int max) {
        ValueAnimator animation = ValueAnimator.ofInt(startSize, endSize);
        animation.setDuration(100);
        animation.addUpdateListener(valueAnimator -> {
            int value = (int) valueAnimator.getAnimatedValue();
            txtTitle.setTextSize(0, value);
            txtTitle.setTranslationY(-(max - value) * 5);
        });
        animation.start();
    }

    public static ValueAnimator recordingAnimation(View view, long duration) {
        float startScale;
        float endScale;
        startScale = 1.0f;
        endScale = 1.2f;
        ValueAnimator animator = ValueAnimator.ofFloat(startScale, endScale);
        animator.setDuration(duration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(animation -> {
            float scale = (float) animation.getAnimatedValue();
            view.setScaleX(scale);
            view.setScaleY(scale);
        });

        return animator;
    }
}
