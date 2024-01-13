package com.example.gasutilityproject.system.launch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.Account;
import com.example.gasutilityproject.Data.DataBase.Remote.EmitManager;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.StaticFields.Text;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.baseWindows.BaseFragment;
import com.example.gasutilityproject.system.main.MainFragment;
import com.example.gasutilityproject.system.login.LoginFragment;

import org.json.JSONObject;

public class LaunchFragment extends BaseFragment {
    @Override
    protected ViewGroup onViewFragmentCreate(Bundle savedInstanceState) {
        Theme.setUpStatusBar(activity, Colors.background, false);
        parent.setBackgroundColor(Colors.background);

        ImageView imgLogo = new ImageView(activity);
        imgLogo.setAlpha(0f);
        imgLogo.setId(Ids.IMAGE_VIEW_ID_1);
        imgLogo.setImageResource(R.drawable.oil_company_vector_logo);
        parent.addView(imgLogo, Param.consParam(Theme.getAf(500), Theme.getAf(500), 0, 0, 0, 0));

        TextView tvTitle = new TextView(activity);
        tvTitle.setAlpha(0f);
        tvTitle.setText(Text.oilCompanyName);
        tvTitle.setTextSize(0, Dimen.fontSize18);
        tvTitle.setTextColor(Colors.black3);
        tvTitle.setTypeface(AppLoader.mediumTypeface, Typeface.BOLD);
        parent.addView(tvTitle, Param.consParam(-2, -2, -imgLogo.getId(), imgLogo.getId(), imgLogo.getId(), -1, Dimen.m12, -1, -1, -1));

        ValueAnimator valueAnimator = new ValueAnimator().ofFloat(0, 1);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            float value = (float) valueAnimator1.getAnimatedValue();
            tvTitle.setAlpha(value);
            imgLogo.setAlpha(value);
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                AppLoader.handler.postDelayed(() -> manageFragments(), 1000);
            }
        });
        valueAnimator.start();

        return parent;
    }

    private void manageFragments() {
        Log.i("TAG", "manageFragments: ");
        if (AppLoader.account.isEmpty()) {
            activity.popFragment(false);
            activity.pushFragment(new LoginFragment(), null);
        } else {
            EmitManager.getInstance().emitGetUserData(activity, onGetUserData, AppLoader.account);
        }
    }

    private EmitManager.RequestListener onGetUserData = new EmitManager.RequestListener() {
        @Override
        public void onSuccess(Object... response) {
            Employee user = Employee.toModel((JSONObject) response[0]);
            MainFragment.setIsAdmin(user.getJobTitle().equals(Employee.JobTitle.ADMIN));
            Account.getInstance().setUser(user);
            activity.popFragment(false);
            activity.pushFragment(new MainFragment(), "MainFragment");
        }

        @Override
        public void onFailure(Error error) {

        }
    };
}
