package com.example.fitnessapp.model.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fitnessapp.model.DBHelper;
import com.example.fitnessapp.model.dao.ConectionDB;
import com.example.fitnessapp.model.entities.Exercise;

public class ExerciseActionsLocalDB implements ConectionDB {

    private SQLiteDatabase database;

    public ExerciseActionsLocalDB(Context context) {
        this.database = new DBHelper(context).getWritableDatabase();
    }

    public void add(Exercise exercise) {
        ContentValues cv = new ContentValues();
        cv.put("name", exercise.getName());
        cv.put("description", exercise.getDescription());
        this.database.insert("exercise", null, cv);
    }

    public void remove(Exercise exercise) {
        this.database.delete("exercise", "id = ?",
                new String[] {exercise.getId()});
    }

    public void update(Exercise exercise) {
        ContentValues cv = new ContentValues();
        cv.put("name", exercise.getName());
        cv.put("description", exercise.getDescription());
    }

    public Exercise getByName(String name) {
        @SuppressLint("Recycle") Cursor c = this.database
                .query("exercise",
                        null,
                        "id = ?",
                        new String[] {name},
                        null,
                        null,
                        null);
        Exercise exercise = null;
        if (c.moveToFirst()) {
            exercise = new Exercise(Long.toString(c.getLong(0)), c.getString(1),
                    c.getString(2));
        }
        return exercise;
    }

    public Exercise getById(String id) {
        @SuppressLint("Recycle") Cursor c = this.database
                .query("exercise",
                        null,
                        "id = ?",
                        new String[] {id},
                        null,
                        null,
                        null);
        Exercise exercise = null;
        if (c.moveToFirst()) {
            exercise = new Exercise(Long.toString(c.getLong(0)), c.getString(1),
                    c.getString(2));
        }
        return exercise;
    }

    @Override
    public void disconnect() {
        this.database.close();
    }
}