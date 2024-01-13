package com.example.gasutilityproject.system.admin.Mission.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.DataBase.Remote.EmitManager;
import com.example.gasutilityproject.Data.Model.Mission;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.StaticFields.Utils;
import com.example.gasutilityproject.system.uiTools.Custom.Button.SelectorButton;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class ExtraTimeRequestDialog extends BottomSheetDialogFragment {
    private Context context;
    private ConstraintLayout parent;
    private SelectorButton btnSend;

    private static final int EDIT_TEXT_ID = 5515;
    private static final int BASE_ID = 15;
    private static final int ERROR_ID = 1514;

    private Mission missionModel;
    private OnRequestListener listener;

    public void setup(Mission missionModel, OnRequestListener listener) {
        this.missionModel = missionModel;
        this.listener=listener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = getContext();
        if (this.context == null) {
            return null;
        }
        parent = new ConstraintLayout(context);
        parent.setBackground(Theme.createRoundDrawable(Dimen.r16, 0, Colors.background));

        btnSend = new SelectorButton(context);
        btnSend.setup("ارسال", Colors.black10Op, Colors.white, Colors.black3, Colors.primaryBlue, Colors.silverLess, onClickListener);
        btnSend.setId(Ids.BUTTON_VIEW_ID_1);
        btnSend.enable();
        parent.addView(btnSend, Param.consParam(-1, Dimen.m64, -1, 0, 0, 0, -1, Dimen.m40, Dimen.m40, Dimen.m64));

        ConstraintLayout fieldTime = createFormItem(BASE_ID + 1, "زمان درخواستی خود را وارد کنید.", -1, "");
        parent.addView(fieldTime, Param.consParam(-1, -2, 0, 0, 0, -btnSend.getId(), -1, -1, -1, Dimen.m40));

        Theme.setThemeNavigationBar(context, getDialog().getWindow());
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return parent;
    }

    private ConstraintLayout createFormItem(int id, String title, int editTextHeight, String edtText) {
        int height = editTextHeight > 0 ? editTextHeight : Theme.getAf(140);

        ConstraintLayout parent = new ConstraintLayout(context);
        parent.setId(id);

        TextView tvTitle = new TextView(context);
        tvTitle.setText(title);
        tvTitle.setId(Ids.TEXT_VIEW_ID_1);
        tvTitle.setTextSize(0, Dimen.fontSize18);
        tvTitle.setTypeface(AppLoader.mediumTypeface);
        tvTitle.setTextColor(Colors.black2);
        parent.addView(tvTitle, Param.consParam(-2, -2, 0, -1, 0, -1, Dimen.m24, -1, Dimen.m40, -1));

        EditText editText = new EditText(context);
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

        TextView tvError = new TextView(context);
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
                    extraTimeRequest(getTime());
                    listener.requestSent();
                    dismiss();
                }
            } else {
                Toast.makeText(context, "لطفا فرم را تکمیل کنید", Toast.LENGTH_SHORT).show();
            }

        }
    };

    public void extraTimeRequest(int time) {
        JSONObject jsonObject = missionModel.toJSON();
        try {
            jsonObject.put("extraTimeRequestInDays", time);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        EmitManager.getInstance().emitAddMission(context, null, jsonObject, false);
    }

    private boolean validation() {
        boolean isValid = true;
        String text = ((EditText) ((parent.findViewById(1 + BASE_ID)).findViewById(EDIT_TEXT_ID))).getText().toString();
        if (Utils.containsNonDigits(text) || text.length() > 3) {
            setError("لطفا مقدار صحیح را وارد کنید");
            isValid = false;
        } else if (Integer.parseInt(text) <= 0) {
            setError("لطفا مقدار صحیح را وارد کنید");
            isValid = false;
        } else {
            unsetError();
        }
        return isValid;
    }

    private boolean isFormFilled() {
        return ((EditText) ((parent.findViewById(1 + BASE_ID)).findViewById(EDIT_TEXT_ID))).length() > 0;
    }

    private int getTime() {
        return Integer.parseInt(Utils.convertToEnglishNumbers(((EditText) ((parent.findViewById(1 + BASE_ID)).findViewById(EDIT_TEXT_ID))).getText().toString().trim()));
    }

    private void setError(String errorMsg) {
        ((TextView) ((parent.findViewById(1 + BASE_ID)).findViewById(ERROR_ID))).setText(errorMsg);
        ((TextView) ((parent.findViewById(1 + BASE_ID)).findViewById(ERROR_ID))).setVisibility(View.VISIBLE);
    }

    private void unsetError() {
        ((TextView) ((parent.findViewById(1 + BASE_ID)).findViewById(ERROR_ID))).setText("");
        ((TextView) ((parent.findViewById(1 + BASE_ID)).findViewById(ERROR_ID))).setVisibility(View.GONE);
    }

    public interface OnRequestListener{
        void requestSent();
    }
}
