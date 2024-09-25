package com.example.myapplication;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class UserDataClass {
     public String Email;
    public String Name;
    public List<String> LastView;

    public UserDataClass(String email, String name, List<String> lastView) {
        Email = email;
        Name = name;
        LastView = lastView;
    }
    public UserDataClass() {
    }


}
