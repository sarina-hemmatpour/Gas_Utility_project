package com.example.gasutilityproject.Data.Model;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

public class Employee extends Model {

    private String firstname, lastname, nationalIdNumber, phoneNumber,
            emailAddress, personnelIdNumber, password, proficiency;
    private int score;
    private JobTitle jobTitle;


    public enum JobTitle {
        ADMIN, TECHNICIAN
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getNationalIdNumber() {
        return nationalIdNumber;
    }

    public void setNationalIdNumber(String nationalIdNumber) {
        this.nationalIdNumber = nationalIdNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPersonnelIdNumber() {
        return personnelIdNumber;
    }

    public void setPersonnelIdNumber(String personnelIdNumber) {
        this.personnelIdNumber = personnelIdNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProficiency() {
        return proficiency;
    }

    public void setProficiency(String proficiency) {
        this.proficiency = proficiency;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public JobTitle getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(JobTitle jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailAddress", getEmailAddress());
            jsonObject.put("firstname", getFirstname());
            jsonObject.put("lastname", getLastname());
            jsonObject.put("nationalIdNumber", getNationalIdNumber());
            jsonObject.put("phoneNumber", getPhoneNumber());
            jsonObject.put("personnelIdNumber", getPersonnelIdNumber());
            jsonObject.put("password", getPassword());
            jsonObject.put("proficiency", getProficiency());
            jsonObject.put("score", getScore());
            jsonObject.put("jobTitle", getJobTitle() == JobTitle.TECHNICIAN ? 1 : 0);
            jsonObject.put("id", getId());
            return jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public static Employee toModel(JSONObject jsonObject) {
        Employee employee = new Employee();
        employee.setEmailAddress(jsonObject.optString("emailAddress"));
        employee.setFirstname(jsonObject.optString("firstname"));
        employee.setLastname(jsonObject.optString("lastname"));
        employee.setNationalIdNumber(jsonObject.optString("nationalIdNumber"));
        employee.setPhoneNumber(jsonObject.optString("phoneNumber"));
        employee.setPersonnelIdNumber(jsonObject.optString("personnelIdNumber"));
        employee.setPassword(jsonObject.optString("password"));
        employee.setProficiency(jsonObject.optString("proficiency"));
        employee.setScore(jsonObject.optInt("score"));
        employee.setJobTitle(JobTitle.values()[jsonObject.optInt("jobTitle")]);
        employee.setId(jsonObject.optLong("id"));
        return employee;
    }
}
