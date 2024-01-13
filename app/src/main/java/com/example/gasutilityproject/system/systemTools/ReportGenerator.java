package com.example.gasutilityproject.system.systemTools;

import com.example.gasutilityproject.AppLoader;
import com.example.gasutilityproject.Data.DataBase.Remote.Query.EmployeeQuery;
import com.example.gasutilityproject.Data.DataBase.Remote.Query.MissionQuery;
import com.example.gasutilityproject.Data.Model.Employee;
import com.example.gasutilityproject.Data.Model.Mission;
import com.google.gson.JsonArray;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ReportGenerator {

    public static void generateReport(String path) {
        JSONArray jsonArrayData = createMissionReportData();
        ExcelConverter.convertJsonToExcel(jsonArrayData, path);
    }

    private static JSONArray createMissionReportData() {
        ArrayList<Mission> missions = MissionQuery.getInstance(AppLoader.getAppContext()).getMissionsModel();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < missions.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            try {
                String status = "";
                if (missions.get(i).getStatus() == Mission.MissionStatus.VERIFIED)
                    status = "تایید شده";
                else if (missions.get(i).getStatus() == Mission.MissionStatus.UNVERIFIED)
                    status = "تایید نشده";
                else if (missions.get(i).getStatus() == Mission.MissionStatus.PENDING)
                    status = "در انظار تایید";
                String technicianStatus = "";
                switch (missions.get(i).getTechnicianStatus()) {
                    case 0:
                        technicianStatus = "در دست انجام";
                        break;
                    case 1:
                        technicianStatus = "انجام شده";
                        break;
                    case 2:
                        technicianStatus = "انجام نشده";
                        break;
                }
                jsonObject.put("نام ماموریت", missions.get(i).getTitle());
                Employee employee = EmployeeQuery.getInstance(AppLoader.getAppContext()).getTechnician(missions.get(i).getTechnicianId());
                jsonObject.put("نام مامور", employee.getFirstname() + " " + employee.getLastname());
                jsonObject.put("وضعیت ماموریت", technicianStatus);
                jsonObject.put("نظر مدیر", status);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }


    public static class ExcelConverter {

        public static void convertJsonToExcel(JSONArray jsonArrayData, String filePath) {
            try {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Sheet1");

                // Create header row
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < jsonArrayData.getJSONObject(0).length(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(jsonArrayData.getJSONObject(0).names().getString(i));
                }

                // Populate data rows
                for (int i = 0; i < jsonArrayData.length(); i++) {
                    Row row = sheet.createRow(i + 1);
                    JSONObject jsonObject = jsonArrayData.getJSONObject(i);

                    for (int j = 0; j < jsonObject.length(); j++) {
                        Cell cell = row.createCell(j);
                        cell.setCellValue(jsonObject.getString(jsonObject.names().getString(j)));
                    }
                }

                // Write the workbook to a file
                try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                    workbook.write(fileOut);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Close the workbook
                workbook.close();

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}
