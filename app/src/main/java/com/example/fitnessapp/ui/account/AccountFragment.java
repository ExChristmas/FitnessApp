package com.example.fitnessapp.ui.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.ui.notifications.NotificationsViewModel;

public class AccountFragment extends Fragment {

    private EditText editTextPas;
    private EditText editTextEmail;
    private EditText editTextPas;
    private Button buttonSignIn;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.account_fragment, container, false);


        return root;
    }

    private void setUser(User user) {
        this.user = user;
    }

}