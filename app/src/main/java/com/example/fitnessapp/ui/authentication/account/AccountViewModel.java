package com.example.fitnessapp.ui.authentication.account;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.fitnessapp.model.Authentication;
import com.example.fitnessapp.model.Synchronization.RecordingToDB;
import com.example.fitnessapp.model.entities.User;

public class AccountViewModel extends AndroidViewModel {

    private Authentication authentication;
    private RecordingToDB recordingToDB;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        authentication = new Authentication(application.getApplicationContext());
        recordingToDB = new RecordingToDB(application);
    }

    void signOut() {
        authentication.signOut();
    }

    public void recording(User user) {
        recordingToDB.recording(user);
    }
}