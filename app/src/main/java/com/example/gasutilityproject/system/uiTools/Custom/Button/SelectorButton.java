package com.example.gasutilityproject.system.uiTools.Custom.Button;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;


public class SelectorButton extends FrameLayout {

    private TextView txtText;

    private int hover;
    private int enabledTextColor;
    private int disabledTextColor;
    private int enabledBackgroundColor;
    private int disabledBackgroundColor;
    private int enabledIcon = 0;
    private int disabledIcon = 0;
    private boolean isEnabled = false;


    public SelectorButton(@NonNull Context context) {
        super(context);
    }

    public void setup(String text, int hover, int enabledTextColor, int disabledTextColor, int enabledBackgroundColor, int disabledBackgroundColor, OnClickListener onClickListener) {
        removeAllViews();
        this.hover = hover;
        this.enabledTextColor = enabledTextColor;
        this.disabledTextColor = disabledTextColor;
        this.enabledBackgroundColor = enabledBackgroundColor;
        this.disabledBackgroundColor = disabledBackgroundColor;

        setOnClickListener(onClickListener);

        Context context = getContext();
        if (context == null)
            return;

        txtText = new TextView(context);
        txtText.setTextSize(0, Dimen.fontSize18);
        txtText.setTextColor(Colors.black3);
        txtText.setTypeface(AppLoader.mediumTypeface);
        txtText.setText(text);
        txtText.setGravity(Gravity.CENTER);
        txtText.setPadding(0, Theme.getAf(40), 0, Theme.getAf(40));
        setBackground(Theme.createRoundSelectorDrawable(Colors.silverLess, Colors.silverLess, Dimen.r32, Dimen.r32));
        addView(txtText, Param.frameParam(-2, -2, Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, -1, -1, -1, -1));
    }

    public void setIcon(int enabledIcon, int disabledIcon) {
        this.enabledIcon = enabledIcon;
        this.disabledIcon = disabledIcon;
        txtText.setCompoundDrawablesWithIntrinsicBounds(0, 0, disabledIcon, 0);
        txtText.setCompoundDrawablePadding(20);
    }

    public void enable() {
        isEnabled = true;
        setBackground(Theme.createRoundSelectorDrawable(hover, enabledBackgroundColor, Dimen.r32, Dimen.r32));
        txtText.setTextColor(enabledTextColor);
        txtText.setCompoundDrawablesWithIntrinsicBounds(0, 0, enabledIcon, 0);
    }

    public void disable() {
        isEnabled = false;
        setBackground(Theme.createRoundSelectorDrawable(Colors.silverLess, disabledBackgroundColor, Dimen.r32, Dimen.r32));
        txtText.setTextColor(disabledTextColor);
        txtText.setCompoundDrawablesWithIntrinsicBounds(0, 0, disabledIcon, 0);
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void changeText(String text) {
        txtText.setText(text);
    }
}
