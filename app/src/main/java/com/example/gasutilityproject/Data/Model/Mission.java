package com.example.gasutilityproject.Data.Model;


import com.example.gasutilityproject.Data.DataBase.Remote.Query.EmployeeQuery;

import org.json.JSONException;
import org.json.JSONObject;

public class Mission extends Model {

    public enum MissionStatus {PENDING, VERIFIED, UNVERIFIED}

    private String title, description;
    private String location;
    private int deadlineInDays;
    private long technicianId;
    private MissionStatus status = MissionStatus.PENDING;
    private int dueDateInDays;
    private String technicianLocation;
    private String technicianReport = "";
    private String technicianFailedMissionReason = "";
    private String technicianReportPhoto = "";
    private int technicianStatus = 0; //0=pending //1=Done //2=notDone
    private int extraTimeRequestInDays =0;
    private int doneDate;

    public int getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(int doneDate) {
        this.doneDate = doneDate;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", getTitle());
            jsonObject.put("description", getDescription());
            jsonObject.put("location", getLocation());
            jsonObject.put("deadlineInDays", getDeadlineInDays());
            jsonObject.put("technicianId", getTechnicianId());
            jsonObject.put("technicianLocation", getTechnicianLocation());
            int st = 0;
            if (getStatus() == MissionStatus.VERIFIED)
                st = 1;
            if (getStatus() == MissionStatus.UNVERIFIED)
                st = 2;
            jsonObject.put("status", st);
            jsonObject.put("dueDateInDays", getDueDateInDays());
            jsonObject.put("id", getId());
            jsonObject.put("technicianReport",getTechnicianReport());
            jsonObject.put("technicianFailedMissionReason",getTechnicianFailedMissionReason());
            jsonObject.put("technicianReportPhoto",getTechnicianReportPhoto());
            jsonObject.put("technicianStatus",getTechnicianStatus());
            jsonObject.put("extraTimeRequestInDays",getExtraTimeRequestInDays());
            jsonObject.put("doneDate",getDoneDate());
            return jsonObject;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static Mission toModel(JSONObject jsonObject) {
        Mission mission = new Mission();

        mission.setId(jsonObject.optLong("id"));
        mission.setTitle(jsonObject.optString("title"));
        mission.setDescription(jsonObject.optString("description"));
        mission.setLocation(jsonObject.optString("location"));
        mission.setDeadlineInDays(jsonObject.optInt("deadlineInDays"));
        mission.setDueDateInDays(jsonObject.optInt("dueDateInDays"));
        mission.setStatus(MissionStatus.values()[jsonObject.optInt("status")]);
        mission.setTechnicianId(jsonObject.optLong("technicianId"));
        mission.setTechnicianLocation(jsonObject.optString("technicianLocation"));
        mission.setTechnicianReport(jsonObject.optString("technicianReport"));
        mission.setTechnicianFailedMissionReason(jsonObject.optString("technicianFailedMissionReason"));
        mission.setTechnicianReportPhoto(jsonObject.optString("technicianReportPhoto"));
        mission.setTechnicianStatus(jsonObject.optInt("technicianStatus"));
        mission.setExtraTimeRequestInDays(jsonObject.optInt("extraTimeRequestInDays"));
        mission.setDoneDate(jsonObject.optInt("doneDate"));

        return mission;
    }

    public int getTechnicianStatus() {
        return technicianStatus;
    }

    public void setTechnicianStatus(int technicianStatus) {
        this.technicianStatus = technicianStatus;
    }

    public String getTechnicianReport() {
        return technicianReport;
    }

    public void setTechnicianReport(String technicianReport) {
        this.technicianReport = technicianReport;
    }

    public String getTechnicianFailedMissionReason() {
        return technicianFailedMissionReason;
    }

    public void setTechnicianFailedMissionReason(String technicianFailedMissionReason) {
        this.technicianFailedMissionReason = technicianFailedMissionReason;
    }

    public String getTechnicianReportPhoto() {
        return technicianReportPhoto;
    }

    public void setTechnicianReportPhoto(String technicianReportPhoto) {
        this.technicianReportPhoto = technicianReportPhoto;
    }

    public int getExtraTimeRequestInDays() {
        return extraTimeRequestInDays;
    }

    public void setExtraTimeRequestInDays(int extraTimeRequestInDays) {
        this.extraTimeRequestInDays = extraTimeRequestInDays;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public int getDeadlineInDays() {
        return deadlineInDays;
    }

    public long getTechnicianId() {
        return technicianId;
    }

    public String getTechnicianLocation() {
        return technicianLocation;
    }

    public MissionStatus getStatus() {
        return status;
    }

    public int getDueDateInDays() {
        return dueDateInDays;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDeadlineInDays(int deadlineInDays) {
        this.deadlineInDays = deadlineInDays;
    }

    public void setTechnicianId(long technicianId) {
        this.technicianId = technicianId;
    }

    public void setTechnicianLocation(String technicianLocation) {
        this.technicianLocation = technicianLocation;
    }

    public void setStatus(MissionStatus status) {
        this.status = status;
    }

    public void setDueDateInDays(int dueDateInDays) {
        this.dueDateInDays = dueDateInDays;
    }

}
