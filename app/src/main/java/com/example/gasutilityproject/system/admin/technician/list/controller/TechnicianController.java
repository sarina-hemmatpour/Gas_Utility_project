package com.example.gasutilityproject.system.admin.technician.list.controller;

import com.example.gasutilityproject.Data.DataBase.Remote.Query.EmployeeQuery;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.system.admin.technician.list.view.TechnicianFragment;

import java.util.ArrayList;

public class TechnicianController {
    private TechnicianFragment fragment;

    public TechnicianController(TechnicianFragment fragment) {
        this.fragment = fragment;
    }
    public ArrayList<Employee> provideTechniciansList(){
        return EmployeeQuery.getInstance(fragment.activity).getTechnicians();
    }
}
