package com.example.fitnessapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private final static String createUser =
            "CREATE TABLE user (" +
                    "email TEXT PRIMARY KEY," +
                    "name TEXT," +
                    "surname TEXT" +
                    ")";
    private final static String createWorkout =
            "CREATE TABLE workout (" +
                    "id TEXT PRIMARY KEY," +
                    "date TEXT," +
                    "id_user TEXT, " +
                    "FOREIGN KEY (id_user) REFERENCES user (id)" +
                    ")";
    private final static String createNote =
            "CREATE TABLE note (" +
                    "id TEXT PRIMARY KEY," +
                    "record TEXT," +
                    "id_workout TEXT," +
                    "id_exercise TEXT," +
                    "FOREIGN KEY (id_workout) REFERENCES workout (id)," +
                    "FOREIGN KEY (id_exercise) REFERENCES exercise (id)" +
                    ")";
    private final static String createExercise =
            "CREATE TABLE exercise (" +
                    "id TEXT PRIMARY KEY," +
                    "name TEXT," +
                    "part_of_body TEXT," +
                    "description TEXT" +
                    ")";
    private final static String createSettings =
            "CREATE TABLE settings (" +
                    "id INTEGER PRIMARY KEY," +
                    "email TEXT," +
                    "starus INTEGER" +
                    ")";

    public DBHelper(Context context) {
        super(context, "fitnessDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createUser);
        db.execSQL(createWorkout);
        db.execSQL(createNote);
        db.execSQL(createExercise);
        db.execSQL(createSettings);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}