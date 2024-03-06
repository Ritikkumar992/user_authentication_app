package com.example.retrofitapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.retrofitapp.ModelResponse.RegisterResponse;
import com.example.retrofitapp.R;
import com.example.retrofitapp.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, password;
    Button register;
    TextView loginText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        name = findViewById(R.id.register_name_id);
        email = findViewById(R.id.register_email_id);
        password = findViewById(R.id.register_password_id);
        register = findViewById(R.id.register_button_id);
        loginText = findViewById(R.id.register_login_id);

        // successfully registration of user.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                //Toast.makeText(SignUpActivity.this, "User Register", Toast.LENGTH_SHORT).show();
            }
        });

        // navigate to login page
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iLogin = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(iLogin);
            }
        });

    }

    private void registerUser() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(userName.isEmpty()){
            name.requestFocus();
            name.setError("Please enter your name ⚠️");
            return;
        }
        if(userEmail.isEmpty()){
            email.requestFocus();
            email.setError("Please enter your email ⚠️");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            email.requestFocus();
            email.setError("Please enter correct email ⚠️");
            return;
        }
        if(userPassword.isEmpty()){
            password.requestFocus();
            password.setError("Please enter your Password ⚠️");
            return;
        }

        //------------------------------------- API----------------------------//
        Call<RegisterResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .register(userName, userEmail, userPassword);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {

                RegisterResponse registerResponse = response.body();
                if(response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SignUpActivity.this, registerResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}