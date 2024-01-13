package com.example.gasutilityproject.Data.DataBase.Remote.Query;

import android.content.Context;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.Model.Employee;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EmployeeQuery extends FakeRemoteData {
    private volatile static EmployeeQuery employeeQuery;
    private final static String EMPLOYEE_DATABASE = "/Employee.txt";
    private JSONArray cacheData = new JSONArray();

    public EmployeeQuery(Context context) throws JSONException {
        super(context);
    }

    public static EmployeeQuery getInstance(Context context) {
        EmployeeQuery localInstance = employeeQuery;
        if (localInstance == null) {
            synchronized (EmployeeQuery.class) {
                localInstance = employeeQuery;
                if (localInstance == null) {
                    try {
                        localInstance = employeeQuery = new EmployeeQuery(context);
                    } catch (JSONException e) {

                    }
                }
            }
        }
        return localInstance;
    }

    public JSONArray getEmployees() {
        ensureCacheData();
        return cacheData;
    }

    public ArrayList<Employee> getTechnicians() {
        ensureCacheData();
        ArrayList<Employee> employees = toEmployeeModels(cacheData);
        ArrayList<Employee> result = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getJobTitle() == Employee.JobTitle.TECHNICIAN)
                result.add(employees.get(i));
        }
        return result;
    }

    public Employee getTechnician(long id) {
        ensureCacheData();
        ArrayList<Employee> employees = toEmployeeModels(cacheData);
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id)
                return employees.get(i);
        }
        return null;
    }

    public void put(JSONObject jsonObject) {
        ensureCacheData();
        try {
            cacheData = put(jsonObject, EMPLOYEE_DATABASE);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(long id) {
        ensureCacheData();
        try {
            cacheData = delete(id, EMPLOYEE_DATABASE);
            MissionQuery.getInstance(AppLoader.getAppContext()).deleteMissionsOfATechnician(id);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Employee> toEmployeeModels(JSONArray jsonArray) {
        ArrayList<Employee> employeeModels = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++)
            employeeModels.add(Employee.toModel(jsonArray.optJSONObject(i)));
        return employeeModels;
    }

    private void ensureCacheData() {
        if (cacheData == null || cacheData.length() == 0) {
            try {
                cacheData = new JSONArray(readFile(EMPLOYEE_DATABASE));
            } catch (JSONException e) {
                cacheData = new JSONArray();
            }
        }
    }

}
