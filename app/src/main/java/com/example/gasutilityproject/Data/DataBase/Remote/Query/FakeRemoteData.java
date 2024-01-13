package com.example.gasutilityproject.Data.DataBase.Remote.Query;

import android.content.Context;

import com.example.gasutilityproject.Data.Model.Model;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FakeRemoteData {
    private String cachePath;

    public FakeRemoteData(Context context) {
        cachePath = context.getCacheDir().getAbsolutePath() + "/remoteDatabase";
        new File(cachePath).mkdirs();
        if (!(new File(cachePath + "/Employee.txt").exists())) {
            String admin = "{\n" +
                    "    \"id\": 195524555541,\n" +
                    "    \"firstname\": \"Sarina\",\n" +
                    "    \"lastname\": \"Hemmatpour\",\n" +
                    "    \"nationalIdNumber\": \"1743434421\",\n" +
                    "    \"phoneNumber\": \"09302052301\",\n" +
                    "    \"emailAddress\": \"hemmatpour.sarina@gmail.com\",\n" +
                    "    \"personnelIdNumber\": \"a\",\n" +
                    "    \"password\": \"a\",\n" +
                    "    \"proficiency\": \"manager\",\n" +
                    "    \"score\": \"100\",\n" +
                    "    \"jobTitle\": 0\n" +
                    "  }";

            try {
                put(new JSONObject(admin), "/Employee.txt");
            } catch (JSONException e) {}
        }
    }

    protected void writeFile(String data, String path) {
        try {
            File file = new File(cachePath + path);
            FileWriter writer = new FileWriter(file);
            writer.append(data);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected String readFile(String path) {
        String applicationText;
        InputStream inputStream;
        try {
            inputStream = new FileInputStream(cachePath + path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String receiveString = "";
            StringBuilder stringBuilder = new StringBuilder();
            while ((receiveString = bufferedReader.readLine()) != null)
                stringBuilder.append(receiveString);
            applicationText = stringBuilder.toString();

            inputStream.close();

            return applicationText;

        } catch (Exception ignored) {
            return "";
        }
    }

    protected void put(Model model, String CACHE_PATH) throws JSONException {
        String data = readFile(CACHE_PATH);
        JSONArray cacheData;
        if (data.isEmpty())
            cacheData = new JSONArray();
        else
            cacheData = new JSONArray(data);
        if (model.getId() == 0) {
            model.createId();
            JSONObject jsonObject = model.toJSON();
            cacheData.put(jsonObject);
            writeFile(cacheData.toString(), CACHE_PATH);
        } else {
            for (int i = 0; i < cacheData.length(); i++) {
                if (cacheData.optJSONObject(i).optLong("id") == model.getId()) {
                    try {
                        cacheData.put(i, model.toJSON());
                        writeFile(cacheData.toString(), CACHE_PATH);
                    } catch (Exception ignored) {
                    }
                    return;
                }
            }
        }
    }

    protected JSONArray put(JSONObject jsonObject, String CACHE_PATH) throws JSONException {
        String data = readFile(CACHE_PATH);
        JSONArray cacheData;
        if (data.isEmpty())
            cacheData = new JSONArray();
        else
            cacheData = new JSONArray(data);

        for (int i = 0; i < cacheData.length(); i++) {
            if (cacheData.optJSONObject(i).optLong("id") == jsonObject.optLong("id")) {
                try {
                    cacheData.put(i, jsonObject);
                    writeFile(cacheData.toString(), CACHE_PATH);
                } catch (Exception ignored) {
                    return cacheData;
                }
                return cacheData;
            }
        }
        cacheData.put(jsonObject);
        writeFile(cacheData.toString(), CACHE_PATH);
        return cacheData;
    }

    protected JSONArray delete(long id, String CACHE_PATH) throws JSONException {
        JSONArray cacheData = new JSONArray(readFile(CACHE_PATH));
        for (int i = 0; i < cacheData.length(); i++) {
            long objectId = cacheData.optJSONObject(i).optLong("id");
            if (objectId == id) {
                cacheData.remove(i);
                writeFile(cacheData.toString(), CACHE_PATH);
                return cacheData;
            }
        }
        return cacheData;
    }

}
