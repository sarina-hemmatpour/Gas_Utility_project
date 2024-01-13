package com.example.gasutilityproject.system.admin.Mission.doMission;

import com.example.gasutilityproject.Data.DataBase.Remote.EmitManager;
import com.example.gasutilityproject.Data.Model.Mission;

import org.json.JSONException;
import org.json.JSONObject;

public class DoMissionController {
    DoMissionFragment fragment;

    public DoMissionController(DoMissionFragment fragment) {
        this.fragment = fragment;
    }

    public void doMission(Mission mission, String[] texts) {
        JSONObject jsonObject = mission.toJSON();
        try {
            jsonObject.put("technicianReport", texts[0]);
            jsonObject.put("technicianLocation",texts[1]);
            jsonObject.put("technicianReportPhoto", texts[2]);
            jsonObject.put("technicianStatus", 1);
            jsonObject.put("doneDate", String.valueOf((int) (System.currentTimeMillis() / 1000 / 3600 / 24)));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        EmitManager.getInstance().emitAddMission(fragment.activity, null, jsonObject, false);
    }
}
