package com.example.gasutilityproject.system.admin.technician.add;

import static com.example.gasutilityproject.StaticFields.Utils.containsNonDigits;
import static com.example.gasutilityproject.StaticFields.Utils.containsNumbers;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.StaticFields.Text;
import com.example.gasutilityproject.StaticFields.Utils;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.baseWindows.BaseFragment;
import com.example.gasutilityproject.system.uiTools.Custom.Button.SelectorButton;
import com.example.gasutilityproject.system.uiTools.Custom.Toolbar;

public class PutTechnicianFragment extends BaseFragment {
    private SelectorButton btnAdd;
    private PutTechnicianController controller = new PutTechnicianController(this);
    private Employee employeeModel;
    private static final int BASE_ID = 15;
    private static final int EDIT_TEXT_ID = 5515;
    private static final int ERROR_ID = 1514;
    private LinearLayout form;
    private String[] titles = new String[]{"نام:", "نام خانوادگی:", "کد ملی:", "تخصص:", "شماره تماس:", "پست الکترونیک:", "رمز عبور:"};
    private boolean isEnabled = true;
    protected PutTechnicianListener listener;

    public void setListener(PutTechnicianListener listener) {
        this.listener = listener;
    }

    public void setEmployeeModel(Employee employeeModel) {
        this.employeeModel = employeeModel;
    }

    public void disableEdit() {
        isEnabled = false;
    }

    @Override
    protected ViewGroup onViewFragmentCreate(Bundle savedInstanceState) {
        parent.setBackgroundColor(Colors.background);

        Theme.setUpStatusBar(activity, Colors.drkLayout, true);
        Toolbar toolbar = new Toolbar(activity, isEnabled ? (employeeModel == null ? Text.addNewTechnician : Text.edit) : "نمایش", Colors.coloredToolbarText, Colors.drkLayout, Colors.black3);
        toolbar.setId(Ids.VIEW_ID_1);
        parent.addView(toolbar, Param.consParam(0, -2, 0, 0, 0, -1));

        ScrollView formScrollView = new ScrollView(activity);
        parent.addView(formScrollView, Param.consParam(-1, 0, -toolbar.getId(), 0, 0, 0));

        form = new LinearLayout(activity);
        form.setOrientation(LinearLayout.VERTICAL);
        formScrollView.addView(form);

        String[] edtTexts = new String[7];
        if (employeeModel != null) {
            edtTexts = new String[]{
                    employeeModel.getFirstname(), employeeModel.getLastname(), employeeModel.getNationalIdNumber(),
                    employeeModel.getProficiency(), employeeModel.getPhoneNumber(), employeeModel.getEmailAddress(),
                    employeeModel.getPassword()
            };
        }

        for (int i = 0; i < 7; i++) {
            ConstraintLayout formItem = createFormItem(i + BASE_ID, titles[i], i == 0, employeeModel == null ? "" : edtTexts[i]);
            form.addView(formItem, Param.linearParam(-1, -2, -1, -1, -1, -1));
        }

        if (employeeModel != null) {
            ConstraintLayout formItem = createFormItem(7 + BASE_ID, "شماره پرسنلی:", false, employeeModel.getPersonnelIdNumber());
            form.addView(formItem, Param.linearParam(-1, -2, -1, -1, -1, -1));
        }

        btnAdd = new SelectorButton(activity);
        btnAdd.setup(Text.submit, Colors.black10Op, Colors.white, Colors.black3, Colors.primaryBlue, Colors.silverLess, onClickListener);
        btnAdd.setId(Ids.BUTTON_VIEW_ID_1);
        btnAdd.enable();
        form.addView(btnAdd, Param.linearParam(-1, Dimen.m64, Dimen.m40, Dimen.m40, Dimen.m40, Dimen.m40));

        if (!isEnabled)
            disable();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return parent;
    }

    private ConstraintLayout createFormItem(int id, String title, boolean focus, String edtText) {
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
        editText.setId(EDIT_TEXT_ID);
        editText.setTextColor(Colors.drkLayout);
        editText.setHintTextColor(Colors.gray5);
        if (employeeModel != null)
            editText.setText(edtText);
        editText.setPadding(Theme.getAf(70), Theme.getAf(35), Theme.getAf(40), Theme.getAf(35));
        editText.setGravity(Gravity.CENTER);
        editText.setTypeface(AppLoader.mediumTypeface);
        editText.setBackground(Theme.createRoundFocusableDrawable(Colors.white, Colors.silverLess, Colors.primaryBlue, Colors.lightGray, Dimen.r32));
        if (focus && isEnabled) {
            editText.requestFocus();
            Theme.showKeyboard(activity);
        }
        parent.addView(editText, Param.consParam(0, Theme.getAf(140), -tvTitle.getId(), 0, 0, -1, Dimen.m12, Dimen.m40, Dimen.m40, -1));

        TextView tvError = new TextView(activity);
        tvError.setId(ERROR_ID);
        tvError.setTextSize(0, Dimen.fontSize12);
        tvError.setTypeface(AppLoader.mediumTypeface);
        tvError.setTextColor(Colors.red600);
        parent.addView(tvError, Param.consParam(-2, -2, -EDIT_TEXT_ID, EDIT_TEXT_ID, -1, -1, Theme.getAf(10), -1, -1, -1));
        tvError.setVisibility(View.GONE);

        return parent;
    }

    private View.OnClickListener onClickListener = view -> {
        if (view.getId() == btnAdd.getId()) {
            if (isFormFilled()) {
                if (validation()) {
                    controller.addNewTechnician(employeeModel, getEditTextsTexts());
                    activity.popFragment(true);
                }
            } else {
                Toast.makeText(activity, "لطفا فرم را تکمیل کنید", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private String getEditTextTest(int index) {
        return ((EditText) ((form.findViewById(index + BASE_ID)).findViewById(EDIT_TEXT_ID))).getText().toString();
    }

    private void setError(int index, String errorMsg) {
        ((TextView) ((form.findViewById(index + BASE_ID)).findViewById(ERROR_ID))).setText(errorMsg);
        ((TextView) ((form.findViewById(index + BASE_ID)).findViewById(ERROR_ID))).setVisibility(View.VISIBLE);
    }

    private void unsetError(int index) {
        ((TextView) ((form.findViewById(index + BASE_ID)).findViewById(ERROR_ID))).setText("");
        ((TextView) ((form.findViewById(index + BASE_ID)).findViewById(ERROR_ID))).setVisibility(View.GONE);
    }

    private void disable() {
        for (int i = 0; i < 7; i++) {
            ((EditText) ((form.findViewById(i + BASE_ID)).findViewById(EDIT_TEXT_ID))).setEnabled(false);
        }
        btnAdd.setVisibility(View.GONE);
        parent.setPadding(0, 0, 0, Dimen.m40);
    }


    private boolean isFormFilled() {
        for (int i = 0; i < 7; i++) {
            if (((EditText) ((form.findViewById(i + BASE_ID)).findViewById(EDIT_TEXT_ID))).length() == 0)
                return false;
        }
        return true;
    }

    private String[] getEditTextsTexts() {
        String[] texts = new String[7];
        for (int i = 0; i < 7; i++) {
            texts[i] = ((EditText) ((form.findViewById(i + BASE_ID)).findViewById(EDIT_TEXT_ID))).getText().toString().trim();
            texts[i] = Utils.convertToEnglishNumbers(texts[i]);
        }
        return texts;
    }

    private boolean validation() {
        boolean isValid = true;
        for (int i = 0; i < 7; i++) {
            String text = ((EditText) ((form.findViewById(i + BASE_ID)).findViewById(EDIT_TEXT_ID))).getText().toString();
            switch (i) {
                case 0:
                case 1:
                case 3:
                    if (containsNumbers(text) || text.length() > 30) {
                        setError(i, "لطفا مقدار صحیح را وارد کنید");
                        isValid = false;
                    } else {
                        unsetError(i);
                    }
                    break;
                case 2:
                    if (text.length() != 10 || containsNonDigits(text)) {
                        setError(i, "لطفا مقدار صحیح را وارد کنید");
                        isValid = false;
                    } else {
                        unsetError(i);
                    }
                    break;
                case 4:
                    if (containsNonDigits(text) || text.length() != 11) {
                        setError(i, "لطفا مقدار صحیح را وارد کنید");
                        isValid = false;
                    } else {
                        unsetError(i);
                    }
                    break;
                case 5:
                    if (!Utils.isValidEmail(text.trim())) {
                        setError(i, "لطفا مقدار صحیح را وارد کنید");
                        isValid = false;
                    } else {
                        unsetError(i);
                    }
                    break;
            }
        }
        return isValid;
    }

    public interface PutTechnicianListener {
        void onAddBtnClicked(Employee employee);
    }

    @Override
    public void onBackPressed() {
        activity.onBackPressed();
    }
}
