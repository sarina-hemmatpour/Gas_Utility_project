package com.example.gasutilityproject.system.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.StaticFields.Text;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.baseWindows.BaseFragment;
import com.example.gasutilityproject.system.uiTools.Custom.Button.SelectorButton;
import com.example.gasutilityproject.system.main.MainFragment;

public class LoginFragment extends BaseFragment {
    private EditText edtPersonnelCode;
    private EditText edtPassword;
    private SelectorButton btnLogin;
    private LoginController controller = new LoginController(this);

    @Override
    protected ViewGroup onViewFragmentCreate(Bundle savedInstanceState) {
        Theme.setUpStatusBar(activity, Colors.background, false);
        parent.setBackgroundColor(Colors.background);

        TextView tvTitle = new TextView(activity);
        tvTitle.setText(Text.enterLoginInfo);
        tvTitle.setId(Ids.TEXT_VIEW_ID_1);
        tvTitle.setTextSize(0, Dimen.fontSize16);
        tvTitle.setTypeface(AppLoader.mediumTypeface);
        tvTitle.setTextColor(Colors.txtGray3);
        parent.addView(tvTitle, Param.consParam(-2, -2, 0, -1, 0, -1, Dimen.m40, -1, Dimen.m40, -1));

        TextView tvPersonnelCode = new TextView(activity);
        tvPersonnelCode.setText(Text.personnelCode);
        tvPersonnelCode.setId(Ids.TEXT_VIEW_ID_3);
        tvPersonnelCode.setTextSize(0, Dimen.fontSize18);
        tvPersonnelCode.setTypeface(AppLoader.mediumTypeface);
        tvPersonnelCode.setTextColor(Colors.black2);
        parent.addView(tvPersonnelCode, Param.consParam(-2, -2, -tvTitle.getId(), -1, 0, -1, Dimen.m24, -1, Dimen.m40, -1));

        edtPersonnelCode = new EditText(activity);
        edtPersonnelCode.setId(Ids.TEXT_VIEW_ID_2);
        edtPersonnelCode.setTextSize(0, Dimen.fontSize14);
        edtPersonnelCode.setTextColor(Colors.drkLayout);
        edtPersonnelCode.setHintTextColor(Colors.gray5);//*****change later
        edtPersonnelCode.setPadding(Theme.getAf(40), Theme.getAf(35), Theme.getAf(40), Theme.getAf(35));
        edtPersonnelCode.setGravity(Gravity.CENTER);
        edtPersonnelCode.setTypeface(AppLoader.mediumTypeface);
        edtPersonnelCode.setBackground(Theme.createRoundFocusableDrawable(Colors.white, Colors.silverLess, Colors.primaryBlue, Colors.lightGray, Dimen.r32));
        edtPersonnelCode.requestFocus();
        edtPersonnelCode.addTextChangedListener(textWatcherPersonnelCode);
        Theme.showKeyboard(activity);
        parent.addView(edtPersonnelCode, Param.consParam(0, Theme.getAf(140), -tvPersonnelCode.getId(), 0, 0, -1, Theme.getAf(30), Theme.getAf(80), Theme.getAf(80), -1));

        TextView tvPassword = new TextView(activity);
        tvPassword.setText(Text.password);
        tvPassword.setId(Ids.TEXT_VIEW_ID_4);
        tvPassword.setTextSize(0, Dimen.fontSize18);
        tvPassword.setTypeface(AppLoader.mediumTypeface);
        tvPassword.setTextColor(Colors.black2);
        parent.addView(tvPassword, Param.consParam(-2, -2, -edtPersonnelCode.getId(), -1, 0, -1, Dimen.m24, -1, Dimen.m40, -1));

        edtPassword = new EditText(activity);
        edtPassword.setId(Ids.TEXT_VIEW_ID_5);
        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        edtPassword.setTextSize(0, Dimen.fontSize14);
        edtPassword.setTextColor(Colors.drkLayout);
        edtPassword.setHintTextColor(Colors.gray5);//*****change later
        edtPassword.setPadding(Theme.getAf(70), Theme.getAf(35), Theme.getAf(40), Theme.getAf(35));
        Theme.showKeyboard(activity);
        edtPassword.setGravity(Gravity.CENTER);
        edtPassword.setTypeface(AppLoader.mediumTypeface);
        edtPassword.setBackground(Theme.createRoundFocusableDrawable(Colors.white, Colors.silverLess, Colors.primaryBlue, Colors.lightGray, Dimen.r32));
//        edtPassword.requestFocus();
        edtPassword.addTextChangedListener(textWatcherPassword);
        parent.addView(edtPassword, Param.consParam(0, Theme.getAf(140), -tvPassword.getId(), 0, 0, -1, Theme.getAf(30), Theme.getAf(80), Theme.getAf(80), -1));

        btnLogin = new SelectorButton(activity);
        btnLogin.setup(Text.enter, Colors.black10Op, Colors.white, Colors.black3, Colors.primaryBlue, Colors.silverLess, onClickListener);
        btnLogin.setId(Ids.BUTTON_VIEW_ID_1);
        btnLogin.disable();
        parent.addView(btnLogin, Param.consParam(-1, Dimen.m64, -1, 0, 0, 0, -1, Dimen.m40, Dimen.m40, Dimen.m40));

        return parent;
    }

    private TextWatcher textWatcherPassword = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            unsetEditTextError();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (edtPassword.length() > 0 && edtPersonnelCode.length() > 0) {
                btnLogin.enable();
            } else if (edtPassword.length() == 0 || edtPersonnelCode.length() == 0) {
                btnLogin.disable();
            }
        }
    };
    private TextWatcher textWatcherPersonnelCode = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            unsetEditTextError();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (edtPassword.length() > 0 && edtPersonnelCode.length() > 0) {
                btnLogin.enable();
            } else if (edtPassword.length() == 0 || edtPersonnelCode.length() == 0) {
                btnLogin.disable();
            }
        }
    };

    private View.OnClickListener onClickListener = view -> {
        if (btnLogin.isEnabled()) {
            Theme.hideKeyboard(activity);
            controller.onBtnLoginClicked(edtPersonnelCode.getText().toString(), edtPassword.getText().toString());
        } else {
            Toast.makeText(activity, "اطلاعات خود را وارد کنید", Toast.LENGTH_SHORT).show();
        }
    };

    protected void login(Boolean isAdmin) {
        MainFragment.setIsAdmin(isAdmin);
        activity.pushFragment(new MainFragment(), "MainFragment");
    }

    protected void showErrorMessage(String message) {
        Toast.makeText(activity, message.trim(), Toast.LENGTH_SHORT).show();
    }

    protected void setEditTextError() {
        edtPassword.setBackground(Theme.createRoundFocusableDrawable(Colors.white, Colors.red1, Colors.primaryBlue, Colors.lightGray, Dimen.r32));
        edtPersonnelCode.setBackground(Theme.createRoundFocusableDrawable(Colors.white, Colors.red1, Colors.primaryBlue, Colors.lightGray, Dimen.r32));
    }

    protected void unsetEditTextError() {
        edtPassword.setBackground(Theme.createRoundFocusableDrawable(Colors.white, Colors.silverLess, Colors.primaryBlue, Colors.lightGray, Dimen.r32));
        edtPersonnelCode.setBackground(Theme.createRoundFocusableDrawable(Colors.white, Colors.silverLess, Colors.primaryBlue, Colors.lightGray, Dimen.r32));
    }

    protected void showProgressBar() {

    }

    protected void hideProgressBar() {

    }
}
