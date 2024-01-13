package com.example.gasutilityproject.Data.DataBase.Remote.Query;

import android.content.Context;

import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.Data.Model.Mission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MissionQuery extends FakeRemoteData {
    private volatile static MissionQuery missionQuery;
    public static final String MISSION_QUERY = "/Mission.txt";
    public static  String MISSION_REPORT_PATH ;
    private JSONArray cacheData = new JSONArray();

    public MissionQuery(Context context) {
        super(context);
        MISSION_REPORT_PATH = context.getExternalFilesDir(null) + "/report_missions.xlsx";
    }

    public static MissionQuery getInstance(Context context) {
        MissionQuery localInstance = missionQuery;
        if (localInstance == null) {
            synchronized (EmployeeQuery.class) {
                localInstance = missionQuery;
                if (localInstance == null) {
                    localInstance = missionQuery = new MissionQuery(context);
                }
            }
        }
        return localInstance;
    }

    public JSONArray getMissions() {
        ensureCacheData();
        return cacheData;
    }

    public ArrayList<Mission> getMissionsModel() {
        ensureCacheData();
        return toMissionModels(getMissions());
    }
    public ArrayList<Mission> getMissionsOfTechnician(long technicianId) {
        ensureCacheData();
        ArrayList<Mission> missions=toMissionModels(getMissions());
        ArrayList<Mission> result=new ArrayList<>();
        for (int i = 0; i < missions.size(); i++) {
            if (missions.get(i).getTechnicianId()==technicianId)
                result.add(missions.get(i));
        }
        return result;
    }

    public void put(JSONObject jsonObject) {
        ensureCacheData();
        try {
            cacheData = put(jsonObject, MISSION_QUERY);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(long id) {
        ensureCacheData();
        try {
            cacheData = delete(id, MISSION_QUERY);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteMissionsOfATechnician(long technicianId) {
        ensureCacheData();
        try {
            for (int i = 0; i < cacheData.length(); i++) {
                Mission mission = Mission.toModel(cacheData.optJSONObject(i));
                if (mission.getTechnicianId() == technicianId) {
                    cacheData = delete(mission.getId(), MISSION_QUERY);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public Mission getMission(long id) {
        ensureCacheData();
        ArrayList<Mission> missions = toMissionModels(cacheData);
        for (int i = 0; i < missions.size(); i++) {
            if (missions.get(i).getId() == id)
                return missions.get(i);
        }
        return null;
    }
    public static ArrayList<Mission> toMissionModels(JSONArray jsonArray) {
        ArrayList<Mission> missionModels = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++)
            missionModels.add(Mission.toModel(jsonArray.optJSONObject(i)));
        return missionModels;
    }

    private void ensureCacheData() {
        if (cacheData == null || cacheData.length() == 0) {
            try {
                cacheData = new JSONArray(readFile(MISSION_QUERY));
            } catch (JSONException e) {
                cacheData = new JSONArray();
            }
        }
    }
}
