package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Account_Activity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    BottomNavigationView navigationView;

    TextView emailText, usernameText;

    Button editProfileButton;
    ProgressBar progresBarForName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        navigationView = findViewById(R.id.navigationBar);

        emailText = findViewById(R.id.emailText);
        usernameText = findViewById(R.id.usernameText);

        progresBarForName = findViewById(R.id.progressBar);
        editProfileButton = findViewById(R.id.button);

        emailText.setVisibility(View.INVISIBLE);
        usernameText.setVisibility(View.INVISIBLE);

        navigationView.setSelectedItemId(R.id.profile);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    startActivity( new Intent( Account_Activity.this, MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                if(item.getItemId() == R.id.compare){
                    startActivity( new Intent( Account_Activity.this, Compare_Acrivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                if(item.getItemId() == R.id.favorits){
                    startActivity( new Intent( Account_Activity.this, Favorities_Activity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                if(item.getItemId() == R.id.profile){

                    return true;
                }
                return false;
            }
        });

        refreshName();

    }
    private void refreshName(){
        DocumentReference docRef = firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()){
                        emailText.setText(doc.get("Email").toString());
                        usernameText.setText(doc.get("Name").toString());
                        emailText.setVisibility(View.VISIBLE);
                        usernameText.setVisibility(View.VISIBLE);
                        progresBarForName.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }
    public void LogOut(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Account_Activity.this, MainActivity.class));
        finish();
    }

    public void ChangeName(View view){
        startActivity(new Intent(Account_Activity.this, ChangeName.class));
    }

    public void ChangeEmail(View view){
        startActivity(new Intent(Account_Activity.this, ChangeEmail.class));
    }

    public void ChangePassword(View view){
        startActivity(new Intent(Account_Activity.this, ChangePassword.class));
    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshName();
    }
}