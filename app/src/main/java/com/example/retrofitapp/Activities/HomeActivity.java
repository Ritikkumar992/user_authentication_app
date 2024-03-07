package com.example.retrofitapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.retrofitapp.ModelResponse.DeleteResponse;
import com.example.retrofitapp.NavFragments.DashboardFragment;
import com.example.retrofitapp.NavFragments.ProfileFragment;
import com.example.retrofitapp.NavFragments.UsersFragment;
import com.example.retrofitapp.R;
import com.example.retrofitapp.RetrofitClient;
import com.example.retrofitapp.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    MenuItem logoutItem;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // creating bottom navigation bar.
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new DashboardFragment());


        // ============================ Shared Preference ========================================//
        sharedPrefManager  = new SharedPrefManager(getApplicationContext());

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


    // ========================= ADDING LOGOUT MENU LIST ========================================//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logoutmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int logoutId = item.getItemId();
        int deleteId = item.getItemId();

        if (logoutId == R.id.logout) {
            logOutUser();
        }
        if(deleteId == R.id.deleteAccount) {
            deleteAccount();
        }
        return super.onOptionsItemSelected(item);
    }


    private void deleteAccount() {
        Call<DeleteResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .deleteUserAccount(sharedPrefManager.getUser().getId());
        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {

                DeleteResponse deleteResponse = response.body();
                String status = deleteResponse.getStatus();

                if(response.isSuccessful() && status.equals("200")){
                    logOutUser();
                    Toast.makeText(HomeActivity.this, deleteResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(HomeActivity.this, deleteResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {
                Toast.makeText(HomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void logOutUser() {
        sharedPrefManager.logout();
        navigateToLogin();

    }
    private  void navigateToLogin()
    {
        Intent iLogin = new Intent(HomeActivity.this, LoginActivity.class);
        iLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(iLogin);

        Toast.makeText(this, "You have been logged out", Toast.LENGTH_SHORT).show();
    }
}

