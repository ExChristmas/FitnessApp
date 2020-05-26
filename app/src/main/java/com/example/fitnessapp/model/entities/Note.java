package com.example.fitnessapp.model.entities;

public class Note {

    private String id;
    private String record;
    private String idWorkout;
    private String idExerscise;

    public Note() { }

    public Note (String id, String record, String idWorkout, String idExerscise) {
        this.id = id;
        this.record = record;
        this.idWorkout = idWorkout;
        this.idExerscise = idExerscise;
    }

    public String getId() {
        return id;
    }

    public String getRecord() {
        return record;
    }

    public String getIdWorkout() {
        return idWorkout;
    }

    public String getIdExerscise() {
        return idExerscise;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public void setIdWorkout(String idWorkout) {
        this.idWorkout = idWorkout;
    }

    public void setIdExerscise(String idExerscise) {
        this.idExerscise = idExerscise;
    }

    public boolean isEmpty() {
        return id == null && record == null && idWorkout == null && idExerscise == null;
    }
}