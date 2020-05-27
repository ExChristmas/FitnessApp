package com.example.fitnessapp.ui.authentication.account;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.fitnessapp.model.Authentication;

public class AccountViewModel extends AndroidViewModel {

    private Authentication authentication;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        authentication = new Authentication(application.getApplicationContext());
    }

    void signOut() {
        authentication.signOut();
    }
}