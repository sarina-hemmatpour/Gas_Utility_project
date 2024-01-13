package com.example.gasutilityproject.system.admin.Mission.denyMission;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.Model.Mission;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.StaticFields.Utils;
import com.example.gasutilityproject.system.baseWindows.BaseFragment;
import com.example.gasutilityproject.system.uiTools.Custom.Button.SelectorButton;
import com.example.gasutilityproject.system.uiTools.Custom.Toolbar;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;

public class DenyMissionFragment extends BaseFragment {
    private Mission missionModel;
    private DenyMissionController controller = new DenyMissionController(this);
    private SelectorButton btnSend;
    private RadioGroup radioGroupReasons;
    private ConstraintLayout fieldCustomReason;
    private RadioButton rbReasonThree;
    private static final int EDIT_TEXT_ID = 5515;
    private static final int BASE_ID = 15;
    private static final int ERROR_ID = 1514;


    public void setMissionModel(Mission missionModel) {
        this.missionModel = missionModel;
    }

    @Override
    protected ViewGroup onViewFragmentCreate(Bundle savedInstanceState) {
        parent.setBackgroundColor(Colors.background);

        Theme.setUpStatusBar(activity, Colors.drkLayout, true);
        Toolbar toolbar = new Toolbar(activity, "عدم انجام ماموریت", Colors.coloredToolbarText, Colors.drkLayout, Colors.black3);
        toolbar.setId(Ids.VIEW_ID_1);
        parent.addView(toolbar, Param.consParam(0, -2, 0, 0, 0, -1));

        btnSend = new SelectorButton(activity);
        btnSend.setup("ارسال", Colors.black10Op, Colors.white, Colors.black3, Colors.primaryBlue, Colors.silverLess, onClickListener);
        btnSend.setId(Ids.BUTTON_VIEW_ID_1);
        btnSend.enable();
        parent.addView(btnSend, Param.consParam(-1, Dimen.m64, -1, 0, 0, 0, -1, Dimen.m40, Dimen.m40, Dimen.m40));

        TextView tvTitle = new TextView(activity);
        tvTitle.setText("دلیل عدم انجام ماموریت:");
        tvTitle.setId(Ids.TEXT_VIEW_ID_1);
        tvTitle.setTextSize(0, Dimen.fontSize18);
        tvTitle.setTypeface(AppLoader.mediumTypeface, Typeface.BOLD);
        tvTitle.setTextColor(Colors.black2);
        parent.addView(tvTitle, Param.consParam(-2, -2, -toolbar.getId(), -1, 0, -1, Dimen.m24, -1, Dimen.m40, -1));


        radioGroupReasons = new RadioGroup(activity);
        radioGroupReasons.setId(54541);
        radioGroupReasons.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        parent.addView(radioGroupReasons, Param.consParam(-2, -2, -tvTitle.getId(), -1, 0, -1, Dimen.m16, -1, Dimen.m40, -1));

        RadioButton rbReasonOne = new RadioButton(activity);
        rbReasonOne.setText("کمبود بودجه");
        rbReasonOne.setId(15);
        rbReasonOne.setTypeface(AppLoader.mediumTypeface);
        rbReasonOne.setTextSize(0, Dimen.fontSize16);
        rbReasonOne.setOnClickListener(v -> {
            fieldCustomReason.setVisibility(View.GONE);
        });
        radioGroupReasons.addView(rbReasonOne, 0, Param.linearParam(-2, -2, Gravity.RIGHT, -1, -1, -1, -1));

        RadioButton rbReasonTwo = new RadioButton(activity);
        rbReasonTwo.setText("کمبود تجهیزات");
        rbReasonTwo.setTypeface(AppLoader.mediumTypeface);
        rbReasonTwo.setId(16);
        rbReasonTwo.setOnClickListener(v -> {
            fieldCustomReason.setVisibility(View.GONE);
        });
        rbReasonTwo.setTextSize(0, Dimen.fontSize16);
        radioGroupReasons.addView(rbReasonTwo, 1, Param.linearParam(-2, -2, Gravity.RIGHT, Dimen.m8, -1, -1, -1));

        rbReasonThree = new RadioButton(activity);
        rbReasonThree.setText("سایر");
        rbReasonThree.setTypeface(AppLoader.mediumTypeface);
        rbReasonThree.setId(17);
        rbReasonThree.setTextSize(0, Dimen.fontSize16);
        rbReasonThree.setOnClickListener(v -> {
            fieldCustomReason.setVisibility(View.VISIBLE);
        });
        radioGroupReasons.addView(rbReasonThree, 2, Param.linearParam(-2, -2, Gravity.RIGHT, Dimen.m8, -1, -1, -1));

        radioGroupReasons.check(rbReasonOne.getId());

        fieldCustomReason = createFormItem(Ids.PARENT_VIEW_ID_1, "دلیل خود را وارد کنید.", Theme.getAf(300), "");
        parent.addView(fieldCustomReason, Param.consParam(-1, -2, -radioGroupReasons.getId(), 0, 0, -1));
        fieldCustomReason.setVisibility(View.GONE);
        return parent;
    }

    private ConstraintLayout createFormItem(int id, String title, int editTextHeight, String edtText) {
        int height = editTextHeight > 0 ? editTextHeight : Theme.getAf(140);

        ConstraintLayout parent = new ConstraintLayout(activity);
        parent.setId(id);

        TextView tvTitle = new TextView(activity);
        tvTitle.setText(title);
        tvTitle.setId(Ids.TEXT_VIEW_ID_1);
        tvTitle.setTextSize(0, Dimen.fontSize18);
        tvTitle.setTypeface(AppLoader.mediumTypeface);
        tvTitle.setTextColor(Colors.black2);
        parent.addView(tvTitle, Param.consParam(-2, -2, 0, -1, 0, -1, Dimen.m24, -1, Dimen.m40, -1));

        EditText editText = new EditText(activity);
        editText.setTextSize(0, Dimen.fontSize14);
        editText.setTextColor(Colors.drkLayout);
        editText.setText(edtText);
        editText.setHintTextColor(Colors.gray5);//*****change later
        editText.setPadding(Theme.getAf(70), Theme.getAf(35), Theme.getAf(40), Theme.getAf(35));
        editText.setId(EDIT_TEXT_ID);
        editText.setGravity(Gravity.RIGHT);
        editText.setTypeface(AppLoader.mediumTypeface);
        editText.setBackground(Theme.createRoundFocusableDrawable(Colors.white, Colors.silverLess, Colors.primaryBlue, Colors.lightGray, Dimen.r32));
        parent.addView(editText, Param.consParam(0, height, -tvTitle.getId(), 0, 0, -1, Dimen.m16, Dimen.m40, Dimen.m40, -1));

        TextView tvError = new TextView(activity);
        tvError.setId(ERROR_ID);
        tvError.setTextSize(0, Dimen.fontSize12);
        tvError.setTypeface(AppLoader.mediumTypeface);
        tvError.setTextColor(Colors.red600);
        parent.addView(tvError, Param.consParam(-2, -2, -EDIT_TEXT_ID, EDIT_TEXT_ID, -1, -1, Theme.getAf(10), -1, -1, -1));
        tvError.setVisibility(View.GONE);

        return parent;
    }

    private View.OnClickListener onClickListener = v -> {
        if (v.getId() == btnSend.getId()) {
            if (isFormFilled()) {
                if (validation()) {
                    controller.denyMission(missionModel, getEditTextsTexts());
                    activity.popFragment(true);
                }
            } else {
                Toast.makeText(activity, "لطفا فرم را تکمیل کنید", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private boolean isFormFilled() {
        if (radioGroupReasons.getCheckedRadioButtonId() == 17)
            return ((EditText) ((parent.findViewById(Ids.PARENT_VIEW_ID_1)).findViewById(EDIT_TEXT_ID))).getText().toString().length() != 0;
        return true;
    }

    private boolean validation() {
        boolean isValid = true;
        if (radioGroupReasons.getCheckedRadioButtonId() == 17) {
            String text = ((EditText) ((parent.findViewById(Ids.PARENT_VIEW_ID_1)).findViewById(EDIT_TEXT_ID))).getText().toString();
            if (!Utils.containsNonDigits(text)) {
                setError("لطفا مقدار صحیح را وارد کنید");
                isValid = false;
            } else {
                unsetError();
            }
        }
        return isValid;
    }

    private String getEditTextsTexts() {
        if (radioGroupReasons.getCheckedRadioButtonId() == 17)
            return ((EditText) ((parent.findViewById(Ids.PARENT_VIEW_ID_1)).findViewById(EDIT_TEXT_ID))).getText().toString();
        if (radioGroupReasons.getCheckedRadioButtonId() == 16)
            return "کمبود تجهیزات";
        return "کمبود بودجه";
    }

    private void setError(String errorMsg) {
        ((TextView) ((parent.findViewById(Ids.PARENT_VIEW_ID_1)).findViewById(ERROR_ID))).setText(errorMsg);
        ((TextView) ((parent.findViewById(Ids.PARENT_VIEW_ID_1)).findViewById(ERROR_ID))).setVisibility(View.VISIBLE);
    }

    private void unsetError() {
        ((TextView) ((parent.findViewById(Ids.PARENT_VIEW_ID_1)).findViewById(ERROR_ID))).setText("");
        ((TextView) ((parent.findViewById(Ids.PARENT_VIEW_ID_1)).findViewById(ERROR_ID))).setVisibility(View.GONE);
    }
}
