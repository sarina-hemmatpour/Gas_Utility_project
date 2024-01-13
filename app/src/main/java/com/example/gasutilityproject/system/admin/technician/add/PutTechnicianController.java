package com.example.gasutilityproject.system.admin.technician.add;


import com.example.gasutilityproject.Data.DataBase.Remote.EmitManager;
import com.example.gasutilityproject.Data.Model.Employee;

import org.json.JSONException;
import org.json.JSONObject;

public class PutTechnicianController {
    private PutTechnicianFragment fragment;

    public PutTechnicianController(PutTechnicianFragment fragment) {
        this.fragment = fragment;
    }

    protected void addNewTechnician(Employee employee, String[] texts) {
        JSONObject jsonObject = employee == null ? (new JSONObject()) : employee.toJSON();
        try {
            jsonObject.put("firstname", texts[0]);
            jsonObject.put("lastname", texts[1]);
            jsonObject.put("nationalIdNumber", texts[2]);
            jsonObject.put("proficiency", texts[3]);
            jsonObject.put("phoneNumber", texts[4]);
            jsonObject.put("emailAddress", texts[5]);
            jsonObject.put("password", texts[6]);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        EmitManager.getInstance().emitAddTechnician(fragment.activity, new EmitManager.RequestListener() {
            @Override
            public void onSuccess(Object... response) {
                if (fragment.listener != null)
                    fragment.listener.onAddBtnClicked(Employee.toModel((JSONObject) response[0]));
            }

            @Override
            public void onFailure(Error error) {

            }
        }, jsonObject, employee == null);
    }
}
