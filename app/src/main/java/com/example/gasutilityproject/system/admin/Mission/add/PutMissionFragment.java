package com.example.gasutilityproject.system.admin.Mission.add;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.Account;
import com.example.gasutilityproject.Data.DataBase.Remote.Query.EmployeeQuery;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.Data.Model.Mission;
import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.StaticFields.Text;
import com.example.gasutilityproject.StaticFields.Utils;
import com.example.gasutilityproject.system.uiTools.ImageHelper;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.baseWindows.BaseFragment;
import com.example.gasutilityproject.system.uiTools.Custom.Button.SelectorButton;
import com.example.gasutilityproject.system.uiTools.Custom.Toolbar;
import com.example.gasutilityproject.system.admin.Mission.dialog.SelectTechnicianDialog;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

public class PutMissionFragment extends BaseFragment {
    private SelectorButton btnAdd;
    private PutMissionController controller = new PutMissionController(this);
    private Mission missionModel;
    private static final int BASE_ID = 15;
    private LinearLayout formLayout;
    private static final int EDIT_TEXT_ID = 5515;
    private Employee selectedTechnician;
    private boolean isEnabled = true;
    private static final int ERROR_ID = 1514;
    private double latitude = -1, longitude = -1;

    public void setMissionModel(Mission missionModel) {
        this.missionModel = missionModel;
        this.selectedTechnician = EmployeeQuery.getInstance(activity).getTechnician(missionModel.getTechnicianId());
        this.latitude = Double.parseDouble(missionModel.getLocation().split(":")[0].trim());
        this.longitude = Double.parseDouble(missionModel.getLocation().split(":")[1].trim());
    }

    public void disableEdit() {
        isEnabled = false;
    }

    @Override
    protected ViewGroup onViewFragmentCreate(Bundle savedInstanceState) {
        parent.setBackgroundColor(Colors.background);

        Theme.setUpStatusBar(activity, Colors.drkLayout, true);
        Toolbar toolbar = new Toolbar(activity, isEnabled ? (missionModel == null ? Text.addNewMission : Text.edit) : "نمایش", Colors.coloredToolbarText, Colors.drkLayout, Colors.black3);
        toolbar.setId(Ids.VIEW_ID_1);
        parent.addView(toolbar, Param.consParam(0, -2, 0, 0, 0, -1));

        ScrollView scrollView = new ScrollView(activity);
        parent.addView(scrollView, Param.consParam(-1, 0, -toolbar.getId(), 0, 0, 0, -1, -1, -1, Dimen.m40));

        formLayout = new LinearLayout(activity);
        formLayout.setOrientation(LinearLayout.VERTICAL);
        formLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        formLayout.setGravity(Gravity.CENTER);
        scrollView.addView(formLayout);

        ConstraintLayout fieldTitle = createFormItem(BASE_ID + 1, "موضوع:", -1, missionModel == null ? "" : missionModel.getTitle());
        formLayout.addView(fieldTitle, Param.linearParam(-1, -2, -1, -1, -1, -1));

        ConstraintLayout fieldDescription = createFormItem(BASE_ID + 2, "نوضیحات:", Theme.getAf(300), missionModel == null ? "" : missionModel.getDescription());
        formLayout.addView(fieldDescription, Param.linearParam(-1, -2, -1, -1, -1, -1));

        ConstraintLayout fieldDuration = createDurationField(BASE_ID + 3, "مهلت انجام:", missionModel == null ? "" : String.valueOf(missionModel.getDeadlineInDays()));
        formLayout.addView(fieldDuration, Param.linearParam(-1, -2, -1, -1, -1, -1));

        ConstraintLayout fieldAddTechnician = createAddTechnicianField(BASE_ID + 4, "تکنسین:", onAddTechnicianClicked, missionModel == null ? "" : selectedTechnician.getFirstname() + " " + selectedTechnician.getLastname());
        formLayout.addView(fieldAddTechnician, Param.linearParam(-1, -2, -1, -1, -1, -1));

        ConstraintLayout fieldPickLocation = createAddTechnicianField(BASE_ID + 5, "لوکیشن:", v -> {
            PickLocationFragment2 pickLocationFragment = new PickLocationFragment2();
            pickLocationFragment.setListener(onLocationPickerListener);
            pickLocationFragment.setUserType(Account.getInstance().isIsAdmin() ?
                    PickLocationFragment2.UserType.MANAGER
                    : PickLocationFragment2.UserType.TECHNICIAN);
            if (missionModel != null || (latitude != -1 && longitude != -1)) {
                pickLocationFragment.setDefaultLngLat(latitude, longitude, isEnabled);
            }
            activity.pushFragment(pickLocationFragment, null);
        }, "");
        formLayout.addView(fieldPickLocation, Param.linearParam(-1, -2, -1, -1, -1, -1));

        if (missionModel != null) {
            if (missionModel.getTechnicianStatus() == 1) {
                TextView tvDenyReason = new TextView(activity);
                tvDenyReason.setText("گزارش: \n" + missionModel.getTechnicianReport());
                tvDenyReason.setId(Ids.TEXT_VIEW_ID_1);
                tvDenyReason.setTextSize(0, Dimen.fontSize14);
                tvDenyReason.setTypeface(AppLoader.mediumTypeface);
                tvDenyReason.setTextColor(Colors.black2);
                formLayout.addView(tvDenyReason, Param.linearParam(-1, -2, Dimen.m24, Dimen.m40, Dimen.m40, -1));

                ShapeableImageView imageView = new ShapeableImageView(activity);
                imageView.setId(Ids.IMAGE_VIEW_ID_4);
                ShapeAppearanceModel shapeAppearanceModel = new ShapeAppearanceModel()
                        .toBuilder()
                        .setTopLeftCorner(CornerFamily.ROUNDED, Dimen.r12)
                        .setTopRightCorner(CornerFamily.ROUNDED, Dimen.r12)
                        .setBottomLeftCorner(CornerFamily.ROUNDED, Dimen.r12)
                        .setBottomRightCorner(CornerFamily.ROUNDED, Dimen.r12)
                        .build();
                imageView.setShapeAppearanceModel(shapeAppearanceModel);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageView.setAdjustViewBounds(true);
                ImageHelper.loadBase64ToImageView(imageView, missionModel.getTechnicianReportPhoto());
                formLayout.addView(imageView, Param.linearParam(-1, -2, Gravity.CENTER, Dimen.m24, Dimen.m40, Dimen.m40, -1));
            } else if (missionModel.getTechnicianStatus() == 2) {
                TextView tvDenyReason = new TextView(activity);
                tvDenyReason.setText("دلیل عدم انجام ماموریت: \n" + missionModel.getTechnicianFailedMissionReason());
                tvDenyReason.setId(Ids.TEXT_VIEW_ID_1);
                tvDenyReason.setTextSize(0, Dimen.fontSize14);
                tvDenyReason.setTypeface(AppLoader.mediumTypeface);
                tvDenyReason.setTextColor(Colors.black2);
                formLayout.addView(tvDenyReason, Param.linearParam(-1, -2, Dimen.m24, Dimen.m40, Dimen.m40, -1));
            }
        }
        btnAdd = new SelectorButton(activity);
        btnAdd.setup(Text.submit, Colors.black10Op, Colors.white, Colors.black3, Colors.primaryBlue, Colors.silverLess, onClickListener);
        btnAdd.setId(Ids.BUTTON_VIEW_ID_1);
        btnAdd.enable();
        formLayout.addView(btnAdd, Param.linearParam(-1, Dimen.m64, Dimen.m40, Dimen.m40, Dimen.m40, Dimen.m40));


        if (!isEnabled)
            disable();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        return parent;
    }

    private ConstraintLayout createDurationField(int id, String title, String edtText) {

        ConstraintLayout parent = new ConstraintLayout(activity);
        parent.setId(id);

        TextView tvTitle = new TextView(activity);
        tvTitle.setText(title);
        tvTitle.setId(Ids.TEXT_VIEW_ID_1);
        tvTitle.setTextSize(0, Dimen.fontSize18);
        tvTitle.setTypeface(AppLoader.mediumTypeface);
        tvTitle.setTextColor(Colors.black2);
        parent.addView(tvTitle, Param.consParam(-2, -2, 0, -1, 0, 0, Dimen.m24, -1, Dimen.m40, -1));

        TextView tvDay = new TextView(activity);
        tvDay.setText("روز");
        tvDay.setId(Ids.TEXT_VIEW_ID_2);
        tvDay.setTextSize(0, Dimen.fontSize14);
        tvDay.setTypeface(AppLoader.mediumTypeface);
        tvDay.setTextColor(Colors.black2);
        parent.addView(tvDay, Param.consParam(-2, -2, 0, 0, -1, 0, Dimen.m24, Dimen.m40, -1, -1));

        EditText editText = new EditText(activity);
        editText.setTextSize(0, Dimen.fontSize14);
        editText.setTextColor(Colors.drkLayout);
        editText.setText(edtText);
        editText.setHintTextColor(Colors.gray5);//*****change later
        editText.setPadding(Theme.getAf(70), Theme.getAf(35), Theme.getAf(40), Theme.getAf(35));
        editText.setId(EDIT_TEXT_ID);
        editText.setGravity(Gravity.CENTER);
        editText.setTypeface(AppLoader.mediumTypeface);
        editText.setBackground(Theme.createRoundFocusableDrawable(Colors.white, Colors.silverLess, Colors.primaryBlue, Colors.lightGray, Dimen.r32));
        parent.addView(editText, Param.consParam(0, Theme.getAf(140), tvDay.getId(), -tvDay.getId(), -tvTitle.getId(), tvDay.getId(), -1, Dimen.m16, Dimen.m16, -1));

        TextView tvError = new TextView(activity);
        tvError.setId(ERROR_ID);
        tvError.setTextSize(0, Dimen.fontSize12);
        tvError.setTypeface(AppLoader.mediumTypeface);
        tvError.setTextColor(Colors.red600);
        parent.addView(tvError, Param.consParam(-2, -2, -EDIT_TEXT_ID, EDIT_TEXT_ID, -1, -1, Theme.getAf(10), -1, -1, -1));
        tvError.setVisibility(View.GONE);

        return parent;
    }


    private ConstraintLayout createAddTechnicianField(int id, String title, View.OnClickListener listener, String tvText) {
        ConstraintLayout parent = new ConstraintLayout(activity);
        parent.setId(id);

        TextView tvTitle = new TextView(activity);
        tvTitle.setText(title);
        tvTitle.setId(Ids.TEXT_VIEW_ID_1);
        tvTitle.setTextSize(0, Dimen.fontSize18);
        tvTitle.setTypeface(AppLoader.mediumTypeface);
        tvTitle.setTextColor(Colors.black2);
        parent.addView(tvTitle, Param.consParam(-2, -2, 0, -1, 0, -1, Dimen.m24, -1, Dimen.m40, -1));

        TextView btnAddTechnician = new TextView(activity);
        btnAddTechnician.setBackground(Theme.createRoundSelectorDrawable(Colors.black10Op, Colors.silverLess, Theme.getAf(300), Theme.getAf(300)));
        if (tvText.equals("")) {
            if (missionModel == null) {
                btnAddTechnician.setTag("f");
                btnAddTechnician.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_add, 0, 0);
                btnAddTechnician.setPadding(0, Theme.getAf(40), 0, Theme.getAf(20));
            } else {
                btnAddTechnician.setTag("t");
                btnAddTechnician.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_map_marker, 0, 0);
                btnAddTechnician.setPadding(0, Theme.getAf(40), 0, Theme.getAf(20));
                this.latitude = Double.parseDouble(missionModel.getLocation().split(":")[0].trim());
                this.longitude = Double.parseDouble(missionModel.getLocation().split(":")[1].trim());
            }

        } else {
            btnAddTechnician.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            btnAddTechnician.setPadding(0, Theme.getAf(20), 0, Theme.getAf(20));
            btnAddTechnician.setText(tvText);
        }
        btnAddTechnician.setId(Ids.BUTTON_VIEW_ID_2);
        btnAddTechnician.setGravity(Gravity.CENTER);
        btnAddTechnician.setOnClickListener(listener);
        parent.addView(btnAddTechnician, Param.consParam(-1, Dimen.m64, -tvTitle.getId(), 0, 0, -1, Dimen.m16, Dimen.m40, Dimen.m40, -1));

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

    private View.OnClickListener onAddTechnicianClicked = v -> {
        FragmentManager fragmentManager = activity.getFM();
        if (fragmentManager != null) {
            SelectTechnicianDialog selectTechnicianDialog = new SelectTechnicianDialog();
            selectTechnicianDialog.setUp(controller.provideTechniciansList(), employee -> {
                TextView textView = ((formLayout.findViewById(BASE_ID + 4)).findViewById(Ids.BUTTON_VIEW_ID_2));
                textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                textView.setPadding(0, Theme.getAf(20), 0, Theme.getAf(20));
                textView.setText(employee.getFirstname() + " " + employee.getLastname());
                selectedTechnician = employee;
            });
            selectTechnicianDialog.show(fragmentManager, null);
        }
    };
    private View.OnClickListener onClickListener = v -> {
        if (v.getId() == btnAdd.getId()) {
            if (isFormFilled()) {
                if (validation()) {
                    controller.addNewMission(missionModel, getEditTextsTexts());
                    activity.popFragment(true);
                }
            } else {
                Toast.makeText(activity, "لطفا فرم را تکمیل کنید", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private PickLocationFragment2.OnLocationPickerListener onLocationPickerListener = (latitude, longitude) -> {
        TextView tvLocation = ((TextView) ((formLayout.findViewById(5 + BASE_ID)).findViewById(Ids.BUTTON_VIEW_ID_2)));
        tvLocation.setTag("t");
        tvLocation.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_map_marker, 0, 0);
        tvLocation.setPadding(0, Theme.getAf(40), 0, Theme.getAf(20));
        this.latitude = latitude;
        this.longitude = longitude;
    };

    private boolean isFormFilled() {
        if (((TextView) ((formLayout.findViewById(4 + BASE_ID)).findViewById(Ids.BUTTON_VIEW_ID_2))).length() == 0)
            return false;
        if (((TextView) ((formLayout.findViewById(5 + BASE_ID)).findViewById(Ids.BUTTON_VIEW_ID_2))).getTag() == "f")
            return false;
        for (int i = 1; i <= 3; i++) {
            if (((EditText) ((formLayout.findViewById(i + BASE_ID)).findViewById(EDIT_TEXT_ID))).length() == 0)
                return false;
        }
        return true;
    }

    private String[] getEditTextsTexts() {
        String[] texts = new String[5];
        for (int i = 0; i < 3; i++) {
            texts[i] = ((EditText) ((formLayout.findViewById(i + 1 + BASE_ID)).findViewById(EDIT_TEXT_ID))).getText().toString().trim();
            texts[i] = Utils.convertToEnglishNumbers(texts[i]);
        }
        texts[3] = "" + selectedTechnician.getId();
        texts[4] = "" + latitude + ":" + longitude;
        return texts;
    }

    private void disable() {
        for (int i = 1; i <= 3; i++) {
            ((EditText) ((formLayout.findViewById(i + BASE_ID)).findViewById(EDIT_TEXT_ID))).setEnabled(false);
        }

        ((TextView) ((formLayout.findViewById(4 + BASE_ID)).findViewById(Ids.BUTTON_VIEW_ID_2))).setEnabled(false);
//        ((TextView) ((formLayout.findViewById(5 + BASE_ID)).findViewById(Ids.BUTTON_VIEW_ID_2))).setEnabled(false);
        btnAdd.setVisibility(View.GONE);
    }

    private boolean validation() {
        boolean isValid = true;
        for (int i = 1; i <= 3; i++) {
            String text = ((EditText) ((formLayout.findViewById(i + BASE_ID)).findViewById(EDIT_TEXT_ID))).getText().toString();
            switch (i) {
                case 1:
                case 2:
                    if (!Utils.containsNonDigits(text)) {
                        setError(i, "لطفا مقدار صحیح را وارد کنید");
                        isValid = false;
                    } else {
                        unsetError(i);
                    }
                    break;
                case 3:
                    if (Utils.containsNonDigits(text) || text.length() > 3) {
                        setError(i, "لطفا مقدار صحیح را وارد کنید");
                        isValid = false;
                    } else if (Integer.parseInt(text) <= 0) {
                        setError(i, "لطفا مقدار صحیح را وارد کنید");
                        isValid = false;
                    } else
                        unsetError(i);
            }
        }
        return isValid;
    }

    private void setError(int index, String errorMsg) {
        ((TextView) ((formLayout.findViewById(index + BASE_ID)).findViewById(ERROR_ID))).setText(errorMsg);
        ((TextView) ((formLayout.findViewById(index + BASE_ID)).findViewById(ERROR_ID))).setVisibility(View.VISIBLE);
    }

    private void unsetError(int index) {
        ((TextView) ((formLayout.findViewById(index + BASE_ID)).findViewById(ERROR_ID))).setText("");
        ((TextView) ((formLayout.findViewById(index + BASE_ID)).findViewById(ERROR_ID))).setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        activity.onBackPressed();
    }
}
