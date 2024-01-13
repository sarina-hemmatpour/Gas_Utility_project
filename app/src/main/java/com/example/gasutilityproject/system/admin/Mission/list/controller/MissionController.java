package com.example.gasutilityproject.system.admin.Mission.list.controller;

import com.example.gasutilityproject.Data.Account;
import com.example.gasutilityproject.Data.DataBase.Remote.Query.MissionQuery;
import com.example.gasutilityproject.Data.Model.Mission;
import com.example.gasutilityproject.system.admin.Mission.list.view.MissionAdapter;
import com.example.gasutilityproject.system.admin.Mission.list.view.MissionFragment;

import java.util.ArrayList;

public class MissionController {
    private MissionFragment fragment;

    public MissionController(MissionFragment fragment) {
        this.fragment = fragment;
    }

    public ArrayList<Mission> provideMissionsList() {
        if (Account.getInstance().isIsAdmin())
            return MissionQuery.getInstance(fragment.activity).getMissionsModel();
        return MissionQuery.getInstance(fragment.activity).getMissionsOfTechnician(Account.getInstance().getUser().getId());
    }
}
