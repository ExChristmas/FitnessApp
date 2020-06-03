package com.example.fitnessapp.model.entities;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String name;
    private String surname;
    private String email;
    private List<Workout> journal;


    public User() { }

    public User(String email, String name, String surname) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.journal = new ArrayList<>();
    }

    public User(User user) {
        this.email = user.email;
        this.name = user.name;
        this.surname = user.surname;
        this.journal = user.journal;
    }

    @NonNull
    @Override
    public String toString() {
        return email + " | " + name + " | " + surname;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getEmail() {
        return this.email;
    }

    public List<Workout> getJournal() {
        return this.journal;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setJournalWorkout(List<Workout> journal) {
        this.journal = journal;
    }

    public boolean setJournalDeleteWorkout(String id) {
        for(int i = 0; i < journal.size(); i++) {
            if(journal.get(i).getId().equals(id)) {
                journal.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean setJournalDeleteNote(String idNote, String idWorkout) {
        for(int i = 0; i < journal.size(); i++) {
            if(journal.get(i).getId().equals(idWorkout)) {
                List<Note> notes = journal.get(i).getNotes();
                for(int j = 0; j < notes.size(); j++) {
                    if(notes.get(j).getId().equals(idNote)) {
                        journal.get(i).getNotes().remove(j);
                        return true;
                    }
                }
            }
        }
        return false;
    }

}