package com.example.gasutilityproject.Data.DataBase.Remote;

import android.content.Context;

import com.example.gasutilityproject.Data.DataBase.Remote.Query.EmployeeQuery;
import com.example.gasutilityproject.Data.DataBase.Remote.Query.MissionQuery;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.Data.Model.Mission;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EmitManager {
    //fake dataBase
    private static volatile EmitManager emitManager;

    public static EmitManager getInstance() {
        EmitManager localInstance = emitManager;
        if (localInstance == null) {
            synchronized (EmitManager.class) {
                localInstance = emitManager;
                if (localInstance == null) {
                    localInstance = emitManager = new EmitManager();
                }
            }
        }
        return localInstance;
    }

    public void emitLogin(Context context, RequestListener requestListener, String username, String password) {
        //response[0]: user existence
        //response[1]: user

        Object[] response = new Object[2];
        response[0] = false;

        JSONArray employees = EmployeeQuery.getInstance(context).getEmployees();
        for (int i = 0; i < employees.length(); i++) {
            if (employees.optJSONObject(i).optString("password").trim().equals(password) &&
                    employees.optJSONObject(i).optString("personnelIdNumber").trim().equals(username)) {
                response[0] = true;
                response[1] = employees.optJSONObject(i);
            }
        }
        try {
            requestListener.onSuccess(response);
        } catch (Error error) {
            requestListener.onFailure(error);
        }
    }

    public void emitAddTechnician(Context context, RequestListener requestListener, JSONObject newTechnician, boolean isNew) {
        if (isNew) {
            long id = System.currentTimeMillis();
            String personnelId = "Tech" + id;
            int jobTitle = 1;
            int score = 0;
            try {
                newTechnician.put("id", id);
                newTechnician.put("personnelIdNumber", personnelId);
                newTechnician.put("score", score);
                newTechnician.put("jobTitle", jobTitle);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        EmployeeQuery.getInstance(context).put(newTechnician);
        if (requestListener != null) {
            try {
                requestListener.onSuccess(newTechnician);
            } catch (Error error) {
                requestListener.onFailure(error);
            }
        }
    }

    public void emitDeleteTechnician(Context context, RequestListener requestListener, long id) {
        EmployeeQuery.getInstance(context).delete(id);
        if (requestListener != null) {
            try {
                requestListener.onSuccess();
            } catch (Error error) {
                requestListener.onFailure(error);
            }
        }
    }

    public void emitAddMission(Context context, RequestListener requestListener, JSONObject newMission, boolean isNew) {
        if (isNew) {
            long id = System.currentTimeMillis();
            try {
                newMission.put("id", id);
                newMission.put("technicianLocation", "-1:-1");
                newMission.put("status", 0); //pending
                newMission.put("technicianReport",0);
                newMission.put("technicianStatus",0);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        MissionQuery.getInstance(context).put(newMission);
        if (requestListener != null) {
            try {
                requestListener.onSuccess();
            } catch (Error error) {
                requestListener.onFailure(error);
            }
        }
    }

    public void emitDeleteMission(Context context, RequestListener requestListener, long id) {
        MissionQuery.getInstance(context).delete(id);
        if (requestListener != null) {
            try {
                requestListener.onSuccess();
            } catch (Error error) {
                requestListener.onFailure(error);
            }
        }
    }

    public void emitUpdateMissionStatus(Context context, RequestListener requestListener, long id, Mission.MissionStatus status) {
        Mission mission = MissionQuery.getInstance(context).getMission(id);
        mission.setStatus(status);
        MissionQuery.getInstance(context).put(mission.toJSON());

        if (status == Mission.MissionStatus.VERIFIED) {
            Employee employee = EmployeeQuery.getInstance(context).getTechnician(mission.getTechnicianId());
            employee.setScore(employee.getScore() + 1);
            EmployeeQuery.getInstance(context).put(employee.toJSON());
        }

        if (requestListener != null) {
            try {
                requestListener.onSuccess();
            } catch (Error error) {
                requestListener.onFailure(error);
            }
        }
    }

    public void emitGetUserData(Context context, RequestListener requestListener, String personnelId) {
        Object[] response = new Object[1];
        JSONArray employees = EmployeeQuery.getInstance(context).getEmployees();
        for (int i = 0; i < employees.length(); i++) {
            if (employees.optJSONObject(i).optString("personnelIdNumber").trim().equals(personnelId)) {
                response[0] = employees.optJSONObject(i);
            }


        }
        try {
            requestListener.onSuccess(response);
        } catch (Error error) {
            requestListener.onFailure(error);
        }
    }

    public interface RequestListener {
        void onSuccess(Object... response);

        void onFailure(Error error);
    }
}
