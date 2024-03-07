package com.example.retrofitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.example.retrofitapp.Activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent iLogin = new Intent(MainActivity.this, LoginActivity.class);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(iLogin);
                finish();
            }
        }, 3000);
    }
}