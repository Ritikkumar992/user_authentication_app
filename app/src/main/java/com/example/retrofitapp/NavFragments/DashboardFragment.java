package com.example.retrofitapp.NavFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.retrofitapp.R;
import com.example.retrofitapp.SharedPrefManager;


public class DashboardFragment extends Fragment {

    TextView userName, userEmail;
    SharedPrefManager sharedPrefManager;

    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_dashboard, container, false);

        userName = view.findViewById(R.id.userNameId);
        userEmail = view.findViewById(R.id.userEmailId);

        sharedPrefManager = new SharedPrefManager(getActivity());

        String text = "Hey! " + sharedPrefManager.getUser().getUsername();
        userName.setText(text);
        userEmail.setText(sharedPrefManager.getUser().getEmail());

        return  view;
    }
}