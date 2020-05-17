package com.example.fitnessapp.ui.notifications;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fitnessapp.model.Authentication;
import com.example.fitnessapp.model.dao.impl.ExerciseActionsLocalDB;
import com.example.fitnessapp.model.entities.User;

public class NotificationsViewModel extends AndroidViewModel{

    private ExerciseActionsLocalDB exLocalDB;

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mEmail;

    private Authentication authentication;

    public NotificationsViewModel(@NonNull Application application) {
        super(application);
        authentication = new Authentication(application.getApplicationContext());
        mText = new MutableLiveData<>();
    }

    public LiveData<User> autorization(String email, String password) {
        return authentication.authorization(email, password);
    }

    public void registration(String name, String surname, String email, String password) {
        authentication.registration(name, surname, email, password);
    }

    public LiveData<String> getText() {
        return mText;
    }

//    public boolean authorization(String email, String password) {
//    }

}