package com.example.fitnessapp.model.entities;

public class Settings {

    private int id;
    private String email;
    private int status;

    Settings(int id, String email, int status) {
        this.id = id;
        this.email = email;
        this.status = status;
    }

    public int getId() {
        return id;
    }

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