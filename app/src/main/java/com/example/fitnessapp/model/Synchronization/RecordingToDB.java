package com.example.fitnessapp.model.Synchronization;

import android.content.Context;

import com.example.fitnessapp.model.dao.impl.SettingsActionsLocalDB;
import com.example.fitnessapp.model.dao.impl.UserActionsGlobalDB;
import com.example.fitnessapp.model.dao.impl.UserActionsLocalDB;
import com.example.fitnessapp.model.entities.Settings;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.model.internetconnection.InternetConnection;

public class RecordingToDB {

    private UserActionsLocalDB userActionsLocalDB;
    private UserActionsGlobalDB userActionsGlobalDB;
    private SettingsActionsLocalDB settingsActionsLocalDB;
    private Context context;

    public RecordingToDB(Context context) {
        userActionsLocalDB = new UserActionsLocalDB(context);
        userActionsGlobalDB = new UserActionsGlobalDB();
        settingsActionsLocalDB = new SettingsActionsLocalDB(context);
        this.context = context;
    }

    public void recording(User user) {
        if (user != null) {
            Settings settings = new Settings();
            settings.setEmail(user.getEmail()); // запоминаем пользователя
            User userTemp = userActionsLocalDB.getByEmail(user.getEmail());
            if (userTemp != null) {
                userActionsLocalDB.remove(userTemp);
            }
            userActionsLocalDB.add(user);
            // если есть интернет
            if (InternetConnection.isConnect(context)) {
                // устанавливаем статус "интернет был"
                settings.setStatus(1);
                // добавляем или обновляем пользователя в глобальной базе
                userActionsGlobalDB.add(user);
            } else {
                // устанавливаем статус "интернета не было"
                settings.setStatus(0);
            }
            //сохраняем запись о предыдущем пользователе в базе
            settingsActionsLocalDB.update(settings);
        }

//        if(user != null) {
//            userActionsGlobalDB.add(user);
//        }
    }

}