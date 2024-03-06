package com.example.retrofitapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.retrofitapp.R;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView registerText;

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
                Intent iHome = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(iHome);
            }
        });
        // Navigate to SignUp Page for registration.
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iRegister = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(iRegister);
            }
        });
    }
}