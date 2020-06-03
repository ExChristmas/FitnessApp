package com.example.fitnessapp.model.entities;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Workout {

    private String id;
    private GregorianCalendar date;
    private String idUser;
    private List<Note> notes;

    public Workout() { }

    public Workout(String id, GregorianCalendar date, String idUser) {
        this.id = id;
        this.date = date;
        this.idUser = idUser;
        this.notes = new ArrayList<>();
    }

    @NonNull
    @Override
    public String toString() {
        return id + " | " + date.get(Calendar.YEAR) + "-" + date.get(Calendar.MONTH) + "-" + date.get(Calendar.DAY_OF_YEAR)
                + " | " + idUser;
    }

    public String getId() {
        return id;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public String getIdUser() {
        return idUser;
    }

    public List<Note> getNotes() {
        return this.notes;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(GregorianCalendar date) {
        this.date = date;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

}