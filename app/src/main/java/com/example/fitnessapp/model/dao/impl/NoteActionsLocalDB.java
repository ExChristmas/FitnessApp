package com.example.fitnessapp.model.dao.impl;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fitnessapp.model.DBHelper;
import com.example.fitnessapp.model.dao.ConectionDB;
import com.example.fitnessapp.model.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteActionsLocalDB implements ConectionDB {

    private SQLiteDatabase database;

    public NoteActionsLocalDB(Context context) {
        this.database = new DBHelper(context).getWritableDatabase();
    }

    public void add(Note note) {
        ContentValues cv = new ContentValues();
        cv.put("record", note.getRecord());
        cv.put("id_workout", note.getIdWorkout());
        cv.put("id_exercise", note.getIdExerscise());
        this.database.insert("note", null, cv);
    }

    public void remove(Note note) {
        this.database.delete("note", "id = ?",
                new String[] {note.getId()});
    }

    public void update(Note note) {
        ContentValues cv = new ContentValues();
        cv.put("record", note.getRecord());
        cv.put("id_workout", note.getIdWorkout());
        cv.put("id_exercise", note.getIdExerscise());
        this.database.update("note", cv, "id = ?",
                new String[] {note.getId()});
    }

    public List<Note> getByIdWorkout(String idWorkout) {
        @SuppressLint("Recycle") Cursor c = this.database
                .query("note",
                        null,
                        "id_worlout = ?",
                        new String[] {idWorkout},
                        null,
                        null,
                        null);
        List<Note> noteList = new ArrayList<>();
        while(c.moveToNext()) {
            Note note = new Note(Long.toString(c.getLong(0)),
                    c.getString(1),
                    idWorkout,
                    Long.toString(c.getLong(2)));
            noteList.add(note);
        }
        return noteList.isEmpty() ? null : noteList;
    }

    public void removeByIdWorkout(String idWorkout) {
        this.database.delete("note", "id_workout = ?",
                new String[] {idWorkout});
    }

    @Override
    public void disconnect() {
        this.database.close();
    }

}