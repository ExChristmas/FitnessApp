package com.example.fitnessapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private final static String createUser =
            "CREATE TABLE user (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "surname TEXT," +
                    "email TEXT" +
                    ");";
    private final static String createWorkout =
            "CREATE TABLE workout (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "date TEXT," +
                    "id_user INTEGER, " +
                    "FOREIGN KEY (id_user) REFERENCES user (id)" +
                    ")";
    private final static String createNote =
            "CREATE TABLE note (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "record TEXT," +
                    "id_workout INTEGER," +
                    "id_exercise INTEGER," +
                    "FOREIGN KEY (id_workout) REFERENCES workout (id)," +
                    "FOREIGN KEY (id_exercise) REFERENCES exercise (id)" +
                    ")";
    private final static String createExercise =
            "CREATE TABLE exercise (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "description TEXT" +
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
