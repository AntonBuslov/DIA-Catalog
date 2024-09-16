package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class Compare_Acrivity extends AppCompatActivity {
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_compare_acrivity);
        navigationView = findViewById(R.id.navigationBar);
        navigationView.setSelectedItemId(R.id.compare);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    startActivity( new Intent( Compare_Acrivity.this, MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                if(item.getItemId() == R.id.compare){
                    return true;
                }
                if(item.getItemId() == R.id.favorits){
                    startActivity( new Intent( Compare_Acrivity.this, Favorities_Activity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                if(item.getItemId() == R.id.profile){
                    if(FirebaseAuth.getInstance().getCurrentUser() == null){
                        startActivity( new Intent( Compare_Acrivity.this, Login_Activity.class));
                        overridePendingTransition(0,0);
                    }else{
                        startActivity( new Intent( Compare_Acrivity.this, Account_Activity.class));
                        overridePendingTransition(0,0);
                    }

                    return true;
                }
                return false;
            }
        });
    }
}