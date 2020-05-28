package com.example.fitnessapp.model.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fitnessapp.model.DBHelper;
import com.example.fitnessapp.model.dao.ConectionDB;
import com.example.fitnessapp.model.entities.Settings;

public class SettingsActionsLocalDB implements ConectionDB {

    private SQLiteDatabase database;

    public SettingsActionsLocalDB(Context context) {
        this.database = new DBHelper(context).getWritableDatabase();
    }

    public void add() {
        ContentValues cv = new ContentValues();
        cv.put("id", 0);
        cv.put("email", "avramenkoav98.mentore@yandex.ru");
        cv.put("status", 1);
        this.database.insert("settings", null, cv);
    }

    public void update(Settings settings) {
            ContentValues cv = new ContentValues();
            cv.put("id", 0);
            cv.put("email", settings.getEmail());
            cv.put("status", settings.getStatus());
            this.database.update("settings", cv, "id = ?",
                    new String[] {"0"});
    }

    public Settings getRecord() {
        @SuppressLint("Recycle") Cursor c = this.database.query("settings",
                null,
                "id = ?",
                new String[] {"0"},
                null,
                null,
                null);

        Settings settings = null;
        if(c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("id");
            int emailColIndex = c.getColumnIndex("email");
            int statusColIndex = c.getColumnIndex("status");

            settings = new Settings(c.getInt(idColIndex), c.getString(emailColIndex),
                    c.getInt(statusColIndex));
        }
        return settings;
    }

    @Override
    public void disconnect() {
        database.close();
    }
}