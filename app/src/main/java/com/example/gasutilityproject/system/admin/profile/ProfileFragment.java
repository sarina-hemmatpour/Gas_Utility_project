package com.example.gasutilityproject.system.admin.profile;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.Account;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.system.login.LoginFragment;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.baseWindows.BaseFragment;
import com.example.gasutilityproject.system.admin.technician.add.PutTechnicianFragment;

public class ProfileFragment extends BaseFragment {
    private Employee employeeModel;
    private TextView tvName;
    private LinearLayout infoLayout;
    public static final int BASE_ID = 5;
    private ImageView imgEdit;
    private String[] titles = new String[]{
            "کد ملی", "تخصص", "شماره تماس",
            "پست الکترونیک", "رمز عبور", "کد پرسنلی ", "امتیاز"
    };

    public void setEmployeeModel(Employee employeeModel) {
        this.employeeModel = employeeModel;
    }

    @Override
    protected ViewGroup onViewFragmentCreate(Bundle savedInstanceState) {
        Theme.setUpStatusBar(activity, Colors.background, false);
        parent.setBackgroundColor(Colors.background);

        ScrollView scrollView = new ScrollView(activity);

        ImageView imgProfile = new ImageView(activity);
        imgProfile.setId(Ids.IMAGE_VIEW_ID_1);
        imgProfile.setImageResource(R.drawable.ic_profile_circle);
        imgProfile.setBackground(Theme.createRoundDrawable(Theme.getAf(300), Theme.getAf(300), Colors.gray1));
        imgProfile.setColorFilter(Colors.gray2);
        imgProfile.setPadding(Theme.getAf(20), Theme.getAf(20), Theme.getAf(20), Theme.getAf(20));
        parent.addView(imgProfile, Param.consParam(Theme.getAf(180), Theme.getAf(180), 0, -1, 0, -1, Dimen.m40, -1, Dimen.m40, -1));

        final int IMG_EDIT_ID = 8485;
        imgEdit = new ImageView(activity);
        imgEdit.setId(IMG_EDIT_ID);
        imgEdit.setImageResource(R.drawable.ic_edit);
        imgEdit.setColorFilter(Colors.gray2);
        imgEdit.setBackground(Theme.createRoundSelectorDrawable(Colors.black10Op, 0, Theme.getAf(100), Theme.getAf(100)));
        imgEdit.setPadding(Theme.getAf(20), Theme.getAf(20), Theme.getAf(20), Theme.getAf(20));
        parent.addView(imgEdit, Param.consParam(Theme.getAf(100), Theme.getAf(100), imgProfile.getId(), 0, -1, imgProfile.getId(), -1, Dimen.m40, -1, -1));
        imgEdit.setOnClickListener(v -> {
            PutTechnicianFragment putTechnicianFragment = new PutTechnicianFragment();
            putTechnicianFragment.setEmployeeModel(employeeModel);
            putTechnicianFragment.setListener(employee -> {
                employeeModel = employee;
                Account.getInstance().setUser(employeeModel);
                resetLayoutData();
            });
            activity.pushFragment(putTechnicianFragment, PutTechnicianFragment.class.getName());
        });

        tvName = new TextView(activity);
        tvName.setId(Ids.TEXT_VIEW_ID_3);
        tvName.setTypeface(AppLoader.mediumTypeface);
        tvName.setTextSize(0, Dimen.fontSize14);
        tvName.setText(employeeModel.getFirstname().trim() + " " + employeeModel.getLastname().trim());
        tvName.setTextColor(Colors.drkTxt);
        parent.addView(tvName, Param.consParam(-2, -2, imgProfile.getId(), -1, -imgProfile.getId(), imgProfile.getId()));

        infoLayout = new LinearLayout(activity);
        infoLayout.setId(34864);
        infoLayout.setOrientation(LinearLayout.VERTICAL);
        infoLayout.setBackground(Theme.createRoundDrawable(Dimen.r16, Dimen.r16, Colors.white));
        infoLayout.setPadding(0, 0, 0, Dimen.m40);
        parent.addView(infoLayout, Param.consParam(-1, -2, -imgProfile.getId(), 0, 0, 0, Dimen.m24, Dimen.m40, Dimen.m40, Dimen.m24));


        String[] information = new String[]{
                employeeModel.getNationalIdNumber(),
                employeeModel.getProficiency(), employeeModel.getPhoneNumber(), employeeModel.getEmailAddress(),
                employeeModel.getPassword(), employeeModel.getPersonnelIdNumber(), String.valueOf(employeeModel.getScore())
        };
        for (int i = 0; i < 7; i++) {
            ConstraintLayout infoItem = createInfoItem(i + BASE_ID, titles[i], information[i]);
            infoLayout.addView(infoItem, Param.linearParam(-1, -2, -1, -1, -1, -1, -1));
        }

        TextView btnExit = new TextView(activity);
        btnExit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_exit, 0, 0, 0);
        btnExit.setTextColor(Colors.red600);
        btnExit.setText("خروج از حساب کاربری");
        btnExit.setTypeface(AppLoader.mediumTypeface);
        btnExit.setTextSize(0, Dimen.fontSize14);
        btnExit.setCompoundDrawablePadding(Theme.getAf(10));
        btnExit.setBackground(Theme.createRoundSelectorDrawable(Colors.black10Op, 0, Theme.getAf(100), Theme.getAf(100)));
        btnExit.setPadding(Theme.getAf(20), Theme.getAf(20), Theme.getAf(20), Theme.getAf(20));
        parent.addView(btnExit, Param.consParam(-2, Theme.getAf(100), -infoLayout.getId(), 0, -1, 0, Dimen.m24, Dimen.m40, -1, Dimen.m40));
        btnExit.setOnClickListener(v -> {
            Account.getInstance().exit();
            activity.pushFragment(new LoginFragment(), PutTechnicianFragment.class.getName());
        });

        scrollView.addView(parent, Param.consParam(-1, -1));
        return scrollView;
    }

    private ConstraintLayout createInfoItem(int id, String title, String info) {
        ConstraintLayout parent = new ConstraintLayout(activity);

        ImageView icDot = new ImageView(activity);
        icDot.setColorFilter(Colors.gray2);
        icDot.setImageResource(R.drawable.ic_item_dot);
        icDot.setId(Ids.IMAGE_VIEW_ID_1);
        parent.addView(icDot, Param.consParam(Theme.getAf(80), Theme.getAf(80), 0, -1, 0, 0, -1, -1, Dimen.m24, -1));


        TextView tvTitle = new TextView(activity);
        tvTitle.setText(title);
        tvTitle.setId(Ids.TEXT_VIEW_ID_1);
        tvTitle.setTextSize(0, Dimen.fontSize18);
        tvTitle.setTypeface(AppLoader.mediumTypeface);
        tvTitle.setTextColor(Colors.black2);
        parent.addView(tvTitle, Param.consParam(-2, -2, icDot.getId(), -1, -icDot.getId(), icDot.getId(), -1, -1, Dimen.m8, -1));

        TextView tvInfo = new TextView(activity);
        tvInfo.setText(info);
        tvInfo.setId(Ids.TEXT_VIEW_ID_3);
        tvInfo.setTextSize(0, Dimen.fontSize18);
        tvInfo.setTypeface(AppLoader.mediumTypeface);
        tvInfo.setGravity(Gravity.CENTER);
        tvInfo.setBackground(Theme.createBorderRoundDrawable(Colors.primaryBlue, Colors.transparent, Dimen.r8));
        tvInfo.setPadding(Theme.getAf(20), 0, Theme.getAf(20), 0);
        tvInfo.setTextColor(Colors.black2);
        parent.addView(tvInfo, Param.consParam(-2, -2, -icDot.getId(), 0, -1, -1, Dimen.m8, Dimen.m24, -1, -1));

        View line = new View(activity);
        line.setBackgroundColor(Colors.gray2);
        parent.addView(line, Param.consParam(0, Theme.getAf(1), tvTitle.getId(), 0, -tvTitle.getId(), tvTitle.getId(), -1, Dimen.m24, Theme.getAf(20), -1));

        return parent;
    }

    private void resetLayoutData() {
        tvName.setText(employeeModel.getFirstname().trim() + " " + employeeModel.getLastname().trim());

        infoLayout.removeAllViews();
        String[] information = new String[]{
                employeeModel.getNationalIdNumber(),
                employeeModel.getProficiency(), employeeModel.getPhoneNumber(), employeeModel.getEmailAddress(),
                employeeModel.getPassword(), employeeModel.getPersonnelIdNumber(), String.valueOf(employeeModel.getScore())
        };
        for (int i = 0; i < 7; i++) {
            ConstraintLayout infoItem = createInfoItem(i + BASE_ID, titles[i], information[i]);
            infoLayout.addView(infoItem, Param.linearParam(-1, -2, -1, -1, -1, -1, -1));
        }
    }

    @Override
    public void onHideChange(boolean isHide) {
        if (!isHide) {
            Theme.setUpStatusBar(activity, Colors.background, false);
            resetLayoutData();
        }
    }

    @Override
    public void onBackPressed() {
        activity.onBackPressed();
    }
}
