package com.example.gasutilityproject.system.admin.Mission.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.admin.technician.list.view.TechnicianAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class SelectTechnicianDialog extends BottomSheetDialogFragment {
    private Context context;
    private ConstraintLayout parent;
    private TechnicianAdapter technicianAdapter;
    private ArrayList<Employee> technicians;
    private TechnicianSelectListener listener;

    public void setUp(ArrayList<Employee> technicians, TechnicianSelectListener listener) {
        this.technicians = technicians;
        this.listener = listener;
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

        TextView tvTitle = new TextView(context);
        tvTitle.setId(Ids.TEXT_VIEW_ID_1);
        tvTitle.setText(".لطفا مامور مورد نظر را انتخاب کنید");
        tvTitle.setTypeface(AppLoader.mediumTypeface);
        tvTitle.setTextSize(0, Dimen.fontSize14);
        parent.addView(tvTitle, Param.consParam(-2, -2, 0, -1, 0, -1, Dimen.m40, -1, Dimen.m40, -1));

        RecyclerView rvTechnician = new RecyclerView(context);
        rvTechnician.setId(Ids.PARENT_VIEW_ID_1);
        rvTechnician.setLayoutManager(new LinearLayoutManager(context));
        technicianAdapter = new TechnicianAdapter(technicians);
        technicianAdapter.ease();
        technicianAdapter.setListener(new TechnicianAdapter.OnItemClickListener() {
            @Override
            public void onItemDeleted(int itemCount) {

            }

            @Override
            public void onItemSelected(Employee employee) {
                listener.onTechnicianSelected(employee);
                dismiss();
            }
        });
        rvTechnician.setAdapter(technicianAdapter);
        parent.addView(rvTechnician, Param.consParam(-1, -2, -tvTitle.getId(), 0, 0, 0, Dimen.m24, -1, -1, Dimen.m40));

        return parent;
    }
    public interface TechnicianSelectListener {
        void onTechnicianSelected(Employee technician);
    }
}
