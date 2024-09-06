package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigationView = findViewById(R.id.navigationBar);
        navigationView.setSelectedItemId(R.id.home);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    return true;
                }
                if(item.getItemId() == R.id.compare){
                    startActivity( new Intent( MainActivity.this, Compare_Acrivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                if(item.getItemId() == R.id.favorits){
                    startActivity( new Intent( MainActivity.this, Favorities_Activity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                if(item.getItemId() == R.id.profile){
                    if(FirebaseAuth.getInstance().getCurrentUser() == null){
                        startActivity( new Intent( MainActivity.this, Login_Activity.class));
                        overridePendingTransition(0,0);
                    }else{
                        startActivity( new Intent( MainActivity.this, Account_Activity.class));
                        overridePendingTransition(0,0);
                    }

                    return true;
                }
                return false;
            }
        });
    }



}