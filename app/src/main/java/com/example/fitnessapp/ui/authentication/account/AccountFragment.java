package com.example.fitnessapp.ui.authentication.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.ui.authentication.authorization.AuthorizationFragment;
import com.example.fitnessapp.ui.authentication.authorization.AuthorizationViewModel;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private AuthorizationViewModel authorizationViewModel;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.accountViewModel = new ViewModelProvider(getActivity())
                .get(AccountViewModel.class);
        this.authorizationViewModel = new ViewModelProvider(getActivity())
                .get(AuthorizationViewModel.class);

         return inflater.inflate(R.layout.account_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewSurname = view.findViewById(R.id.textViewSurname);
        TextView textViewEmail = view.findViewById(R.id.textViewEmail);
        Button buttonSignOut = view.findViewById(R.id.buttonSignOut);

        textViewName.setText(user.getName());
        textViewSurname.setText(user.getSurname());
        textViewEmail.setText(user.getEmail());

        buttonSignOut.setOnClickListener(v -> {
            accountViewModel.recording(authorizationViewModel.getUser());
            accountViewModel.signOut();
            authorizationViewModel.signOut();
            AuthorizationFragment authorizationFragment = new AuthorizationFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, authorizationFragment);
            transaction.commit();
        });
    }

    public void setUser(User user) {
        this.user = user;
    }
}