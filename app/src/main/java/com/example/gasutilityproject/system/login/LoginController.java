package com.example.gasutilityproject.system.login;

import com.example.gasutilityproject.Data.Account;
import com.example.gasutilityproject.Data.DataBase.Remote.EmitManager;
import com.example.gasutilityproject.Data.Model.Employee;

import org.json.JSONObject;

public class LoginController {
    private LoginFragment loginFragment;

    public LoginController(LoginFragment loginFragment) {
        this.loginFragment = loginFragment;
    }

    public void onBtnLoginClicked(String username, String password) {
        EmitManager.getInstance().emitLogin(loginFragment.activity, onLogin, username.trim(), password.trim());

    }

    private EmitManager.RequestListener onLogin = new EmitManager.RequestListener() {
        @Override
        public void onSuccess(Object... response) {
            boolean userExistence = (boolean) response[0];
            if (userExistence) {
                Employee employee = Employee.toModel((JSONObject) response[1]);
                Account.getInstance().setUser(employee);
                Account.setIsAdmin(employee.getJobTitle() == Employee.JobTitle.ADMIN);
                loginFragment.login(employee.getJobTitle() == Employee.JobTitle.ADMIN);
            } else {
                loginFragment.showErrorMessage("نام کاربری یا رمز عبور اشتباه است.");
                loginFragment.setEditTextError();
            }
        }

        @Override
        public void onFailure(Error error) {

        }
    };

}
