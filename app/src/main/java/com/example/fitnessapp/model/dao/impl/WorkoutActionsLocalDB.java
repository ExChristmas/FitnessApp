package com.example.fitnessapp.model.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fitnessapp.model.DBHelper;
import com.example.fitnessapp.model.dao.ConectionDB;
import com.example.fitnessapp.model.entities.Note;
import com.example.fitnessapp.model.entities.Workout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class WorkoutActionsLocalDB implements ConectionDB {

    private SQLiteDatabase database;
    private Context context;

    public WorkoutActionsLocalDB(Context context) {
        this.database = new DBHelper(context).getWritableDatabase();
        this.context = context;
    }

    public void add(Workout workout) {
        ContentValues cv = new ContentValues();
        String date = workout.getDate().get(Calendar.YEAR) + "-" +
                (workout.getDate().get(Calendar.MONTH)) + "-" +
                workout.getDate().get(Calendar.DAY_OF_MONTH);
        cv.put("id", workout.getId());
        cv.put("date", date);
        cv.put("id_user", workout.getIdUser());
        this.database.insert("workout", null, cv);
        NoteActionsLocalDB noteActionsLocalDB = new NoteActionsLocalDB(context);
        for(Note note : workout.getNotes()) {
            noteActionsLocalDB.add(note);
        }
        noteActionsLocalDB.disconnect();
    }

    public void remove(Workout workout) {
        NoteActionsLocalDB noteActionsLocalDB = new NoteActionsLocalDB(context);
        for (Note note : workout.getNotes()) {
            noteActionsLocalDB.remove(note);
        }
        noteActionsLocalDB.disconnect();
        this.database.delete("workout", "id = ?",
                new String[] {workout.getId()});
    }

    public void update(Workout workout) {
        ContentValues cv = new ContentValues();
        String date = workout.getDate().get(Calendar.YEAR) + "-" +
                (workout.getDate().get(Calendar.MONTH)) + "-" +
                workout.getDate().get(Calendar.DAY_OF_MONTH);
        cv.put("date", date);
        cv.put("id_user", workout.getIdUser());
        this.database.update("workout", cv, "id = ?",
                new String[] {workout.getId()});
        NoteActionsLocalDB noteActionsLocalDB = new NoteActionsLocalDB(context);
        for(Note note : workout.getNotes()) {
            noteActionsLocalDB.update(note);
        }
        noteActionsLocalDB.disconnect();
    }

    public List<Workout> getByIdUser(String idUser) {
      @SuppressLint("Recycle") Cursor c = this.database
                .query("workout",
                        null,
                        "id_user = ?",
                        new String[] {idUser},
                        null,
                        null,
                        null);
        List<Workout> workoutsList = new ArrayList<>();
        NoteActionsLocalDB noteDAO = new NoteActionsLocalDB(this.context);
        String idWorkout;

        int idColumn;
        int idDateColumn;
        int idUserColumn;

        while (c.moveToNext()) {

            idColumn = c.getColumnIndex("id");
            idDateColumn = c.getColumnIndex("date");
            idUserColumn = c.getColumnIndex("id_user");

                    idWorkout = c.getString(idColumn);
                    //преобразование строки в тип хранения даты
                    String[] ymd = c.getString(idDateColumn).split("-");
                    GregorianCalendar date = new GregorianCalendar(Integer.parseInt(ymd[0]),
                            Integer.parseInt(ymd[1]), Integer.parseInt(ymd[2]));

                    Workout workout = new Workout(idWorkout,
                            date,
                            c.getString(idUserColumn));
                    workout.setNotes(noteDAO.getByIdWorkout(idWorkout));
                    workoutsList.add(workout);
            }
        return workoutsList.isEmpty() ? null : workoutsList;
    }

    public Workout getById(String id) {
        @SuppressLint("Recycle") Cursor c = this.database
                .query("workout",
                        null,
                        "id = ?",
                        new String[] {id},
                        null,
                        null,
                        null);
        Workout workout = null;

        if (c.moveToFirst()) {
            NoteActionsLocalDB noteDAO = new NoteActionsLocalDB(this.context);

            int idColumn = c.getColumnIndex("id");
            int idDateColumn = c.getColumnIndex("date");
            int idUserColumn = c.getColumnIndex("id_user");

            //преобразование строки в тип хранения даты
            String[] ymd = c.getString(idDateColumn).split("-");
            GregorianCalendar date = new GregorianCalendar(Integer.parseInt(ymd[0]),
                    Integer.parseInt(ymd[1]), Integer.parseInt(ymd[2]));

                workout = new Workout(c.getString(idColumn),
                        date,
                        c.getString(idUserColumn));
                workout.setNotes(noteDAO.getByIdWorkout(id));
        }
        return workout;
    }

    public List<Workout> getAll() {
        @SuppressLint("Recycle") Cursor c = this.database
                .query("workout",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
        List<Workout> listWorkout = new ArrayList<>();

        int idColumn;
        int idDateColumn;
        int idUserColumn;

        while(c.moveToNext()) {

            idColumn = c.getColumnIndex("id");
            idDateColumn = c.getColumnIndex("date");
            idUserColumn = c.getColumnIndex("id_user");

            String[] ymd = c.getString(idDateColumn).split("-");
            GregorianCalendar date = new GregorianCalendar(Integer.parseInt(ymd[0]),
                    Integer.parseInt(ymd[1]), Integer.parseInt(ymd[2]));

            listWorkout.add(new Workout(c.getString(idColumn),
                    date,
                    c.getString(idUserColumn)));
        }

        return listWorkout;
    }

    @Override
    public void disconnect() {
        this.database.close();
    }
}