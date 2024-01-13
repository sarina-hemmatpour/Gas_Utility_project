package com.example.gasutilityproject.system.admin.Mission.list.view;

import android.graphics.Color;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.Account;
import com.example.gasutilityproject.Data.DataBase.Remote.EmitManager;
import com.example.gasutilityproject.Data.DataBase.Remote.Query.EmployeeQuery;
import com.example.gasutilityproject.Data.DataBase.Remote.Query.MissionQuery;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.Data.Model.Mission;
import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Dimen;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.system.admin.Mission.denyMission.DenyMissionFragment;
import com.example.gasutilityproject.system.admin.Mission.dialog.ExtraTimeRequestDialog;
import com.example.gasutilityproject.system.admin.Mission.doMission.DoMissionFragment;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.baseWindows.BaseActivity;
import com.example.gasutilityproject.system.admin.Mission.add.PutMissionFragment;
import com.example.gasutilityproject.system.admin.Mission.dialog.VerifyMissionDialog;
import com.example.gasutilityproject.system.admin.technician.add.PutTechnicianFragment;
import com.example.gasutilityproject.system.admin.technician.list.view.TechnicianAdapter;
import com.example.gasutilityproject.system.main.MainFragment;

import java.util.ArrayList;

public class MissionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Mission> missions;
    private ConstraintLayout itemParent;

    private TechnicianAdapter.OnItemClickListener listener;


    public MissionAdapter(ArrayList<Mission> missions) {
        this.missions = missions;
    }

    public void setListener(TechnicianAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemParent = new ConstraintLayout(parent.getContext());
        itemParent.setLayoutParams(Param.consParam(-1, -2, -1, -1, -1, -1, Dimen.m16, Dimen.m40, Dimen.m40, -1));
        return Account.getInstance().isIsAdmin() ? new MissionHolder(itemParent) : new TechMissionHolder(itemParent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (Account.getInstance().isIsAdmin()) {
            ((MissionHolder) holder).bindItem(position);
        } else {
            ((TechMissionHolder) holder).bindItem(position);
        }

    }

    @Override
    public int getItemCount() {
        return missions.size();
    }

    class TechMissionHolder extends RecyclerView.ViewHolder {
        private View statusView;
        private TextView tvTitle, btnNeedMoreTime;
        private TextView btnDoMission;
        private TextView btnDenyMission;
        private TextView tvRemainingTime;

        public TechMissionHolder(@NonNull ConstraintLayout itemView) {
            super(itemView);
            itemView.setBackground(Theme.createRoundDrawable(Dimen.r12, Colors.white));

            statusView = new View(itemView.getContext());
            statusView.setId(Ids.VIEW_ID_1);
            statusView.setBackground(Theme.createRoundDrawable(Dimen.r12, 0, Dimen.r12, 0, Colors.gray2));
            itemView.addView(statusView, Param.consParam(Theme.getAf(20), 0, 0, -1, 0, 0, Theme.getAf(55), -1, -1, Theme.getAf(55)));

            LinearLayout linearLayout = new LinearLayout(itemView.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            itemView.addView(linearLayout, Param.consParam(-2, -2, 0, -1, -statusView.getId(), 0, Dimen.m16, -1, Dimen.m24, Dimen.m16));

            tvTitle = new TextView(itemView.getContext());
            tvTitle.setId(Ids.TEXT_VIEW_ID_3);
            tvTitle.setTypeface(AppLoader.mediumTypeface);
            tvTitle.setTextSize(0, Dimen.fontSize14);
            tvTitle.setTextColor(Colors.drkTxt);
            linearLayout.addView(tvTitle, Param.linearParam(-2, -2, Gravity.RIGHT, -1, -1, -1, -1));

            tvRemainingTime = new TextView(itemView.getContext());
            tvRemainingTime.setTypeface(AppLoader.mediumTypeface);
            tvRemainingTime.setId(Ids.TEXT_VIEW_ID_5);
            tvRemainingTime.setTextSize(0, Dimen.fontSize14);
            tvRemainingTime.setTextColor(Colors.gray2);
            linearLayout.addView(tvRemainingTime, Param.linearParam(-2, -2, Gravity.RIGHT, -1, -1, -1, -1));

            btnNeedMoreTime = new TextView(itemView.getContext());
            btnNeedMoreTime.setTypeface(AppLoader.mediumTypeface);
            btnNeedMoreTime.setId(Ids.TEXT_VIEW_ID_4);
            btnNeedMoreTime.setBackground(Theme.createRoundSelectorDrawable(Colors.black10Op, 0, Theme.getAf(30), Theme.getAf(30)));
            btnNeedMoreTime.setText("درخواست زمان بیشتر");
            btnNeedMoreTime.setPadding(Theme.getAf(5), Theme.getAf(5), Theme.getAf(5), Theme.getAf(5));
            btnNeedMoreTime.setTextSize(0, Theme.getAf(28));
            btnNeedMoreTime.setTextColor(Colors.primaryBlue);
            linearLayout.addView(btnNeedMoreTime, Param.linearParam(-2, -2, Gravity.RIGHT, Dimen.m8, -1, -1, -1));

            LinearLayout linearLayoutActions = new LinearLayout(itemView.getContext());
            linearLayoutActions.setOrientation(LinearLayout.VERTICAL);
            linearLayoutActions.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            itemView.addView(linearLayoutActions, Param.consParam(-2, -2, 0, 0, -1, 0, Dimen.m32, Dimen.m24, -1, Dimen.m32));

            btnDoMission = new TextView(itemView.getContext());
            btnDoMission.setText("انجام");
            btnDoMission.setTypeface(AppLoader.mediumTypeface);
            btnDoMission.setTextColor(Colors.white);
            btnDoMission.setId(Ids.BUTTON_VIEW_ID_4);
            btnDoMission.setPadding(Theme.getAf(65), Theme.getAf(10), Theme.getAf(65), Theme.getAf(10));
            btnDoMission.setBackground(Theme.createRoundSelectorDrawable(Colors.black10Op, Color.parseColor("#C82E3192"), Theme.getAf(100), Theme.getAf(100)));
            linearLayoutActions.addView(btnDoMission, Param.linearParam(-2, -2, Gravity.LEFT, -1, -1, -1, -1));

            btnDenyMission = new TextView(itemView.getContext());
            btnDenyMission.setText("عدم انجام");
            btnDenyMission.setTypeface(AppLoader.mediumTypeface);
            btnDenyMission.setTextColor(Colors.black3);
            btnDenyMission.setId(Ids.BUTTON_VIEW_ID_5);
            btnDenyMission.setPadding(Theme.getAf(30), Theme.getAf(10), Theme.getAf(30), Theme.getAf(10));
            btnDenyMission.setBackground(Theme.createRoundSelectorDrawable(Colors.black10Op, Colors.lightGray, Theme.getAf(100), Theme.getAf(100)));
            linearLayoutActions.addView(btnDenyMission, Param.linearParam(-2, -2, Gravity.LEFT, Dimen.m16, -1, -1, -1));
        }

        public void bindStatusViewColor(Mission.MissionStatus technicianStatus) {
            switch (technicianStatus) {
                case PENDING:
                    statusView.setBackground(Theme.createRoundDrawable(Dimen.r12, 0, Dimen.r12, 0, Colors.lightGray));
                    break;
                case VERIFIED:
                    statusView.setBackground(Theme.createRoundDrawable(Dimen.r12, 0, Dimen.r12, 0, Colors.greenDark));
                    break;
                case UNVERIFIED:
                    statusView.setBackground(Theme.createRoundDrawable(Dimen.r12, 0, Dimen.r12, 0, Colors.red600));
                    break;
            }
        }

        public void bindItem(int position) {
//            missions.get(position).setDone(true);

            bindStatusViewColor(missions.get(position).getStatus());

            tvTitle.setText(missions.get(position).getTitle());

            itemView.setClickable(true);
            itemView.setOnClickListener(v -> {
                PutMissionFragment putMissionFragment = new PutMissionFragment();
                putMissionFragment.setMissionModel(missions.get(position));
                putMissionFragment.disableEdit();
                ((BaseActivity) (itemView.getContext())).pushFragment(putMissionFragment, PutTechnicianFragment.class.getName());
            });
            btnDoMission.setOnClickListener(v -> {
                DoMissionFragment doMissionFragment = new DoMissionFragment();
                doMissionFragment.setMissionModel(missions.get(position));
                ((BaseActivity) (itemView.getContext())).pushFragment(doMissionFragment, DoMissionFragment.class.getName());
            });

            btnDenyMission.setOnClickListener(v -> {
                DenyMissionFragment denyMissionFragment = new DenyMissionFragment();
                denyMissionFragment.setMissionModel(missions.get(position));
                ((BaseActivity) (itemView.getContext())).pushFragment(denyMissionFragment, DenyMissionFragment.class.getName());

            });
            btnNeedMoreTime.setOnClickListener(v -> {
                FragmentManager fragmentManager = ((BaseActivity) itemView.getContext()).getFM();
                if (fragmentManager != null) {
                    ExtraTimeRequestDialog extraTimeDialog = new ExtraTimeRequestDialog();
                    extraTimeDialog.setup(missions.get(position), () -> {
                        missions = MissionQuery.getInstance(itemParent.getContext()).getMissionsOfTechnician(Account.getInstance().getUser().getId());
                        notifyDataSetChanged();
                    });
                    extraTimeDialog.show(fragmentManager, null);
                }
            });

            if (missions.get(position).getTechnicianStatus() == 0) {
                int today = (int) (System.currentTimeMillis() / 1000 / 24 / 3600);
                int dueDate = missions.get(position).getDueDateInDays();
                tvRemainingTime.setText(Html.fromHtml("مهلت باقی\u200Cمانده: " + (Math.max((dueDate - today), 0)) + " روز"));

                btnDenyMission.setVisibility(View.VISIBLE);
                btnDoMission.setVisibility(View.VISIBLE);
            } else if (missions.get(position).getTechnicianStatus() == 1) {
                btnDenyMission.setVisibility(View.GONE);
                btnDoMission.setVisibility(View.VISIBLE);
                btnDoMission.setClickable(false);
                btnNeedMoreTime.setVisibility(View.GONE);

                btnDoMission.setTextColor(Colors.greenDark);
                btnDoMission.setText("انجام شد");
                btnDoMission.setPadding(Theme.getAf(30), Theme.getAf(10), Theme.getAf(30), Theme.getAf(10));
                btnDoMission.setBackground(Theme.createRoundDrawable(Theme.getAf(100), Theme.getAf(100), Theme.getAf(100), Theme.getAf(100), Color.parseColor("#140FC642")));

                int today = (int) (System.currentTimeMillis() / 1000 / 24 / 3600);
                int doneDate = missions.get(position).getDoneDate();
                int days = today - doneDate;
                tvRemainingTime.setText("زمان تعیین وضعیت:\n " + (days == 0 ? "امروز" : (days + " روز پیش")));
            } else {
                btnDenyMission.setVisibility(View.VISIBLE);
                btnDenyMission.setClickable(false);
                btnDoMission.setVisibility(View.GONE);
                btnNeedMoreTime.setVisibility(View.GONE);

                btnDenyMission.setTextColor(Colors.red600);
                btnDenyMission.setPadding(Theme.getAf(30), Theme.getAf(10), Theme.getAf(30), Theme.getAf(10));
                btnDenyMission.setText("انجام نشد");
                btnDenyMission.setBackground(Theme.createRoundDrawable(Theme.getAf(100), Theme.getAf(100), Theme.getAf(100), Theme.getAf(100), Colors.red1));

                int today = (int) (System.currentTimeMillis() / 1000 / 24 / 3600);
                int doneDate = missions.get(position).getDoneDate();
                int days = today - doneDate;
                tvRemainingTime.setText("زمان تعیین وضعیت:\n " + (days == 0 ? "امروز" : (days + " روز پیش")));
            }
            int extraTime = missions.get(position).getExtraTimeRequestInDays();
            if (extraTime > 0) {
                btnNeedMoreTime.setClickable(false);
                btnNeedMoreTime.setText("درخواست " + extraTime + " روز زمان اضافه");
                btnNeedMoreTime.setTextColor(Colors.red600);
                btnNeedMoreTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                btnNeedMoreTime.setBackground(null);
            } else {
                btnNeedMoreTime.setClickable(true);
                btnNeedMoreTime.setText("درخواست زمان بیشتر");
                btnNeedMoreTime.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_left_arrow, 0, 0, 0);
                btnNeedMoreTime.setCompoundDrawablePadding(Theme.getAf(8));
                btnNeedMoreTime.setBackground(Theme.createRoundSelectorDrawable(Colors.black10Op, 0, Theme.getAf(30), Theme.getAf(30)));
            }
        }

    }

    class MissionHolder extends RecyclerView.ViewHolder {
        private View statusView;
        private TextView tvTitle, tvDescription;
        private ImageView imgIsVerified;
        private ImageView imgEdit;
        private ImageView imgDelete;
        private TextView tvRemainingTime;

        public MissionHolder(@NonNull ConstraintLayout itemView) {
            super(itemView);
            itemView.setBackground(Theme.createRoundDrawable(Dimen.r12, Colors.white));

            statusView = new View(itemView.getContext());
            statusView.setId(Ids.VIEW_ID_1);
            statusView.setBackground(Theme.createRoundDrawable(Dimen.r12, 0, Dimen.r12, 0, Colors.gray2));
            itemView.addView(statusView, Param.consParam(Theme.getAf(20), 0, 0, -1, 0, 0, Theme.getAf(55), -1, -1, Theme.getAf(55)));

            LinearLayout linearLayout = new LinearLayout(itemView.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            itemView.addView(linearLayout, Param.consParam(-2, -2, 0, -1, -statusView.getId(), 0, Dimen.m16, -1, Dimen.m24, Dimen.m16));

            tvTitle = new TextView(itemView.getContext());
            tvTitle.setId(Ids.TEXT_VIEW_ID_3);
            tvTitle.setTypeface(AppLoader.mediumTypeface);
            tvTitle.setTextSize(0, Dimen.fontSize14);
            tvTitle.setTextColor(Colors.drkTxt);
            linearLayout.addView(tvTitle, Param.linearParam(-2, -2, Gravity.RIGHT, -1, -1, -1, -1));

            tvDescription = new TextView(itemView.getContext());
            tvDescription.setTypeface(AppLoader.mediumTypeface);
            tvDescription.setId(Ids.TEXT_VIEW_ID_4);
            tvDescription.setTextSize(0, Dimen.fontSize14);
            tvDescription.setTextColor(Colors.gray2);
            linearLayout.addView(tvDescription, Param.linearParam(-2, -2, Gravity.RIGHT, -1, -1, -1, -1));

            tvRemainingTime = new TextView(itemView.getContext());
            tvRemainingTime.setTypeface(AppLoader.mediumTypeface);
            tvRemainingTime.setId(Ids.TEXT_VIEW_ID_5);
            tvRemainingTime.setTextSize(0, Dimen.fontSize14);
            tvRemainingTime.setTextColor(Colors.gray2);
            linearLayout.addView(tvRemainingTime, Param.linearParam(-2, -2, Gravity.RIGHT, -1, -1, -1, -1));

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


            imgIsVerified = new ImageView(itemView.getContext());
            imgIsVerified.setId(Ids.IMAGE_VIEW_ID_1);
            imgIsVerified.setImageResource(R.drawable.ic_pending);
            imgIsVerified.setColorFilter(Colors.gray2);
            imgIsVerified.setOnClickListener(v -> {
                //todo load verify bottom sheet dialog
                FragmentManager fm = ((BaseActivity) (itemView.getContext())).getFM();
                if (fm != null) {
                    VerifyMissionDialog verifyMissionDialog = new VerifyMissionDialog();
                    verifyMissionDialog.show(fm, null);
                }
            });
            imgIsVerified.setBackground(Theme.createRoundSelectorDrawable(Colors.gray1, Colors.transparent, Theme.getAf(300), Theme.getAf(300)));
            imgIsVerified.setPadding(Theme.getAf(20), Theme.getAf(20), Theme.getAf(20), Theme.getAf(20));
            itemView.addView(imgIsVerified, Param.consParam(Theme.getAf(100), Theme.getAf(100), -imgEdit.getId(), 0, -1, 0, Dimen.m12, Dimen.m24, -1, Dimen.m24));

        }

        public void bindImgIsVerified(Mission.MissionStatus status) {
            switch (status) {
                case PENDING:
                    imgIsVerified.setImageResource(R.drawable.ic_pending);
                    imgIsVerified.setColorFilter(Colors.darkGray);
                    break;
                case VERIFIED:
                    imgIsVerified.setImageResource(R.drawable.ic_verified);
                    imgIsVerified.setColorFilter(Colors.greenDark);
                    break;
                case UNVERIFIED:
                    imgIsVerified.setImageResource(R.drawable.ic_unverified);
                    imgIsVerified.setColorFilter(Colors.red600);
                    break;
            }

        }

        public void bindStatusViewColor(int technicianStatus) {
            switch (technicianStatus) {
                case 0:
                    statusView.setBackground(Theme.createRoundDrawable(Dimen.r12, 0, Dimen.r12, 0, Colors.lightGray));
                    break;
                case 1:
                    statusView.setBackground(Theme.createRoundDrawable(Dimen.r12, 0, Dimen.r12, 0, Colors.greenDark));
                    break;
                case 2:
                    statusView.setBackground(Theme.createRoundDrawable(Dimen.r12, 0, Dimen.r12, 0, Colors.red600));
                    break;
            }
        }

        public void bindItem(int position) {
//            missions.get(position).setDone(true);

            bindStatusViewColor(missions.get(position).getTechnicianStatus());

            bindImgIsVerified(missions.get(position).getStatus());

            Employee employee = EmployeeQuery.getInstance(itemView.getContext()).getTechnician(missions.get(position).getTechnicianId());
            tvDescription.setText(MainFragment.isAdmin ?
                    employee.getFirstname() + " " + employee.getLastname()
                    : "مهلت: " + missions.get(position).getDueDateInDays());

            tvTitle.setText(missions.get(position).getTitle());

            if (missions.get(position).getTechnicianStatus() == 0) {
                int today = (int) (System.currentTimeMillis() / 1000 / 24 / 3600);
                int dueDate = missions.get(position).getDueDateInDays();
                int extraTime = missions.get(position).getExtraTimeRequestInDays();
                String txtExtraTime = extraTime == 0 ? "" : "<br><font color = '#E53935'>درخواست زمان بیشتر (m روز)</font>".replace("m", String.valueOf(extraTime));
                tvRemainingTime.setText(Html.fromHtml("مهلت باقی\u200Cمانده: " + (Math.max((dueDate - today), 0)) + " روز" + txtExtraTime));
            }


            itemView.setClickable(true);
            itemView.setOnClickListener(v -> {
                PutMissionFragment putMissionFragment = new PutMissionFragment();
                putMissionFragment.setMissionModel(missions.get(position));
                putMissionFragment.disableEdit();
                ((BaseActivity) (itemView.getContext())).pushFragment(putMissionFragment, PutTechnicianFragment.class.getName());
            });
            imgDelete.setOnClickListener(v -> {
                EmitManager.getInstance().emitDeleteMission(itemView.getContext(), null, missions.get(position).getId());
                missions.remove(position);
                notifyDataSetChanged();
                listener.onItemDeleted(getItemCount());
            });

            if (missions.get(position).getTechnicianStatus() == 0) { //pending
                imgEdit.setEnabled(true);
                imgEdit.setOnClickListener(v -> {
                    PutMissionFragment putMissionFragment = new PutMissionFragment();
                    putMissionFragment.setMissionModel(missions.get(position));
                    ((BaseActivity) (itemView.getContext())).pushFragment(putMissionFragment, PutTechnicianFragment.class.getName());
                });
            } else {
                imgEdit.setEnabled(false);
                imgEdit.setColorFilter(Colors.gray2);
            }

            if (missions.get(position).getTechnicianStatus() != 1) { // notDone or pending
                imgIsVerified.setEnabled(false);
                imgIsVerified.setColorFilter(Colors.gray2);

            } else if (missions.get(position).getStatus() == Mission.MissionStatus.PENDING) {
                imgIsVerified.setEnabled(true);
                imgIsVerified.setOnClickListener(v -> {
                    FragmentManager fm = ((BaseActivity) (itemView.getContext())).getFM();
                    if (fm != null) {
                        VerifyMissionDialog verifyMissionDialog = new VerifyMissionDialog();
                        verifyMissionDialog.setup(missions.get(position), validate -> {
                            if (validate) {
                                missions.get(position).setStatus(Mission.MissionStatus.VERIFIED);
                                notifyDataSetChanged();
                                EmitManager.getInstance().emitUpdateMissionStatus(itemView.getContext(), null, missions.get(position).getId(), Mission.MissionStatus.VERIFIED);
                            } else {
                                missions.get(position).setStatus(Mission.MissionStatus.UNVERIFIED);
                                notifyDataSetChanged();
                                EmitManager.getInstance().emitUpdateMissionStatus(itemView.getContext(), null, missions.get(position).getId(), Mission.MissionStatus.UNVERIFIED);
                            }
                        });
                        verifyMissionDialog.show(fm, null);
                    }
                });
            } else {
                imgIsVerified.setEnabled(false);
            }

        }

    }

    public void search(String titlePart) {
        if (titlePart.equals("")) {
            missions = Account.getInstance().isIsAdmin() ?
                    MissionQuery.getInstance(itemParent.getContext()).getMissionsModel() :
                    MissionQuery.getInstance(itemParent.getContext()).getMissionsOfTechnician(Account.getInstance().getUser().getId());
            notifyDataSetChanged();
            return;
        }
        ArrayList<Mission> resultMissions = new ArrayList<>();
        for (int i = 0; i < missions.size(); i++) {
            if (missions.get(i).getTitle().contains(titlePart)) {
                resultMissions.add(missions.get(i));
            }
        }
        missions = resultMissions;
        notifyDataSetChanged();
    }
}
