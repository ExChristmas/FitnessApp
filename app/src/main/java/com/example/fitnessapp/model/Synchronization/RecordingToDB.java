package com.example.fitnessapp.model.Synchronization;

import android.content.Context;

import com.example.fitnessapp.model.dao.impl.SettingsActionsLocalDB;
import com.example.fitnessapp.model.dao.impl.UserActionsGlobalDB;
import com.example.fitnessapp.model.dao.impl.UserActionsLocalDB;
import com.example.fitnessapp.model.entities.Settings;
import com.example.fitnessapp.model.entities.User;
import com.google.firebase.auth.FirebaseAuth;

public class RecordingToDB {

    private FirebaseAuth mAuth;
    private UserActionsLocalDB userActionsLocalDB;
    private UserActionsGlobalDB userActionsGlobalDB;
    private SettingsActionsLocalDB settingsActionsLocalDB;
    private Settings settings;

    public RecordingToDB(Context context) {
//        userActionsLocalDB = new UserActionsLocalDB(context);
        userActionsGlobalDB = new UserActionsGlobalDB();
//        settingsActionsLocalDB = new SettingsActionsLocalDB(context);
        mAuth = FirebaseAuth.getInstance();
    }

    public void recording(User user) {
//        if (user != null) {
//            settings = settingsActionsLocalDB.getRecord();
//            if (userActionsLocalDB.getByEmail(user.getEmail()) == null) {
//                userActionsLocalDB.add(user);
//            } else {
//                userActionsLocalDB.update(user);
//            }
//            if (settings.getStatus() != 0) {
        if(user != null) {
            userActionsGlobalDB.add(user);
        }
//            }
//        }
    }
}