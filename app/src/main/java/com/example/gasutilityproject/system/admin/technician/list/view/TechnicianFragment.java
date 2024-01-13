package com.example.gasutilityproject.system.admin.technician.list.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.baseWindows.BaseFragment;
import com.example.gasutilityproject.system.admin.technician.add.PutTechnicianFragment;
import com.example.gasutilityproject.system.admin.technician.list.controller.TechnicianController;

public class TechnicianFragment extends BaseFragment {
    private TechnicianController controller = new TechnicianController(this);
    private TechnicianAdapter technicianAdapter;
    private ConstraintLayout emptyStateParent;
    public RecyclerView technicianRv;

    @Override
    protected ViewGroup onViewFragmentCreate(Bundle savedInstanceState) {
        parent.setBackgroundColor(Colors.background);

        EditText edtSearch = new EditText(activity);
        edtSearch.setTextSize(0, Dimen.fontSize14);
        edtSearch.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_search,0);
        edtSearch.setCompoundDrawablePadding(Theme.getAf(20));
        edtSearch.setTextColor(Colors.darkGray);
        edtSearch.setHint("جست و جو");
        edtSearch.setHintTextColor(Colors.darkGray);
        edtSearch.setPadding(Theme.getAf(70), Theme.getAf(35), Theme.getAf(40), Theme.getAf(35));
        edtSearch.setId(5844);
        edtSearch.setGravity(Gravity.RIGHT);
        edtSearch.setTypeface(AppLoader.mediumTypeface);
        edtSearch.setBackground(Theme.createRoundFocusableDrawable(Colors.silverLess, Colors.silverLess, Colors.lightGray, Colors.lightGray, Dimen.r32));
        parent.addView(edtSearch, Param.consParam(0, Theme.getAf(140), 0, 0, 0, -1, Dimen.m16, Dimen.m40, Dimen.m40, -1));

        technicianRv = new RecyclerView(activity);
        technicianRv.setId(Ids.PARENT_VIEW_ID_1);
        technicianRv.setLayoutManager(new LinearLayoutManager(activity));
        technicianAdapter = new TechnicianAdapter(controller.provideTechniciansList());
        technicianAdapter.setListener(listener);
        technicianRv.setAdapter(technicianAdapter);
        parent.addView(technicianRv, Param.consParam(-1, 0, -edtSearch.getId(), 0, 0, 0,-1,-1,-1,Dimen.m40));

        if (technicianAdapter != null && technicianAdapter.getItemCount() == 0) {
            loadEmptyState();
        } else {
            hideEmptyState();
        }

        ImageView btnAddNewTechnician = new ImageView(activity);
        btnAddNewTechnician.setImageResource(R.drawable.ic_add);
        btnAddNewTechnician.setBackground(Theme.createRoundSelectorDrawable(Colors.black10Op, Colors.primaryBlue, Theme.getAf(300), Theme.getAf(300)));
        btnAddNewTechnician.setElevation(50);
        btnAddNewTechnician.setColorFilter(Colors.white);
        btnAddNewTechnician.setOnClickListener(onAddTechnicianBtnClicked);
        btnAddNewTechnician.setPadding(Theme.getAf(20), Theme.getAf(20), Theme.getAf(20), Theme.getAf(20));
        parent.addView(btnAddNewTechnician, Param.consParam(Dimen.m64, Dimen.m64, -1, -1, 0, 0, -1, -1, Dimen.m40, Dimen.m40));

        edtSearch.addTextChangedListener(textWatcher);

        return parent;
    }
    private TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            technicianAdapter.search(charSequence.toString().trim());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void loadEmptyState() {
        if (emptyStateParent == null) {
            emptyStateParent = new ConstraintLayout(activity);
            parent.addView(emptyStateParent, Param.consParam(0, -2, 0, 0, 0, 0,-1, Dimen.m64, Dimen.m64, -1));

            ImageView imgState = new ImageView(activity);
            imgState.setImageResource(R.drawable.empty_state_technician);
            imgState.setId(Ids.IMAGE_VIEW_ID_1);
            emptyStateParent.addView(imgState, Param.consParam(-2, -2, 0, 0, 0, -1));

            TextView tvState = new TextView(activity);
            tvState.setText("تکنسینی وجود ندارد");
            tvState.setTypeface(AppLoader.mediumTypeface);
            tvState.setTextColor(Colors.gray2);
            tvState.setTextSize(0, Dimen.fontSize14);
            emptyStateParent.addView(tvState, Param.consParam(-2, -2, -imgState.getId(), imgState.getId(), imgState.getId(), -1,-Theme.getAf(280),-1,-1,-1));
        }
        emptyStateParent.setVisibility(View.VISIBLE);
    }

    private void hideEmptyState() {
        if (emptyStateParent != null)
            emptyStateParent.setVisibility(View.GONE);
    }

    private View.OnClickListener onAddTechnicianBtnClicked = v -> {
        activity.pushFragment(new PutTechnicianFragment(), PutTechnicianFragment.class.getName());
    };
    private TechnicianAdapter.OnItemClickListener listener=new TechnicianAdapter.OnItemClickListener() {
        @Override
        public void onItemDeleted(int itemCount) {
            if (technicianAdapter.getItemCount() == 0) {
                loadEmptyState();
            } else
                hideEmptyState();
        }

        @Override
        public void onItemSelected(Employee employee) {

        }
    };
    @Override
    public void onHideChange(boolean isHide) {
        if (!isHide) {
            technicianAdapter = new TechnicianAdapter(controller.provideTechniciansList());
            technicianAdapter.setListener(listener);
            technicianRv.setAdapter(technicianAdapter);
            Theme.setUpStatusBar(activity, Colors.background, false);
            if (technicianAdapter.getItemCount() == 0) {
                loadEmptyState();
            } else
                hideEmptyState();
        }
        parent.setBackgroundColor(Colors.background);
    }
}
