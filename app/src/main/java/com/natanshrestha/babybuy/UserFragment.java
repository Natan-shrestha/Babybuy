package com.natanshrestha.babybuy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.List;

public class UserFragment extends Fragment {

    private TextView userfragFullname, userfragEmail, userfragFullnameforlogo;
    private ImageView gotodashboard;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        userfragFullname = view.findViewById(R.id.userfragfullname);
        userfragEmail = view.findViewById(R.id.userfragEmail);
        userfragFullnameforlogo = view.findViewById(R.id.tv_name);
        gotodashboard = view.findViewById(R.id.Gotoback);

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Observe the user data changes
        userViewModel.getUserLiveData().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                // Update UI with user data
                userfragFullname.setText(user.getName());
                userfragEmail.setText(user.getEmail());
                userfragFullnameforlogo.setText(user.getName());
            }
        });

        // Navigate to dashboard fragment on click
        gotodashboard.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_userFragment_to_dashboardFragment);
        });

        return view;
    }
}
