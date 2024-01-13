package com.example.gasutilityproject.system.admin.Mission.denyMission;

import com.example.gasutilityproject.Data.DataBase.Remote.EmitManager;
import com.example.gasutilityproject.Data.Model.Mission;

import org.json.JSONException;
import org.json.JSONObject;

public class DenyMissionController {
    private DenyMissionFragment fragment;

    public DenyMissionController(DenyMissionFragment fragment) {
        this.fragment = fragment;
    }
    public void denyMission(Mission mission, String text) {
        JSONObject jsonObject = mission.toJSON();
        try {
            jsonObject.put("technicianFailedMissionReason", text);
            jsonObject.put("technicianStatus", 2);
            jsonObject.put("doneDate", String.valueOf((int) (System.currentTimeMillis() / 1000 / 3600 / 24)));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        EmitManager.getInstance().emitAddMission(fragment.activity, null, jsonObject, false);
    }
}
