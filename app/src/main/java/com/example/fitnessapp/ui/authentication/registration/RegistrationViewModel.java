package com.example.fitnessapp.ui.authentication.registration;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.fitnessapp.model.Authentication;

public class RegistrationViewModel extends AndroidViewModel {

    private Authentication authentication;

    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        authentication = new Authentication(application.getApplicationContext());
    }

    void registration(String name, String surname, String email,
                      String password, String passwordConfirm) {
        if(password.equals(passwordConfirm)) {
            authentication.registration(name, surname, email, password);
        }
    }

}