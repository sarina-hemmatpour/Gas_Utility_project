package com.example.gasutilityproject.system.main;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;

import com.example.gasutilityproject.Data.Account;
import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Colors;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.system.uiTools.Animation.Animation;
import com.example.gasutilityproject.system.uiTools.Param;
import com.example.gasutilityproject.system.uiTools.Theme;
import com.example.gasutilityproject.system.baseWindows.BaseFragment;
import com.example.gasutilityproject.system.admin.Mission.list.view.MissionFragment;
import com.example.gasutilityproject.system.admin.profile.ProfileFragment;
import com.example.gasutilityproject.system.admin.technician.list.view.TechnicianFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainFragment extends BaseFragment {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout containerView;
    public TechnicianFragment technicianFragment;
    public ProfileFragment profileFragment;
    public MissionFragment missionFragment = new MissionFragment();
    public static boolean isAdmin = true;
    private int lastItem = isAdmin ? R.id.navigation_mission : R.id.navigation_limited_mission;

    public static void setIsAdmin(boolean isAdmin) {
        MainFragment.isAdmin = isAdmin;
    }

    @Override
    protected ViewGroup onViewFragmentCreate(Bundle savedInstanceState) {
        FragmentManager fragmentManager = getFM();
        if (fragmentManager == null) {
            activity.finish();
            return null;
        }

        int[][] states = new int[][]{new int[]{android.R.attr.state_checked}, new int[]{-android.R.attr.state_checked}};
        int[] colors = new int[]{Colors.bgDark, Colors.color6b6b6b};
        ColorStateList stateList = new ColorStateList(states, colors);
        bottomNavigationView = new BottomNavigationView(activity);
        bottomNavigationView.inflateMenu(isAdmin ? R.menu.bottom_navigation_menu : R.menu.bottom_navigation_menu_limited);
        bottomNavigationView.setItemIconTintList(stateList);
        bottomNavigationView.setLabelVisibilityMode(NavigationBarView.LABEL_VISIBILITY_LABELED);
        bottomNavigationView.setBackgroundColor(Colors.white);
        bottomNavigationView.setItemTextColor(stateList);
        bottomNavigationView.setElevation(26);
//        AppLoader.handler.postDelayed(() -> {
//            bottomNavigationView.setSelectedItemId(R.id.navigation_mission);
//        }, 200);
        bottomNavigationView.setOnItemSelectedListener(onNavigationItemSelected);
        parent.addView(bottomNavigationView, Param.consParam(0, Theme.getAf(180), -1, 0, 0, 0));

        containerView = new FrameLayout(activity);
        containerView.setId(Ids.PARENT_VIEW_ID_1);
        containerView.startAnimation(Animation.translateYAndAlphaAnimation(Theme.getAf(100), 0, 0, 1, 300));
        parent.addView(containerView, Param.consParam(-1, -1, 0, 0, 0, -bottomNavigationView.getId(), -1, -1, -1, Theme.getAf(180)));
        fragmentManager.beginTransaction().add(containerView.getId(), missionFragment).commit();

        return parent;
    }

    private NavigationBarView.OnItemSelectedListener onNavigationItemSelected = menuItem -> {
        int newItem = menuItem.getItemId();
        if ((getFM()) == null || newItem == lastItem)
            return false;

        FragmentManager fragmentManager = getFM();
        switch (newItem) {
            case R.id.navigation_mission:
                if (missionFragment == null) {
                    missionFragment = new MissionFragment();
                    fragmentManager.beginTransaction().add(containerView.getId(), missionFragment).commit();
                } else {
                    missionFragment.showFragment(fragmentManager, true);
                }
                break;
            case R.id.navigation_technician:
                if (technicianFragment == null) {
                    technicianFragment = new TechnicianFragment();
                    fragmentManager.beginTransaction().add(containerView.getId(), technicianFragment).commit();
                } else {
                    technicianFragment.showFragment(fragmentManager, true);
                }
                break;
            case R.id.navigation_profile:
            case R.id.navigation_limited_profile:
                if (profileFragment == null) {
                    profileFragment = new ProfileFragment();
                    profileFragment.setEmployeeModel(Account.getInstance().getUser());
                    fragmentManager.beginTransaction().add(containerView.getId(), profileFragment).commit();
                } else {
                    profileFragment.showFragment(fragmentManager, true);
                }
                break;
            case R.id.navigation_limited_mission:
                break;
        }
        switch (lastItem) {
            case R.id.navigation_mission:
                if (missionFragment != null)
                    missionFragment.hideFragment(fragmentManager, true);
                break;
            case R.id.navigation_technician:
                if (technicianFragment != null)
                    technicianFragment.hideFragment(fragmentManager, true);
                break;
            case R.id.navigation_profile:
            case R.id.navigation_limited_profile:
                if (profileFragment != null)
                    profileFragment.hideFragment(fragmentManager, true);
                break;
            case R.id.navigation_limited_mission:
                break;
        }

        lastItem = newItem;
        return true;
    };

    private boolean isTechnicianShowing() {
        return lastItem == 2;
    }

    private boolean isProfileShowing() {
        return lastItem == 3;
    }

    private boolean isMissionShowing() {
        return lastItem == 1;
    }
}
