package com.natanshrestha.babybuy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.natanshrestha.babybuy.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private UserViewModel model;
    private boolean rememberEmail = false;

    // SharedPreferences file name for storing email
    private static final String PREF_NAME = "login_prefs";
    private static final String KEY_EMAIL = "email";

    public LoginFragment() {
        // Required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        model = new ViewModelProvider(this).get(UserViewModel.class);
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        Button btnLogin = binding.btnLogin;
        CheckBox cbRememberMe = binding.checkBoxRemberme;
        TextView txtRegisterLink = binding.txtRegisterLink;

        // Check if email was remembered from previous session
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String rememberedEmail = sharedPreferences.getString(KEY_EMAIL, "");
        if (!TextUtils.isEmpty(rememberedEmail)) {
            binding.edtEmail.setText(rememberedEmail);
            cbRememberMe.setChecked(true);
            rememberEmail = true;
        }

        // Login button click listener
        btnLogin.setOnClickListener(v -> {
            String email = binding.edtEmail.getText().toString();
            String password = binding.edtPassword.getText().toString();
            User user;

            // Check if the Remember Me checkbox is checked
            rememberEmail = cbRememberMe.isChecked();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                String error = getString(R.string.error_empty_fields);
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    binding.inlEmail.setError(error);
                    binding.inlPassword.setError(error);
                } else {
                    if (TextUtils.isEmpty(email)) {
                        binding.inlEmail.setError(error);
                        binding.inlPassword.setError(null);
                    }
                    if (TextUtils.isEmpty(password)) {
                        binding.inlPassword.setError(error);
                        binding.inlEmail.setError(null);
                    }
                }
            } else {
                binding.inlEmail.setError(null);
                binding.inlPassword.setError(null);

                // If Remember Me is checked, save the email
                if (rememberEmail) {
                    saveEmail(email);
                }

                // Perform login operation (example: fetching user from ViewModel)
                user = model.getUser(email, password);
                if (user == null) {
                    String error = getString(R.string.error_login);
                    binding.inlEmail.setError(error);
                    binding.inlPassword.setError(error);
                    return;
                }
                binding.inlEmail.setError(null);
                binding.inlPassword.setError(null);

                // Navigate to Dashboard fragment on successful login
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_dashboardFragment);
            }
        });

        // Register link click listener
        txtRegisterLink.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registrationFragment));
    }

    // Method to save email to SharedPreferences
    private void saveEmail(String email) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }
}
