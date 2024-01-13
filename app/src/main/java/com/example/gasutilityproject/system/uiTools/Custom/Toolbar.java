package com.example.gasutilityproject.system.uiTools.Custom;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.baseWindows.BaseActivity;

public class Toolbar extends ConstraintLayout {

    private ImageView imgBack;
    private TextView txtTitle;


    public Toolbar(Context context, String title, int titleColor, int backgroundColor, int hoverColor) {
        super(context);

        setBackgroundColor(backgroundColor);

        txtTitle = new TextView(context);
        txtTitle.setText(title);
        txtTitle.setTextColor(titleColor);
        txtTitle.setTypeface(AppLoader.mediumTypeface);
        txtTitle.setTextSize(0, Dimen.fontSize20);
        addView(txtTitle, Param.consParam(-2, -2, 0, 0, 0, 0));

        imgBack = new ImageView(context);
        imgBack.setImageResource(R.drawable.ic_back);
        imgBack.setOnClickListener((view) -> ((BaseActivity) context).popFragment(true));
        imgBack.setBackground(Theme.createRoundSelectorDrawable(hoverColor, Colors.transparent, Theme.getAf(60), Theme.getAf(60)));
        imgBack.setPadding(Theme.getAf(20), Theme.getAf(20), Theme.getAf(20), Theme.getAf(20));
        addView(imgBack, Param.consParam(Theme.getAf(110), Theme.getAf(110), 0, 0, -1, 0, Theme.getAf(35), Theme.getAf(35), -1, Theme.getAf(35)));
    }

    public void changeImgBackListener(OnClickListener onClickListener) {
        imgBack.setOnClickListener(onClickListener);
    }

    public void changeTitle(String title) {
        txtTitle.setText(title);
    }

    public void hideImgBack() {
        imgBack.setVisibility(View.INVISIBLE);
    }
}
