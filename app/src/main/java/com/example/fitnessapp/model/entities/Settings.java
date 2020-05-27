package com.example.fitnessapp.model.entities;

import androidx.annotation.NonNull;

public class Settings {

    private int id;
    private String email;
    private int status;

    public Settings(int id, String email, int status) {
        this.id = id;
        this.email = email;
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        return id + "-" + email + "-" + status;
    }

    public int getId() { return this.id; }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}