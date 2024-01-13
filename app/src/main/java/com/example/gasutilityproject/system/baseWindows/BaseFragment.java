package com.example.gasutilityproject.system.baseWindows;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class BaseFragment extends Fragment {
    public BaseActivity activity;
    protected ConstraintLayout parent;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        activity = (BaseActivity) getActivity();
        if (activity == null)
            return null;
        parent = new ConstraintLayout(activity);
        parent.setMotionEventSplittingEnabled(false);
        parent.setFocusableInTouchMode(true);
        parent.setFocusable(true);
        parent.setOnClickListener(null);
        parent.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);


        return onViewFragmentCreate(savedInstanceState);
    }
    public void hideFragment(FragmentManager manager, boolean isNavigation) {
        onHideChange(true);
        if (parent != null)
            parent.setVisibility(View.GONE);
        if (isNavigation)
            manager.beginTransaction().hide(this).commit();
    }
    public void showFragment(FragmentManager manager, boolean isNavigation) {
        if (parent != null)
            parent.setVisibility(View.VISIBLE);
        onHideChange(false);
        if (isNavigation)
            manager.beginTransaction().show(this).commit();
    }

    public FragmentManager getFM() {
        try {
            FragmentManager fragmentManager = getChildFragmentManager();
            if (fragmentManager.isDestroyed() || activity.activityPaused)
                return null;
            else
                return fragmentManager;
        } catch (IllegalStateException e) {
            return null;
        }
    }
    protected void onHideChange(boolean isHide) {

    }
    public void onBackPressed() {

    }
    protected abstract ViewGroup onViewFragmentCreate(Bundle savedInstanceState);
}
