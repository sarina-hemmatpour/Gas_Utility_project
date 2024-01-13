package com.example.gasutilityproject.system.uiTools.Custom.Button;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;

public class ColoredButton extends FrameLayout {

    private TextView txtText;
    private int hoverColor;
    private int backgroundColor;
    private OnClickListener onClickListener;

    public ColoredButton(Context context) {
        super(context);
    }

    public void setup(String text, int textColor, int hoverColor, int backgroundColor, int fontSize, int icon, boolean isIconRight, OnClickListener onClickListener) {
        removeAllViews();
        this.hoverColor = hoverColor;
        this.backgroundColor = backgroundColor;
        this.onClickListener=onClickListener;
        setOnClickListener(onClickListener);

        Context context = getContext();
        if (context == null)
            return;

        txtText = new TextView(context);
        txtText.setTextSize(0, fontSize);
        txtText.setTextColor(textColor);
        txtText.setTypeface(AppLoader.mediumTypeface);
        txtText.setText(text);
        txtText.setGravity(Gravity.CENTER);
        txtText.setPadding(0, Theme.getAf(40), 0, Theme.getAf(40));
        if (icon != 0) {
            if (isIconRight) {
                txtText.setCompoundDrawablesWithIntrinsicBounds(null, null, Theme.resizeDrawable(context , icon,Dimen.m24 ,Dimen.m24), null);
            } else {
                txtText.setCompoundDrawablesWithIntrinsicBounds(Theme.resizeDrawable(context , icon,Dimen.m24 ,Dimen.m24), null, null, null);
            }
            txtText.setCompoundDrawablePadding(Theme.getAf(20));
        }
        Theme.setTextViewDrawableColor(txtText, textColor);
        setBackground(Theme.createRoundSelectorDrawable(hoverColor, backgroundColor, Dimen.r8, Dimen.r8));
        addView(txtText, Param.frameParam(-2, -2, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, -1, -1, -1, -1));

    }

    public void changePadding(int left, int top, int right, int bottom) {
        txtText.setPadding(0, 0, 0, 0);
        txtText.setPadding(left, top, right, bottom);
    }

    public void changeRadius(int radius) {
        setBackground(Theme.createRoundSelectorDrawable(hoverColor, backgroundColor, radius, radius));
    }

    public void changeBtnListener(OnClickListener onClickListener) {
        setOnClickListener(onClickListener);
    }

    public void changeText(String text) {
        txtText.setText(text);
    }
}
