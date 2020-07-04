package com.jsc.firebase_exercise;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "LoginFragment";
    Toolbar toolbar;
    FloatingActionButton floatingActionButton;

    int AUTHUI_REQUEST_CODE = 10001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        toolbar = getActivity().findViewById(R.id.toolbar);
        floatingActionButton = getActivity().findViewById(R.id.fab);
        toolbar.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);


        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            NavHostFragment.findNavController(LoginFragment.this)
                    .navigate(R.id.action_secondFragment_to_FirstFragment);
            toolbar.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);
        }

        view.findViewById(R.id.button_second).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        List<AuthUI.IdpConfig> provider = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        Intent intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(provider)
                .setTosAndPrivacyPolicyUrls("https://example.com", "https://example.com")
                .setLogo(R.drawable.ic_note)
                .setAlwaysShowSignInMethodScreen(true)
                .build();
        startActivityForResult(intent, AUTHUI_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTHUI_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // We have signed in the user or we have a new user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG, "onActivityResult:  User Email: " + user.getEmail());

                if (user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()) {
                    Toast.makeText(getContext(), "Welcome New User", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Welcome back", Toast.LENGTH_SHORT).show();
                }

                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_secondFragment_to_FirstFragment);
                toolbar.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.VISIBLE);
            } else {
                //Signing is failed
                IdpResponse response = IdpResponse.fromResultIntent(data);
                if (response == null) {
                    Log.d(TAG, "onActivityResult: the user ha canceled the signing");
                } else {
                    Log.e(TAG, "onActivityResult: ", response.getError());
                }
            }
        }
    }
}