package com.example.fitnessapp.ui.authentication.authorization;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnessapp.model.Authentication;
import com.example.fitnessapp.model.dao.impl.SettingsActionsLocalDB;
import com.example.fitnessapp.model.dao.impl.UserActionsGlobalDB;
import com.example.fitnessapp.model.dao.impl.UserActionsLocalDB;
import com.example.fitnessapp.model.entities.Settings;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.model.entities.Workout;
import com.example.fitnessapp.model.internetconnection.InternetConnection;

import java.util.List;

public class AuthorizationViewModel extends AndroidViewModel {

    private Application application;
    private User user;
    private Authentication authentication;
    private Settings settings;

    public AuthorizationViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        authentication = new Authentication(application.getApplicationContext());
    }

    public LiveData<User> autorization(String email, String password) {

        MutableLiveData<User> liveData = new MutableLiveData<>();

//        this.user = user;
//        liveData.setValue(user);

        SettingsActionsLocalDB settingsActionsLocalDB = new SettingsActionsLocalDB(application);
        settings = settingsActionsLocalDB.getRecord();

        // если интернет есть
        if (InternetConnection.isConnect(application)) {
            // ждём, когда придёт пользователь
            authentication.authorization(email, password).observeForever(user -> {

                // если в прошлой сессии не было интренета и был авторизован текущий пользователь
                if (settings.getStatus() == 0 && settings.getEmail().equals(user.getEmail())) {
                    UserActionsLocalDB userActionsLocalDB = new UserActionsLocalDB(application);

                    // то берём польхователя из локальной базы
                    User userRes = userActionsLocalDB.getByEmail(user.getEmail());
                    userActionsLocalDB.disconnect();

                    // сохраняем юзера во ViewModel
                    this.user = userRes;
                    // отправляем пользователя во фрагмент
                    liveData.setValue(userRes);

                    // и обновляем юзера в глобальной БД
                    UserActionsGlobalDB userActionsGlobalDB = new UserActionsGlobalDB();
                    userActionsGlobalDB.add(userRes);
                } else { // иначе, обновляем пользователя в локальной БД

                    UserActionsLocalDB userActionsLocalDB = new UserActionsLocalDB(application);
                    userActionsLocalDB.update(user);
                    userActionsLocalDB.disconnect();

                    // сохраняем юзера во ViewModel
                    this.user = user;
                    // отправляем пользователя во фрагмент
                    liveData.setValue(user);
                }
            });
        } else { // если интернета нет, то берём пользователя из локальной базы

            UserActionsLocalDB userActionsLocalDB = new UserActionsLocalDB(application);
            User user = userActionsLocalDB.getByEmail(email);
            userActionsLocalDB.disconnect();

            // сохраняем в ViewModel
            this.user = user;
            //отправляем пользователя во фрагмент
        }

        return liveData;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void signOut() {
        user = null;
    }

    // возврат авторизованного пользователя, в случае,
    // если пользователь не вышел из приложения и закрыл его
    public LiveData<User> getUserSignedIn() {
        return authentication.checkSignIn();
    }

    public void setUserWorkouts(List<Workout> workouts) {
        this.user.setJournalWorkout(workouts);
    }
}