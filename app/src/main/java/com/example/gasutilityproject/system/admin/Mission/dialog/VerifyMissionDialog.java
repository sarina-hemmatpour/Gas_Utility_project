package com.example.gasutilityproject.system.admin.Mission.dialog;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.DataBase.Remote.Query.EmployeeQuery;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.Data.Model.Mission;
import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.baseWindows.BaseActivity;
import com.example.gasutilityproject.system.uiTools.Custom.Button.ColoredButton;
import com.example.gasutilityproject.system.systemTools.MissionManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class VerifyMissionDialog extends BottomSheetDialogFragment {
    private BaseActivity activity;

    private ColoredButton btnProve;
    private ColoredButton btnDis;
    private Mission mission;
    private ImageView imgLocation;
    private TextView tvLocationStatus;
    private ConstraintLayout parent;
    private ValidateMissionListener listener;

    public void setup(Mission mission, ValidateMissionListener listener) {
        this.mission = mission;
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        activity = (BaseActivity) getActivity();
        if (activity == null)
            return null;

        setCancelable(true);

        parent = new ConstraintLayout(activity);
        parent.setBackground(Theme.createRoundDrawable(Dimen.r16, 0, Colors.white));

        TextView txtTitle = new TextView(activity);
        txtTitle.setId(Ids.TEXT_VIEW_ID_1);
        txtTitle.setTextSize(0, Dimen.fontSize16);
        txtTitle.setText("تایید ماموریت");
        txtTitle.setGravity(Gravity.CENTER);
        txtTitle.setTypeface(AppLoader.mediumTypeface, Typeface.BOLD);
        txtTitle.setTextSize(0, Dimen.fontSize20);
        txtTitle.setTextColor(Colors.txtGray3);
        parent.addView(txtTitle, Param.consParam(0, -2, 0, 0, 0, -1, Dimen.m40, -1, -1, -1));

        Employee employee = EmployeeQuery.getInstance(activity).getTechnician(mission.getTechnicianId());

        TextView txtDesc = new TextView(activity);
        txtDesc.setId(Ids.TEXT_VIEW_ID_2);
        txtDesc.setText("ماموریت: " + mission.getTitle() + "\nمامور: " + employee.getFirstname() + " " + employee.getLastname());
        txtDesc.setTextColor(Colors.gray2);
        txtDesc.setTypeface(AppLoader.mediumTypeface);
        txtDesc.setTextSize(0, Dimen.fontSize16);
        txtDesc.setGravity(Gravity.RIGHT);
        parent.addView(txtDesc, Param.consParam(0, -2, -txtTitle.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        imgLocation = new ImageView(activity);
        imgLocation.setId(Ids.IMAGE_VIEW_ID_1);
        parent.addView(imgLocation, Param.consParam(Theme.getAf(200), Theme.getAf(200), -txtDesc.getId(), 0, 0, -1, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        tvLocationStatus = new TextView(activity);
        tvLocationStatus.setId(Ids.TEXT_VIEW_ID_5);
        tvLocationStatus.setTypeface(AppLoader.mediumTypeface);
        tvLocationStatus.setTextSize(0, Dimen.fontSize14);
        tvLocationStatus.setGravity(Gravity.CENTER);
        parent.addView(tvLocationStatus, Param.consParam(0, -2, -imgLocation.getId(), 0, 0, -1, Dimen.m8, Dimen.m40, Dimen.m40, -1));

        loadImgLocation();

        btnProve = new ColoredButton(activity);
        btnProve.setId(Ids.BUTTON_VIEW_ID_2);
        btnProve.setup("تایید", Colors.white, Colors.hover, Colors.primaryBlue, Dimen.fontSize14, 0, true, onClickListener);
        parent.addView(btnProve, Param.consParam((AppLoader.widthScreen - Theme.getAf(195)) / 2, -2, -tvLocationStatus.getId(), 0, -1, 0, Theme.getAf(90), Theme.getAf(80), -1, Theme.getAf(80)));

        btnDis = new ColoredButton(activity);
        btnDis.setId(Ids.BUTTON_VIEW_ID_3);
        btnDis.setup("رد", Colors.black5, Colors.hover, Colors.background, Dimen.fontSize14, 0, true, onClickListener);
        parent.addView(btnDis, Param.consParam((AppLoader.widthScreen - Theme.getAf(195)) / 2, -2, -tvLocationStatus.getId(), -1, 0, 0, Theme.getAf(90), -1, Theme.getAf(80), Theme.getAf(80)));

        Theme.setThemeNavigationBar(activity, getDialog().getWindow());
        return parent;
    }

    private void loadImgLocation() {
        boolean isLocationValid = MissionManager.validateLocation(mission);
        if (isLocationValid) {
            imgLocation.setImageResource(R.drawable._location);
            tvLocationStatus.setText("لوکیشن توسط سیستم خودکار، تایید شد");
            tvLocationStatus.setTextColor(Colors.greenDark);

        } else {
            imgLocation.setImageResource(R.drawable.img_cross_location);
            tvLocationStatus.setText("لوکیشن توسط سیستم خودکار، تایید نشد");
            tvLocationStatus.setTextColor(Colors.red600);
        }
    }

    private View.OnClickListener onClickListener = v -> {
        if (v.getId() == btnDis.getId()) {
            listener.result(false);
        } else if (v.getId() == btnProve.getId()) {
            listener.result(true);
        }
        dismiss();
    };

    public interface ValidateMissionListener {
        void result(boolean validate);
    }
}
