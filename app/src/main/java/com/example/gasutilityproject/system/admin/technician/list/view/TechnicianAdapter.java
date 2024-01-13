package com.example.gasutilityproject.system.admin.technician.list.view;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.DataBase.Remote.EmitManager;
import com.example.gasutilityproject.Data.DataBase.Remote.Query.EmployeeQuery;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.baseWindows.BaseActivity;
import com.example.gasutilityproject.system.admin.technician.add.PutTechnicianFragment;

import java.util.ArrayList;
import java.util.List;

public class TechnicianAdapter extends RecyclerView.Adapter<TechnicianAdapter.TechnicianViewHolder> {
    private List<Employee> techniciansList;
    private ConstraintLayout itemParent;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TechnicianAdapter(List<Employee> techniciansList) {
        this.techniciansList = techniciansList;
    }

    private boolean isEasyMode;

    public void ease() {
        isEasyMode = true;
    }

    @NonNull
    @Override
    public TechnicianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemParent = new ConstraintLayout(parent.getContext());
        itemParent.setLayoutParams(Param.consParam(-1, -2, -1, -1, -1, -1, Dimen.m16, Dimen.m40, Dimen.m40, -1));
        return new TechnicianViewHolder(itemParent);
    }

    @Override
    public void onBindViewHolder(@NonNull TechnicianViewHolder holder, int position) {
        holder.bindItem(position);
    }

    @Override
    public int getItemCount() {
        return techniciansList.size();
    }

    public class TechnicianViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvDescription;
        private TextView tvScore;
        private ImageView imgEdit;
        private ImageView imgDelete;

        public TechnicianViewHolder(@NonNull ConstraintLayout itemView) {
            super(itemView);
            itemView.setBackground(Theme.createRoundDrawable(Dimen.r12, Colors.white));

            ImageView imgProfile = new ImageView(itemView.getContext());
            imgProfile.setId(Ids.IMAGE_VIEW_ID_1);
            imgProfile.setImageResource(R.drawable.ic_profile_circle);
            imgProfile.setBackground(Theme.createRoundDrawable(Theme.getAf(300), Theme.getAf(300), Colors.gray1));
            imgProfile.setColorFilter(Colors.gray2);
            imgProfile.setPadding(Theme.getAf(20), Theme.getAf(20), Theme.getAf(20), Theme.getAf(20));
            itemView.addView(imgProfile, Param.consParam(Theme.getAf(100), Theme.getAf(100), 0, -1, 0, 0, -1, -1, Dimen.m24, -1));

            LinearLayout linearLayout = new LinearLayout(itemView.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            itemView.addView(linearLayout, Param.consParam(-2, -2, imgProfile.getId(), -1, -imgProfile.getId(), imgProfile.getId(), -1, -1, Dimen.m16, -1));

            tvName = new TextView(itemView.getContext());
            tvName.setId(Ids.TEXT_VIEW_ID_3);
            tvName.setTypeface(AppLoader.mediumTypeface);
            tvName.setTextSize(0, Dimen.fontSize14);
            tvName.setText("سارینا همت پور");
            tvName.setTextColor(Colors.drkTxt);
            linearLayout.addView(tvName, Param.linearParam(-2, -2, Gravity.RIGHT, -1, -1, -1, -1));

            tvDescription = new TextView(itemView.getContext());
            tvDescription.setTypeface(AppLoader.mediumTypeface);
            tvDescription.setTextSize(0, Dimen.fontSize12);
            tvDescription.setText("مهندس لوله سازی");
            tvDescription.setId(Ids.TEXT_VIEW_ID_4);
            tvDescription.setTextColor(Colors.gray2);
            linearLayout.addView(tvDescription, Param.linearParam(-2, -2, Gravity.RIGHT, -1, -1, -1, -1));

            final int IMG_EDIT_ID = 8485;
            imgEdit = new ImageView(itemView.getContext());
            imgEdit.setId(IMG_EDIT_ID);
            imgEdit.setImageResource(R.drawable.ic_edit);
            imgEdit.setBackground(Theme.createRoundSelectorDrawable(Colors.black10Op, 0, Theme.getAf(100), Theme.getAf(100)));
            imgEdit.setPadding(Theme.getAf(20), Theme.getAf(20), Theme.getAf(20), Theme.getAf(20));
            itemView.addView(imgEdit, Param.consParam(Theme.getAf(90), Theme.getAf(90), 0, 0, -1, -1, Dimen.m16, Dimen.m24, -1, -1));

            imgDelete = new ImageView(itemView.getContext());
            imgDelete.setImageResource(R.drawable.ic_remove);
            imgDelete.setId(Ids.BUTTON_VIEW_ID_4);
            imgDelete.setBackground(Theme.createRoundSelectorDrawable(Colors.black10Op, 0, Theme.getAf(100), Theme.getAf(100)));
            imgDelete.setPadding(Theme.getAf(20), Theme.getAf(20), Theme.getAf(20), Theme.getAf(20));
            itemView.addView(imgDelete, Param.consParam(Theme.getAf(90), Theme.getAf(90), 0, -IMG_EDIT_ID, -1, -1, Dimen.m16, Dimen.m24, -1, -1));

            ImageView imgScore = new ImageView(itemView.getContext());
            imgScore.setId(Ids.IMAGE_VIEW_ID_2);
            imgScore.setImageResource(R.drawable.ic_score_chart);
            imgScore.setColorFilter(Colors.gray2);
            itemView.addView(imgScore, Param.consParam(Theme.getAf(70), Theme.getAf(70), isEasyMode ? 0 : -imgEdit.getId(), 0, -1, -1, Dimen.m8, Dimen.m32, -1, -1));

            tvScore = new TextView(itemView.getContext());
            tvScore.setTypeface(AppLoader.mediumTypeface);
            tvScore.setTextSize(0, Dimen.fontSize12);
            tvScore.setText("32");
            tvScore.setTextColor(Colors.gray2);
            itemView.addView(tvScore, Param.consParam(-2, -2, -imgScore.getId(), imgScore.getId(), imgScore.getId(), 0, -1, -1, -1, Dimen.m16));

            if (isEasyMode) {
                imgEdit.setVisibility(View.GONE);
                imgDelete.setVisibility(View.GONE);
            }

        }

        public void bindItem(int position) {
            tvDescription.setText(techniciansList.get(position).getProficiency());
            tvName.setText(techniciansList.get(position).getFirstname() + " " + techniciansList.get(position).getLastname());
            tvScore.setText(String.valueOf(techniciansList.get(position).getScore()));

            itemView.setClickable(true);
            itemView.setOnClickListener(v -> {
                if (isEasyMode) {
                    listener.onItemSelected(techniciansList.get(position));
                } else {
                    PutTechnicianFragment putTechnicianFragment = new PutTechnicianFragment();
                    putTechnicianFragment.setEmployeeModel(techniciansList.get(position));
                    putTechnicianFragment.disableEdit();
                    ((BaseActivity) (itemView.getContext())).pushFragment(putTechnicianFragment, PutTechnicianFragment.class.getName());
                }
            });
            imgDelete.setOnClickListener(v -> {
                EmitManager.getInstance().emitDeleteTechnician(itemView.getContext(), null, techniciansList.get(position).getId());
                techniciansList.remove(position);
                notifyDataSetChanged();
                listener.onItemDeleted(getItemCount());
            });
            imgEdit.setOnClickListener(v -> {
                PutTechnicianFragment putTechnicianFragment = new PutTechnicianFragment();
                putTechnicianFragment.setEmployeeModel(techniciansList.get(position));
                ((BaseActivity) (itemView.getContext())).pushFragment(putTechnicianFragment, PutTechnicianFragment.class.getName());
            });
        }
    }

    public void search(String titlePart) {
        if (itemParent.getContext() == null) {
            return;
        }
        if (titlePart.equals("")) {
            techniciansList = EmployeeQuery.getInstance(itemParent.getContext()).getTechnicians();
            notifyDataSetChanged();
            return;
        }
        ArrayList<Employee> resultTechnicians = new ArrayList<>();
        for (int i = 0; i < techniciansList.size(); i++) {
            if (techniciansList.get(i).getFirstname().contains(titlePart) || techniciansList.get(i).getLastname().contains(titlePart)) {
                resultTechnicians.add(techniciansList.get(i));
            }
        }
        techniciansList = resultTechnicians;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemDeleted(int itemCount);

        void onItemSelected(Employee employee);
    }
}
