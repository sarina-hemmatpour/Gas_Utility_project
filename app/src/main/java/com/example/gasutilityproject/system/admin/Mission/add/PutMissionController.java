package com.example.gasutilityproject.system.admin.Mission.add;

import com.example.gasutilityproject.Data.DataBase.Remote.EmitManager;
import com.example.gasutilityproject.Data.DataBase.Remote.Query.EmployeeQuery;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.Data.Model.Mission;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PutMissionController {
    PutMissionFragment fragment;

    public PutMissionController(PutMissionFragment fragment) {
        this.fragment = fragment;
    }

    public ArrayList<Employee> provideTechniciansList() {
        return EmployeeQuery.getInstance(fragment.activity).getTechnicians();
    }

    protected void addNewMission(Mission mission, String[] texts) {
        JSONObject jsonObject = mission == null ? (new JSONObject()) : mission.toJSON();
        try {
            jsonObject.put("title", texts[0]);
            jsonObject.put("description", texts[1]);
            jsonObject.put("deadlineInDays", texts[2]);
            jsonObject.put("technicianId", Long.parseLong(texts[3]));
            jsonObject.put("location", texts[4]);
            int duDateInDays = (int) ((System.currentTimeMillis() / 1000 / 60 / 60 / 24) + jsonObject.optInt("deadlineInDays"));
            jsonObject.put("dueDateInDays", duDateInDays);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        EmitManager.getInstance().emitAddMission(fragment.activity, null, jsonObject, mission == null);
    }
}
