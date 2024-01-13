package com.example.gasutilityproject.system.baseWindows;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.R;
import com.example.gasutilityproject.StaticFields.Ids;
import com.example.gasutilityproject.system.launch.LaunchFragment;
import com.example.gasutilityproject.system.admin.Mission.list.view.MissionFragment;
import com.example.gasutilityproject.system.admin.profile.ProfileFragment;
import com.example.gasutilityproject.system.admin.technician.list.view.TechnicianFragment;
import com.example.gasutilityproject.system.main.MainFragment;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends FragmentActivity {
    private boolean lockedPush = false;
    public boolean activityPaused = false;
    protected FrameLayout mainParent;
    public final ArrayList<BaseFragment> stacks = new ArrayList<>(4);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainParent = new FrameLayout(this);
        mainParent.setId(Ids.PARENT_VIEW_ID_1);
        mainParent.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        setContentView(mainParent);

        pushFragment(new LaunchFragment(), null, 0, 0);
    }

    public FragmentManager getFM() {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager == null || fragmentManager.isDestroyed())
                return null;
            else
                return fragmentManager;
        } catch (Exception e) {
            return null;
        }
    }

    public void pushFragment(BaseFragment fragment, String tag) {
        pushFragment(fragment, tag, R.anim.scale_open_transition, R.anim.scale_exit_transition, 500);
    }

    public void pushFragment(BaseFragment fragment, String tag, int openAnimation, int exitAnimation) {
        pushFragment(fragment, tag, openAnimation, exitAnimation, 500);
    }

    public void pushFragment(BaseFragment fragment, String tag, int openAnimation, int exitAnimation, int delayHide) {
        FragmentManager fragmentManager = getFM();
        if (lockedPush || fragmentManager == null || activityPaused)
            return;
        lockedPush = true;
        AppLoader.handler.postDelayed(() -> lockedPush = false, 400);

        fragmentManager.beginTransaction().setCustomAnimations(openAnimation, exitAnimation)
                .setReorderingAllowed(true).add(mainParent.getId(), fragment, tag).commit();
        stacks.add(fragment);


        AppLoader.handler.postDelayed(() -> {
            if (stacks.size() > 1)
                stacks.get(stacks.size() - 2).hideFragment(fragmentManager, false);
        }, delayHide);
    }

    public void popFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFM();
        if (fragmentManager == null || activityPaused)
            return;
        fragmentManager.beginTransaction().setReorderingAllowed(true).remove(fragment).commit();
        stacks.remove(stacks.size() - 2);
    }

    public void popFragment(boolean isBack) {
        popFragment(isBack, R.anim.scale_open_transition, R.anim.scale_exit_transition);
    }

    public void popFragment(boolean isBack, int openAnimation, int exitAnimation) {
        FragmentManager fragmentManager = getFM();
        if (fragmentManager == null || activityPaused)
            return;
        try {
            fragmentManager.beginTransaction().setCustomAnimations(openAnimation, exitAnimation)
                    .setReorderingAllowed(true).remove(getCurrentFragment()).commit();
            if (isBack) {
                if (stacks.size() > 1 && stacks.get(stacks.size() - 2) instanceof MainFragment) {
                    MainFragment mainFragment=((MainFragment) stacks.get(stacks.size() - 2));
                    MissionFragment missionFragment = mainFragment.missionFragment;
                    if (missionFragment.missionRv != null)
                        missionFragment.missionRv.suppressLayout(true);
                    if (missionFragment.missionRv != null)
                        AppLoader.handler.postDelayed(() -> missionFragment.missionRv.suppressLayout(false), 20);
                    missionFragment.onHideChange(false);

                    TechnicianFragment technicianFragment = mainFragment.technicianFragment;
                    if (technicianFragment != null) {
                        if (technicianFragment.technicianRv != null)
                            technicianFragment.technicianRv.suppressLayout(true);
                        if (technicianFragment.technicianRv != null)
                            AppLoader.handler.postDelayed(() -> technicianFragment.technicianRv.suppressLayout(false), 20);
                        technicianFragment.onHideChange(false);
                    }

                    ProfileFragment profileFragment = mainFragment.profileFragment;
                    if (profileFragment != null) {
                        profileFragment.onHideChange(false);
                    }
                    stacks.get(stacks.size() - 2).showFragment(fragmentManager, true);
                } else if (stacks.size() > 1) {
                    stacks.get(stacks.size() - 2).onHideChange(false);
                    stacks.get(stacks.size() - 2).showFragment(fragmentManager, true);
                }
            }
            stacks.remove(stacks.size() - 1);
        } catch (Exception ignored) {
            throw ignored;
        }
    }

    public BaseFragment getCurrentFragment() {
        if (stacks.size() == 0)
            return null;
        return stacks.get(stacks.size() - 1);
    }

    @Override
    public void onBackPressed() {
        if (stacks.size() > 1) {
            popFragment(true);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Theme.hideKeyboard(this);
        activityPaused = true;
    }

    @Override
    protected void onDestroy() {
        try {
            FragmentManager fragmentManager = getFM();
            if (fragmentManager != null) {
                List<Fragment> fragments = fragmentManager.getFragments();
                if (fragments != null && fragments.size() > 0) {
                    for (Fragment fragment : fragments) {
                        fragmentManager.beginTransaction().remove(fragment).commit();
                    }
                }
            }

        } catch (Exception ignored) {
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getFM();
        if (fragmentManager == null)
            return;
        activityPaused = false;

    }
}
