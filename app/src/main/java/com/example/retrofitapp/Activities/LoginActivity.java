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

import com.example.retrofitapp.ModelResponse.LoginResponse;
import com.example.retrofitapp.R;
import com.example.retrofitapp.RetrofitClient;
import com.example.retrofitapp.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView registerText;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.login_email_id);
        password = findViewById(R.id.login_password_id);
        login = findViewById(R.id.login_button_id);
        registerText = findViewById(R.id.login_register_id);

        // Navigate to Home Page after successfully login.
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        sharedPrefManager = new SharedPrefManager(getApplicationContext());

        // Navigate to SignUp Page for registration.
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iRegister = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(iRegister);
            }
        });
    }

    private void userLogin() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

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
        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .login(userEmail, userPassword);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                String status = loginResponse.getStatus();
                if(response.isSuccessful()){
                    if(status.equals("200")){
                        sharedPrefManager.saveUser(loginResponse.getUser());
                        navigateToHome();
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else{
//                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(LoginActivity.this, "USER NOT FOUND ⚠️", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this,  loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
        if(sharedPrefManager.isLoggedIn()){
            navigateToHome();
        }
    }

    private  void navigateToHome()
    {
        Intent iHome = new Intent(LoginActivity.this, HomeActivity.class);
        iHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(iHome);
    }
}