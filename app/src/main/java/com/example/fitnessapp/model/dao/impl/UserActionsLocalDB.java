package com.example.fitnessapp.model.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fitnessapp.model.DBHelper;
import com.example.fitnessapp.model.dao.ConectionDB;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.model.entities.Workout;

public class UserActionsLocalDB implements ConectionDB {

    private SQLiteDatabase database;
    private Context context;

    public UserActionsLocalDB(Context context) {
        this.database = new DBHelper(context).getWritableDatabase();
        this.context = context;
    }

    public void add(User user) {
        ContentValues cv = new ContentValues();
        cv.put("email", user.getEmail());
        cv.put("name", user.getName());
        cv.put("surname", user.getSurname());
        this.database.insert("user", null, cv);
        WorkoutActionsLocalDB workoutActionsLocalDB = new WorkoutActionsLocalDB(context);
        for (Workout workout : user.getJournal()) {
            workoutActionsLocalDB.add(workout);
        }
        workoutActionsLocalDB.disconnect();
    }

    public void remove(User user) {
        this.database.delete("user", "email = ?",
                new String[] {user.getEmail()});
    }

    public void update(User user) {
        ContentValues cv = new ContentValues();
        cv.put("email", user.getEmail());
        cv.put("name", user.getName());
        cv.put("surname", user.getName());
        this.database.update("user", cv, "email = ?",
                new String[] {user.getEmail()});

    }

    public User getByEmail(String email) {
        @SuppressLint("Recycle") Cursor c = this.database
                .query("user",
                        null,
                        "email = ?",
                        new String[] {email},
                        null,
                        null,
                        null);
        User user = null;
        if(c.moveToFirst()) {

            int emailColIndex = c.getColumnIndex("email");
            int nameColIndex = c.getColumnIndex("name");
            int surnameColIndex = c.getColumnIndex("surname");

            user = new User(c.getString(emailColIndex), c.getString(nameColIndex),
                    c.getString(surnameColIndex));

            WorkoutActionsLocalDB workoutDAO = new WorkoutActionsLocalDB(this.context);
            user.setJournalWorkout(workoutDAO.getByIdUser(email));
        }
        return user;
    }

    @Override
    public void disconnect() {
        this.database.close();
    }

}