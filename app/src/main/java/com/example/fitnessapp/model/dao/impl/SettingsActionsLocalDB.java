package com.example.fitnessapp.model.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.fitnessapp.model.DBHelper;
import com.example.fitnessapp.model.dao.ConectionDB;
import com.example.fitnessapp.model.entities.Settings;

public class SettingsActionsLocalDB implements ConectionDB {

    private SQLiteDatabase database;

    public SettingsActionsLocalDB(Context context) {
        this.database = new DBHelper(context).getWritableDatabase();
    }

    public void update(Settings settings) {
            ContentValues cv = new ContentValues();
            cv.put("id", 0);
            cv.put("email", settings.getEmail());
            cv.put("status", settings.getStatus());
            this.database.update("user", cv, "id = ?",
                    new String[] {"0"});
    }

    @Override
    public void disconnect() {
        database.close();
    }
}
