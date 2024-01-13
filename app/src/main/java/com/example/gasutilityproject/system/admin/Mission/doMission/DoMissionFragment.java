package com.example.gasutilityproject.system.admin.Mission.doMission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.Model.Mission;
import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.StaticFields.Utils;
import com.example.gasutilityproject.system.admin.Mission.add.PickLocationFragment2;
import com.example.gasutilityproject.system.baseWindows.BaseFragment;
import com.example.gasutilityproject.system.uiTools.Custom.Button.SelectorButton;
import com.example.gasutilityproject.system.uiTools.Custom.Toolbar;
import com.example.gasutilityproject.system.uiTools.ImageHelper;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.ShapeAppearanceModel;

public class DoMissionFragment extends BaseFragment {
    private Mission missionModel;
    private SelectorButton btnSend;
    private DoMissionController controller = new DoMissionController(this);
    private String photoBase64;
    private LinearLayout formLayout;
    private static final int EDIT_TEXT_ID = 5515;
    private static final int BASE_ID = 15;
    private static final int ERROR_ID = 1514;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 1001;
    private ShapeableImageView imageView;
    private boolean isEnable = true;

    private double latitude = -1, longitude = -1;


    public void setMissionModel(Mission missionModel) {
        this.missionModel = missionModel;
    }

    @Override
    protected ViewGroup onViewFragmentCreate(Bundle savedInstanceState) {
        parent.setBackgroundColor(Colors.background);

        Theme.setUpStatusBar(activity, Colors.drkLayout, true);
        Toolbar toolbar = new Toolbar(activity, "انجام ماموریت", Colors.coloredToolbarText, Colors.drkLayout, Colors.black3);
        toolbar.setId(Ids.VIEW_ID_1);
        parent.addView(toolbar, Param.consParam(0, -2, 0, 0, 0, -1));

        btnSend = new SelectorButton(activity);
        btnSend.setup("ارسال", Colors.black10Op, Colors.white, Colors.black3, Colors.primaryBlue, Colors.silverLess, onClickListener);
        btnSend.setId(Ids.BUTTON_VIEW_ID_1);
        btnSend.enable();
        parent.addView(btnSend, Param.consParam(-1, Dimen.m64, -1, 0, 0, 0, -1, Dimen.m40, Dimen.m40, Dimen.m40));

        ScrollView scrollView = new ScrollView(activity);
        parent.addView(scrollView, Param.consParam(-1, 0, -toolbar.getId(), 0, 0, -btnSend.getId(), -1, -1, -1, Dimen.m40));

        formLayout = new LinearLayout(activity);
        formLayout.setOrientation(LinearLayout.VERTICAL);
        formLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        formLayout.setGravity(Gravity.CENTER);
        scrollView.addView(formLayout);

        ConstraintLayout fieldReport = createFormItem(BASE_ID + 1, "گزارش:", Theme.getAf(300), "");
        formLayout.addView(fieldReport, Param.linearParam(-1, -2, -1, -1, -1, -1));

        ConstraintLayout fieldPickLocation = createAdderField(BASE_ID + 2, "ثبت لوکیشن:", v -> {
            PickLocationFragment2 pickLocationFragment = new PickLocationFragment2();
            pickLocationFragment.setListener(onLocationPickerListener);
            pickLocationFragment.setUserType(PickLocationFragment2.UserType.TECHNICIAN);
            if ((latitude != -1 && longitude != -1)) {
                pickLocationFragment.setDefaultLngLat(latitude, longitude, true);
            }
            activity.pushFragment(pickLocationFragment, null);
        }, R.drawable.ic_map_marker);
        formLayout.addView(fieldPickLocation, Param.linearParam(-1, -2, -1, -1, -1, -1));

        ConstraintLayout fieldTakePhoto = createAdderField(BASE_ID + 3, "ثبت عکس:", onTakePhotoClicked, R.drawable.ic_photo);
        formLayout.addView(fieldTakePhoto, Param.linearParam(-1, -2, -1, -1, -1, -1));

        imageView = new ShapeableImageView(activity);
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
        formLayout.addView(imageView, Param.linearParam(-1, -2, Gravity.CENTER, Dimen.m24, Dimen.m40, Dimen.m40, -1));

        return parent;
    }

    private PickLocationFragment2.OnLocationPickerListener onLocationPickerListener = (latitude, longitude) -> {
        TextView tvLocation = ((TextView) ((formLayout.findViewById(2 + BASE_ID)).findViewById(Ids.BUTTON_VIEW_ID_2)));
        tvLocation.setTag("t");
        tvLocation.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_map_marker, 0, 0);
        tvLocation.setPadding(0, Theme.getAf(40), 0, Theme.getAf(20));
        this.latitude = latitude;
        this.longitude = longitude;
    };

    private ConstraintLayout createAdderField(int id, String title, View.OnClickListener listener, int icon) {
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
        if (isEnable) {
            btnAddTechnician.setTag("f");
            btnAddTechnician.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_add, 0, 0);
            btnAddTechnician.setPadding(0, Theme.getAf(40), 0, Theme.getAf(20));
        } else {
            btnAddTechnician.setTag("t");
            btnAddTechnician.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
            btnAddTechnician.setPadding(0, Theme.getAf(40), 0, Theme.getAf(20));
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

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);

            TextView textView = ((TextView) ((formLayout.findViewById(3 + BASE_ID)).findViewById(Ids.BUTTON_VIEW_ID_2)));
            textView.setTag("t");
            textView.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_photo, 0, 0);
            photoBase64 = ImageHelper.convertUriToBase64(ImageHelper.convertBitmapToFile(activity, imageBitmap));
        }
    }

    private View.OnClickListener onClickListener = v -> {
        if (v.getId() == btnSend.getId()) {
            if (isFormFilled()) {
                if (validation()) {
                    controller.doMission(missionModel, getEditTextsTexts());
                    activity.popFragment(true);
                }
            } else {
                Toast.makeText(activity, "لطفا فرم را تکمیل کنید", Toast.LENGTH_SHORT).show();
            }

        }
    };
    private View.OnClickListener onTakePhotoClicked = v -> {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA_PERMISSION);
        } else {
            dispatchTakePictureIntent();
        }
    };

    //todo uncomment
    private boolean isFormFilled() {
        if (((EditText) ((formLayout.findViewById(1 + BASE_ID)).findViewById(EDIT_TEXT_ID))).getText().toString().length() == 0)
            return false;
        if (((TextView) ((formLayout.findViewById(2 + BASE_ID)).findViewById(Ids.BUTTON_VIEW_ID_2))).getTag() == "f")
            return false;
        return ((TextView) ((formLayout.findViewById(3 + BASE_ID)).findViewById(Ids.BUTTON_VIEW_ID_2))).getTag() != "f";
    }

    private String[] getEditTextsTexts() {
        String[] texts = new String[3];
        texts[0] = ((EditText) ((formLayout.findViewById(1 + BASE_ID)).findViewById(EDIT_TEXT_ID))).getText().toString().trim();
        texts[0] = Utils.convertToEnglishNumbers(texts[0]);
        texts[1] = "" + latitude + ":" + longitude;
        texts[2] = photoBase64;
        return texts;
    }

    private void disable() {
        //todo fix
        for (int i = 1; i <= 3; i++) {
            ((EditText) ((formLayout.findViewById(i + BASE_ID)).findViewById(EDIT_TEXT_ID))).setEnabled(false);
        }

        ((TextView) ((formLayout.findViewById(4 + BASE_ID)).findViewById(Ids.BUTTON_VIEW_ID_2))).setEnabled(false);
        ((TextView) ((formLayout.findViewById(5 + BASE_ID)).findViewById(Ids.BUTTON_VIEW_ID_2))).setEnabled(false);
        btnSend.setVisibility(View.GONE);
    }

    private boolean validation() {
        boolean isValid = true;
        String text = ((EditText) ((formLayout.findViewById(1 + BASE_ID)).findViewById(EDIT_TEXT_ID))).getText().toString();
        if (!Utils.containsNonDigits(text)) {
            setError(1, "لطفا مقدار صحیح را وارد کنید");
            isValid = false;
        } else {
            unsetError(1);
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
}
