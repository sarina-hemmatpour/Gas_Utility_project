package com.example.gasutilityproject.Data.Model;

import org.json.JSONObject;

public abstract class Model {
    protected long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void createId() {
        id = System.currentTimeMillis();
    }

    public abstract JSONObject toJSON();
}
