package com.example.fitnessapp.ui.authentication.authorization;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.model.internetconnection.InternetConnection;
import com.example.fitnessapp.ui.authentication.account.AccountFragment;
import com.example.fitnessapp.ui.authentication.registration.RegistrationFragment;

public class AuthorizationFragment extends Fragment {

    private AuthorizationViewModel authorizationViewModel;
    private EditText editTextEmail;
    private EditText editTextPas;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.authorizationViewModel =
                new ViewModelProvider(getActivity()).get(AuthorizationViewModel.class);
        return inflater.inflate(R.layout.email_password_fragment, container,
                false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        User userCheck = authorizationViewModel.getUser();

        authorizationViewModel.getUserSignedIn().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                authorizationViewModel.setUser(user);
                AccountFragment accountFragment = new AccountFragment();
                accountFragment.setUser(user);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, accountFragment);
                transaction.commit();
            }
            });

        if (userCheck != null) {
            AccountFragment accountFragment = new AccountFragment();
            accountFragment.setUser(userCheck);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, accountFragment);
            transaction.commit();
        }

        this.editTextEmail = view.findViewById(R.id.editTextEmail);
        this.editTextPas = view.findViewById(R.id.editTextPas);
        Button buttonSignIn = view.findViewById(R.id.buttonSignIn);
        Button buttonRegistration = view.findViewById(R.id.buttonRegInReg);

        this.editTextEmail.setText("stark@mail.ru");
        this.editTextPas.setText("1234567");

        buttonSignIn.setOnClickListener(v -> {
            authorizationViewModel.autorization(editTextEmail.getText().toString(),
                    editTextPas.getText().toString()).observe(getViewLifecycleOwner(), user -> {
                if (user != null) {
                    AccountFragment accountFragment = new AccountFragment();
                    accountFragment.setUser(user);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, accountFragment);
                    transaction.commit();
                }
            });
        });

        buttonRegistration.setOnClickListener(v -> {
            if(!InternetConnection.isConnect(getContext())) {
                Toast.makeText(getContext(),
                        "Нет соединения с интернетом!", Toast.LENGTH_SHORT).show();
            }
            RegistrationFragment registrationFragment = new RegistrationFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, registrationFragment);
            transaction.commit();
        });
    }
}