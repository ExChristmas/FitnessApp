package com.example.fitnessapp.ui.authentication.authorization;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.R;
import com.example.fitnessapp.ui.authentication.account.AccountFragment;
import com.example.fitnessapp.ui.authentication.registration.RegistrationFragment;

public class AuthorizationFragment extends Fragment {

    private AuthorizationViewModel authorizationViewModel;
    private EditText editTextEmail;
    private EditText editTextPas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.authorizationViewModel =
                new ViewModelProvider(getActivity()).get(AuthorizationViewModel.class);
        View root = inflater.inflate(R.layout.email_password_fragment, container,
                false);

        this.editTextEmail = root.findViewById(R.id.editTextEmail);
        this.editTextPas = root.findViewById(R.id.editTextPas);
        Button buttonSignIn = root.findViewById(R.id.buttonSignIn);
        Button buttonRegistration = root.findViewById(R.id.buttonRegInReg);

        buttonSignIn.setOnClickListener(v -> {
            authorizationViewModel.autorization(editTextEmail.getText().toString(),
                    editTextPas.getText().toString()).observe(getViewLifecycleOwner(), user -> {
                        if(user != null) {
                            AccountFragment accountFragment = new AccountFragment();
                            accountFragment.setUser(user);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.nav_host_fragment, accountFragment);
                            transaction.commit();
                        }
            });
        });

        buttonRegistration.setOnClickListener(v -> {
            RegistrationFragment registrationFragment = new RegistrationFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, registrationFragment);
            transaction.commit();
        });

        return root;
    }

}