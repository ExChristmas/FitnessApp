package com.example.fitnessapp.ui.authentication.authorization;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fitnessapp.model.Authentication;
import com.example.fitnessapp.model.entities.User;

public class AuthorizationViewModel extends AndroidViewModel {

    private Authentication authentication;

    public AuthorizationViewModel(@NonNull Application application) {
        super(application);
        authentication = new Authentication(application.getApplicationContext());
    }

    LiveData<User> autorization(String email, String password) {
        return authentication.authorization(email, password);
    }

}