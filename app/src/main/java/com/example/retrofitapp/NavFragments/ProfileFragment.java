package com.example.retrofitapp.NavFragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofitapp.Activities.HomeActivity;
import com.example.retrofitapp.Activities.LoginActivity;
import com.example.retrofitapp.ModelResponse.LoginResponse;
import com.example.retrofitapp.ModelResponse.UpdatePassResponse;
import com.example.retrofitapp.R;
import com.example.retrofitapp.RetrofitClient;
import com.example.retrofitapp.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

   EditText userName, userEmail, currentPassword, newPassword;
    int userId;
    String userEmailId;
   SharedPrefManager sharedPrefManager;
   Button updateUserAccountButton, updateBtnPassword;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        userName = view.findViewById(R.id.userName);
        userEmail = view.findViewById(R.id.userEmail);

        // for update Password
        currentPassword = view.findViewById(R.id.currentPassword);
        newPassword = view.findViewById(R.id.newPassword);
        
        
        // ................Shared Preference........
        sharedPrefManager = new SharedPrefManager(getActivity());
        userId = sharedPrefManager.getUser().getId();
        userEmailId = sharedPrefManager.getUser().getEmail();
        
        
        
        

        //========================================== USER ACCOUNT UPDATE ===============================//
        updateUserAccountButton = view.findViewById(R.id.updateBtn);
        updateUserAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserAccount();
            }

            private void updateUserAccount() {
                String name = userName.getText().toString().trim();
                String email = userEmail.getText().toString().trim();

                if(name.isEmpty()){
                    userName.setError("Please Enter User Name ⚠️");
                    userName.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    userEmail.requestFocus();
                    userEmail.setError("Please Enter correct Email ⚠️");
                    return;
                }

                //--------------------------- RETROFIT CALL ----------------------------------------//
                Call<LoginResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .updateUserAccount(userId,name, email);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        LoginResponse updateResponse = response.body();
                        String status = updateResponse.getStatus();

                        if(response.isSuccessful() && status.equals("200")){
                                sharedPrefManager.saveUser(updateResponse.getUser()); // saving data to shared preference.
                                Toast.makeText(getActivity(), updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                navigateToHome();
                            }
                        else{
                            Toast.makeText(getActivity(), updateResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // =================================================Update Password==============================//
        updateBtnPassword = view.findViewById(R.id.updateBtnPassword);
        updateBtnPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserPassword();
            }

            private void updateUserPassword() {
                String userCurrentPassword = currentPassword.getText().toString().trim();
                String userNewPassword = newPassword.getText().toString().trim();


                if(userCurrentPassword.isEmpty()){
                    currentPassword.setError("Please Enter Your Current Password ⚠️");
                    currentPassword.requestFocus();
                    return;
                }
                if(userNewPassword.isEmpty()){
                    newPassword.setError("Please Enter Your Current Password ⚠️");
                    newPassword.requestFocus();
                    return;
                }

                //=======================================RETROFIT IMPLEMENTATION ===========================//
                Call<UpdatePassResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .updateUserPassword(userEmailId, userCurrentPassword, userNewPassword);

                call.enqueue(new Callback<UpdatePassResponse>() {
                    @Override
                    public void onResponse(Call<UpdatePassResponse> call, Response<UpdatePassResponse> response) {
                        UpdatePassResponse passwordResponse = response.body();
                        String status = passwordResponse.getStatus();

                        if(response.isSuccessful() && status.equals("200")){
                            Toast.makeText(getActivity(), passwordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            navigateToHome();
                        }
                        else{
                            Toast.makeText(getActivity(), passwordResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<UpdatePassResponse> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
        

        return view;
    }
    private  void navigateToHome()
    {
        Intent iHome = new Intent(requireContext(), HomeActivity.class);
        iHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(iHome);
    }
}