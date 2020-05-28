package com.example.fitnessapp.ui.authentication.authorization;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fitnessapp.model.Authentication;
import com.example.fitnessapp.model.entities.Settings;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.model.entities.Workout;

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
        // ждём, когда придёт юзер
        authentication.authorization(email, password).observeForever(user -> {
            this.user = user;
            liveData.setValue(user);
        });

        return liveData;
//            SettingsActionsLocalDB settingsActionsLocalDB = new SettingsActionsLocalDB(application);
//            settings = settingsActionsLocalDB.getRecord();

        // если интернет есть
//            if (InternetConnection.isConnect(application)) {
//                // если в прошлой сессии не было интренета и был авторизован текущий юзер
//                if (settings.getStatus() == 0 && settings.getEmail().equals(user.getEmail())) {
//                    UserActionsLocalDB userActionsLocalDB = new UserActionsLocalDB(application);
//
//                    // то берём юзера из локальной базы
//                    User userRes = userActionsLocalDB.getByEmail(user.getEmail());
//
//                    // сохраняем юзера во ViewModel
//                    this.user = userRes;
//                    liveData.setValue(userRes);
//                    userActionsLocalDB.disconnect();
//
//                    // сохраняем информацию о пользователе в локальной базе
//                    settings.setStatus(1);
//                    settings.setEmail(userRes.getEmail());
//                    settingsActionsLocalDB.update(settings);
//
//                    // и обновляем юзера в глобальной БД
//                    UserActionsGlobalDB userActionsGlobalDB = new UserActionsGlobalDB();
//                    userActionsGlobalDB.add(userRes);
//                } else { // иначе, обновляем юзера в локальной БД
//                    UserActionsLocalDB userActionsLocalDB = new UserActionsLocalDB(application);
//                    userActionsLocalDB.update(user);
//                    userActionsLocalDB.disconnect();
//
//                    // сохраняем юзера во ViewModel
//                    this.user = user;
//                    liveData.setValue(user);
//
//                    // сохраняем информацию о пользователе в локальной базе
//                    settings.setStatus(1);
//                    settings.setEmail(user.getEmail());
//                    settingsActionsLocalDB.update(settings);
//                }
//            } else { // иначе, если нет интернета
////                UserActionsLocalDB userActionsLocalDB = new UserActionsLocalDB(application);
////                User userRes = userActionsLocalDB.getByEmail(settings.getEmail());
//                this.user = user;
//                liveData.setValue(user); // то отправляем юзера, пришедшего из локальной БД
//
//                // сохраняем информацию о пользователе в локальной базе
//                settings.setStatus(0);
//                settings.setEmail(user.getEmail());
//                settingsActionsLocalDB.update(settings);
//            }
//        });

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