package com.example.fitnessapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.dao.impl.UserActionsGlobalDB;
import com.example.fitnessapp.model.entities.Exercise;
import com.example.fitnessapp.model.entities.Note;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.model.entities.Workout;
import com.example.fitnessapp.ui.account.AccountFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private AccountFragment accountFragment;
    private EditText editTextEmail;
    private EditText editTextPas;
    private Button buttonSignIn;
    private Button buttonRegistration;
    private FirebaseFirestore db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.db = FirebaseFirestore.getInstance();

        this.notificationsViewModel =
                ViewModelProviders.of(getActivity()).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.email_password_fragment, container,
                false);

        this.editTextEmail = root.findViewById(R.id.editTextEmail);
        this.editTextPas = root.findViewById(R.id.editTextPas);
        this.buttonSignIn = root.findViewById(R.id.buttonSignIn);
        this.buttonRegistration = root.findViewById(R.id.buttonReg);

        this.accountFragment = new AccountFragment();

        this.buttonSignIn.setOnClickListener(v -> {
            notificationsViewModel.autorization(editTextEmail.getText().toString(),
                    editTextPas.getText().toString()).observe(getViewLifecycleOwner(), user ->  {

                Bundle result = new Bundle();
                result.put
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, accountFragment);
                transaction.commit();
            });
        });

        this.buttonRegistration.setOnClickListener(v -> {
        });

        return root;

    }

    @Override
    public void onStart() {
        super.onStart();
    }

}