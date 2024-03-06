package com.example.retrofitapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.retrofitapp.NavFragments.DashboardFragment;
import com.example.retrofitapp.NavFragments.ProfileFragment;
import com.example.retrofitapp.NavFragments.UsersFragment;
import com.example.retrofitapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // creating bottom navigation bar.
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new DashboardFragment());
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        int itemId = item.getItemId();

        if (itemId == R.id.dashboard) {
            fragment = new DashboardFragment();
        } else if (itemId == R.id.users) {
            fragment = new UsersFragment();
        } else if (itemId == R.id.profile) {
            fragment = new ProfileFragment();
        }
        if(fragment != null){
            loadFragment(fragment);
            return true;
        }
        return false;
    }
    void loadFragment(Fragment fragment){
        // to attach fragment.
        getSupportFragmentManager().beginTransaction().replace(R.id.relativeLayout, fragment).commit();
    }
}