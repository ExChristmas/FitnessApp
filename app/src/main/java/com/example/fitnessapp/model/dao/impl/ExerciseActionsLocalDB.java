package com.example.fitnessapp.model.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnessapp.model.DBHelper;
import com.example.fitnessapp.model.dao.ConectionDB;
import com.example.fitnessapp.model.entities.Exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExerciseActionsLocalDB implements ConectionDB {

    private SQLiteDatabase database;

    public ExerciseActionsLocalDB(Context context) {
        this.database = new DBHelper(context).getWritableDatabase();
    }

    public void add(Exercise exercise) {
        ContentValues cv = new ContentValues();
        cv.put("id", exercise.getId());
        cv.put("name", exercise.getName());
        cv.put("description", exercise.getDescription());
        cv.put("part_of_body", exercise.getPartOfBody());
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
        cv.put("part_of_body", exercise.getPartOfBody());
        this.database.update("exercise", cv,"id = ?",
                new String[] {exercise.getId()});
    }

    public Exercise getByName(String name) {
        @SuppressLint("Recycle") Cursor c = this.database
                .query("exercise",
                        null,
                        "name = ?",
                        new String[] {name},
                        null,
                        null,
                        null);
        Exercise exercise = null;

        int idColumn;
        int nameColumn;
        int partOfBodyColumn;
        int descriptionColumn;

        if (c.moveToFirst()) {

            idColumn = c.getColumnIndex("id");
            nameColumn = c.getColumnIndex("name");
            partOfBodyColumn = c.getColumnIndex("part_of_body");
            descriptionColumn = c.getColumnIndex("description");

            exercise = new Exercise(c.getString(idColumn), c.getString(nameColumn),
                    c.getString(partOfBodyColumn), c.getString(descriptionColumn));
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

        int idColumn;
        int nameColumn;
        int partOfBodyColumn;
        int descriptionColumn;

        if (c.moveToFirst()) {

            idColumn = c.getColumnIndex("id");
            nameColumn = c.getColumnIndex("name");
            partOfBodyColumn = c.getColumnIndex("part_of_body");
            descriptionColumn = c.getColumnIndex("description");

            exercise = new Exercise(c.getString(idColumn), c.getString(nameColumn),
                    c.getString(partOfBodyColumn), c.getString(descriptionColumn));
        }
        return exercise;
    }

    public List<Exercise> getAll() {
        @SuppressLint("Recycle") Cursor c = this.database
                .query("exercise",
                null,
                null,
                null,
                null,
                null,
                null);
        List<Exercise> exercises = new ArrayList<>();

        int idColumn;
        int nameColumn;
        int partOfBodyColumn;
        int descriptionColumn;

        while (c.moveToNext()) {

            idColumn = c.getColumnIndex("id");
            nameColumn = c.getColumnIndex("name");
            partOfBodyColumn = c.getColumnIndex("part_of_body");
            descriptionColumn = c.getColumnIndex("description");

            Exercise exercise = new Exercise(c.getString(idColumn), c.getString(nameColumn),
                    c.getString(partOfBodyColumn), c.getString(descriptionColumn));

            exercises.add(exercise);
        }
        return Objects.requireNonNull(exercises).isEmpty() ? null : exercises;
    }

    public LiveData<List<Exercise>> getByPartOfBody(String partOfBody) {
        @SuppressLint("Recycle") Cursor c = this.database.query("exercise",
                null,
                "part_of_body = ?",
                new String[] {partOfBody},
                null,
                null,
                null);
        MutableLiveData<List<Exercise>> listLiveData = new MutableLiveData<>();
        List<Exercise> exercises = new ArrayList<>();

        int idColumn;
        int nameColumn;
        int partOfBodyColumn;
        int descriptionColumn;

        while (c.moveToNext()) {

            idColumn = c.getColumnIndex("id");
            nameColumn = c.getColumnIndex("name");
            partOfBodyColumn = c.getColumnIndex("part_of_body");
            descriptionColumn = c.getColumnIndex("description");

            Exercise exercise = new Exercise(c.getString(idColumn), c.getString(nameColumn),
                    c.getString(partOfBodyColumn), c.getString(descriptionColumn));
            exercises.add(exercise);
        }
        listLiveData.setValue(exercises);
        return Objects.requireNonNull(listLiveData.getValue()).isEmpty() ? null : listLiveData;
    }

    @Override
    public void disconnect() {
        this.database.close();
    }
}