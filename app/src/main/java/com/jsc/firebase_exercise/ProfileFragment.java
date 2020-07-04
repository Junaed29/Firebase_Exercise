package com.jsc.firebase_exercise;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    Button updateProfile;
    CircleImageView circleImageView;
    TextInputEditText profileNameEditText;
    ProgressBar progressBar;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateProfile = view.findViewById(R.id.updatebuttonId);
        circleImageView = (CircleImageView) view.findViewById(R.id.profile_image);
        profileNameEditText =  view.findViewById(R.id.profileNameEditTextId);
        progressBar =  view.findViewById(R.id.progressBar);

        updateProfile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}