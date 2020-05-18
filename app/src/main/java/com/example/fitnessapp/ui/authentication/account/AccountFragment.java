package com.example.fitnessapp.ui.authentication.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.fitnessapp.R;
import com.example.fitnessapp.model.entities.User;
import com.example.fitnessapp.ui.authentication.authorization.AuthorizationFragment;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.accountViewModel =
                ViewModelProviders.of(getActivity()).get(AccountViewModel.class);
        View root = inflater.inflate(R.layout.account_fragment, container, false);

        TextView textViewName = root.findViewById(R.id.textViewName);
        TextView textViewSurname = root.findViewById(R.id.textViewSurname);
        TextView textViewEmail = root.findViewById(R.id.textViewEmail);
        Button buttonSignOut = root.findViewById(R.id.buttonSignOut);

        textViewName.setText(user.getName());
        textViewSurname.setText(user.getSurname());
        textViewEmail.setText(user.getEmail());

        buttonSignOut.setOnClickListener(v -> {
            accountViewModel.signOut();
            AuthorizationFragment authorizationFragment = new AuthorizationFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, authorizationFragment);
            transaction.commit();
        });

        return root;
    }

    public void setUser(User user) {
        this.user = user;
    }

}