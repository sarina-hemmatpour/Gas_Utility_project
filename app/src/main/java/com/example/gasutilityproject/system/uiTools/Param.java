package com.example.gasutilityproject.system.uiTools;

import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

public class Param {

    public static ConstraintLayout.LayoutParams consParam(int w, int h) {
        return new ConstraintLayout.LayoutParams(w, h);
    }

    public static ConstraintLayout.LayoutParams consParam(int w, int h, int top, int left, int right, int bottom) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(w, h);
        if (top != -1)
            if (top >= 0)
                layoutParams.topToTop = top;
            else
                layoutParams.topToBottom = top * -1;
        if (bottom != -1)
            if (bottom >= 0)
                layoutParams.bottomToBottom = bottom;
            else
                layoutParams.bottomToTop = bottom * -1;
        if (left != -1)
            if (left >= 0)
                layoutParams.leftToLeft = left;
            else
                layoutParams.leftToRight = left * -1;
        if (right != -1)
            if (right >= 0)
                layoutParams.rightToRight = right;
            else
                layoutParams.rightToLeft = right * -1;
        return layoutParams;
    }

    public static ConstraintLayout.LayoutParams consParam(int w, int h, int top, int left, int right, int bottom, float bV, float bH) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(w, h);
        if (top != -1)
            if (top >= 0)
                layoutParams.topToTop = top;
            else
                layoutParams.topToBottom = top * -1;
        if (bottom != -1)
            if (bottom >= 0)
                layoutParams.bottomToBottom = bottom;
            else
                layoutParams.bottomToTop = bottom * -1;
        if (left != -1)
            if (left >= 0)
                layoutParams.leftToLeft = left;
            else
                layoutParams.leftToRight = left * -1;
        if (right != -1)
            if (right >= 0)
                layoutParams.rightToRight = right;
            else
                layoutParams.rightToLeft = right * -1;

        if (bV != -1)
            layoutParams.verticalBias = bV;

        if (bH != -1)
            layoutParams.horizontalBias = bH;

        return layoutParams;
    }

    public static ConstraintLayout.LayoutParams consParam(int w, int h, int top, int left, int right, int bottom, int mT, int mL, int mR, int mB) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(w, h);
        if (top != -1)
            if (top >= 0)
                layoutParams.topToTop = top;
            else
                layoutParams.topToBottom = top * -1;
        if (bottom != -1)
            if (bottom >= 0)
                layoutParams.bottomToBottom = bottom;
            else
                layoutParams.bottomToTop = bottom * -1;
        if (left != -1)
            if (left >= 0)
                layoutParams.leftToLeft = left;
            else
                layoutParams.leftToRight = left * -1;
        if (right != -1)
            if (right >= 0)
                layoutParams.rightToRight = right;
            else
                layoutParams.rightToLeft = right * -1;

        if (mT != -1)
            layoutParams.topMargin = mT;
        if (mB != -1)
            layoutParams.bottomMargin = mB;
        if (mL != -1)
            layoutParams.leftMargin = mL;
        if (mR != -1)
            layoutParams.rightMargin = mR;

        return layoutParams;
    }

    public static ConstraintLayout.LayoutParams consParam(int w, int h, int top, int left, int right, int bottom, int mT, int mL, int mR, int mB, float bV, float bH) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(w, h);
        if (top != -1)
            if (top >= 0)
                layoutParams.topToTop = top;
            else
                layoutParams.topToBottom = top * -1;
        if (bottom != -1)
            if (bottom >= 0)
                layoutParams.bottomToBottom = bottom;
            else
                layoutParams.bottomToTop = bottom * -1;
        if (left != -1)
            if (left >= 0)
                layoutParams.leftToLeft = left;
            else
                layoutParams.leftToRight = left * -1;
        if (right != -1)
            if (right >= 0)
                layoutParams.rightToRight = right;
            else
                layoutParams.rightToLeft = right * -1;

        if (mT != -1)
            layoutParams.topMargin = mT;
        if (mB != -1)
            layoutParams.bottomMargin = mB;
        if (mL != -1)
            layoutParams.leftMargin = mL;
        if (mR != -1)
            layoutParams.rightMargin = mR;

        if (bV != -1)
            layoutParams.verticalBias = bV;
        if (bH != -1)
            layoutParams.horizontalBias = bH;

        return layoutParams;
    }

    public static ConstraintLayout.LayoutParams consParam(int w, int h, int top, int left, int right, int bottom, int mT, int mL, int mR, int mB, int clipW, int clipH, float bV, float bH) {
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(w, h);
        if (top != -1)
            if (top >= 0)
                layoutParams.topToTop = top;
            else
                layoutParams.topToBottom = top * -1;
        if (bottom != -1)
            if (bottom >= 0)
                layoutParams.bottomToBottom = bottom;
            else
                layoutParams.bottomToTop = bottom * -1;
        if (left != -1)
            if (left >= 0)
                layoutParams.leftToLeft = left;
            else
                layoutParams.leftToRight = left * -1;
        if (right != -1)
            if (right >= 0)
                layoutParams.rightToRight = right;
            else
                layoutParams.rightToLeft = right * -1;

        if (mT != -1)
            layoutParams.topMargin = mT;
        if (mB != -1)
            layoutParams.bottomMargin = mB;
        if (mL != -1)
            layoutParams.leftMargin = mL;
        if (mR != -1)
            layoutParams.rightMargin = mR;

        if (clipH != -1)
            if (clipH > 0)
                layoutParams.matchConstraintMaxHeight = clipH;
            else
                layoutParams.matchConstraintMinHeight = clipH * -1;

        if (clipW != -1)
            if (clipW > 0)
                layoutParams.matchConstraintMaxWidth = clipW;
            else
                layoutParams.matchConstraintMinWidth = clipW * -1;

        if (bV != -1)
            layoutParams.verticalBias = bV;
        if (bH != -1)
            layoutParams.horizontalBias = bH;

        return layoutParams;
    }

    public static FrameLayout.LayoutParams frameParam(int w, int h) {
        return new FrameLayout.LayoutParams(w, h);
    }

    public static FrameLayout.LayoutParams frameParam(int w, int h, int gravity) {
        return new FrameLayout.LayoutParams(w, h, gravity);
    }

    public static FrameLayout.LayoutParams frameParam(int w, int h, int mT, int mL, int mR, int mB) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(w, h);
        layoutParams.setMargins(mL, mT, mR, mB);
        return layoutParams;
    }

    public static FrameLayout.LayoutParams frameParam(int w, int h, int gravity, int mT, int mL, int mR, int mB) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(w, h, gravity);
        layoutParams.setMargins(mL, mT, mR, mB);
        return layoutParams;
    }

    public static LinearLayout.LayoutParams linearParam(int w, int h, int mT, int mL, int mR, int mB) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, h);
        layoutParams.setMargins(mL, mT, mR, mB);
        return layoutParams;
    }

    public static LinearLayout.LayoutParams linearParam(int w, int h, float weight, int mT, int mL, int mR, int mB) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(w, h, weight);
        layoutParams.setMargins(mL, mT, mR, mB);
        return layoutParams;
    }
}
