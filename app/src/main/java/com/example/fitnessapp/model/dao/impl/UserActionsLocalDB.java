package com.example.fitnessapp.model.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fitnessapp.model.DBHelper;
import com.example.fitnessapp.model.dao.ConectionDB;
import com.example.fitnessapp.model.entities.User;

public class UserActionsLocalDB implements ConectionDB {

    private SQLiteDatabase database;
    private Context context;

    public UserActionsLocalDB(Context context) {
        this.database = new DBHelper(context).getWritableDatabase();
        this.context = context;
    }

    public void add(User user) {
        ContentValues cv = new ContentValues();
        cv.put("name", user.getName());
        cv.put("surname", user.getSurname());
        cv.put("email", user.getEmail());
        this.database.insert("user", null, cv);
    }

    public void remove(User user) {
        this.database.delete("user", "id = ?",
                new String[] {user.getId()});
    }

    public void update(User user) {
        ContentValues cv = new ContentValues();
        cv.put("name", user.getName());
        cv.put("surname", user.getName());
        cv.put("email", user.getEmail());
        this.database.update("user", cv, "id = ?",
                new String[] {user.getId()});
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
        String idUser;
        if(c.moveToFirst()) {
            idUser = Long.toString(c.getLong(0));
            user = new User(idUser, c.getString(1),
                    c.getString(2), c.getString(3));
            WorkoutActionsLocalDB workoutDAO = new WorkoutActionsLocalDB(this.context);
            user.setJournalWorkout(workoutDAO.getByIdUser(idUser));
        }
        System.out.println("!!!!!!");
        return user;
    }

    public User getById(String id) {
        @SuppressLint("Recycle") Cursor c = this.database
                .query("user", null, "id = ?",
                        new String[]{id},
                        null, null, null);
        User user = null;
        if (c.moveToFirst()) {
            user = new User(id, c.getString(1),
                    c.getString(2), c.getString(3));
            WorkoutActionsLocalDB workoutDAO = new WorkoutActionsLocalDB(this.context);
            user.setJournalWorkout(workoutDAO.getByIdUser(id));
        }
        return user;
    }

    @Override
    public void disconnect() {
        this.database.close();
    }

}