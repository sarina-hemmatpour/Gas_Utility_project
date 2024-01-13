package com.example.gasutilityproject.Data;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.StaticFields.ShPKey;

public class Account {
    private static Account account;
    private static boolean isAdmin;

    public static Account getInstance() {
        Account localInstance = account;
        if (localInstance == null) {
            synchronized (Account.class) {
                localInstance = account;
                if (localInstance == null) {
                    localInstance = account = new Account();
                }
            }
        }
        return localInstance;
    }
    private Employee user;

    public void setUser(Employee user) {
        this.user = user;
        AppLoader.editor.putString(ShPKey.ACCOUNT, user.getPersonnelIdNumber()).apply();
    }
    public Employee getUser() {
        return user;
    }

    public boolean isIsAdmin() {
        return user.getJobTitle() == Employee.JobTitle.ADMIN;
    }

    public static void setIsAdmin(boolean isAdmin) {
        Account.isAdmin = isAdmin;
    }

    public void exit() {
        this.user = null;
        AppLoader.editor.putString(ShPKey.ACCOUNT, "").apply();
    }
}
