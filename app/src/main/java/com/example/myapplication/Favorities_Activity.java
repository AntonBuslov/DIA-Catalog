package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Favorities_Activity extends AppCompatActivity {
    BottomNavigationView navigationView;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favorities);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView =findViewById(R.id.FavoriteRecycle);

        navigationView = findViewById(R.id.navigationBar);
        navigationView.setSelectedItemId(R.id.favorits);

        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    startActivity( new Intent( Favorities_Activity.this, MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                if(item.getItemId() == R.id.compare){
                    startActivity( new Intent( Favorities_Activity.this, Compare_Acrivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                if(item.getItemId() == R.id.favorits){
                    return true;
                }
                if(item.getItemId() == R.id.profile){
                    if(FirebaseAuth.getInstance().getCurrentUser() == null){
                        startActivity( new Intent( Favorities_Activity.this, Login_Activity.class));
                        overridePendingTransition(0,0);
                    }else{
                        startActivity( new Intent( Favorities_Activity.this, Account_Activity.class));
                        overridePendingTransition(0,0);
                    }

                    return true;
                }
                return false;
            }
        });
        loadFavoritre();
    }


    private void loadFavoritre(){
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            return;
        }
        Log.i("INFO","load");
        firebaseFirestore.collection("users").document(firebaseAuth.getCurrentUser().getUid().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    Log.i("INFO","userload");
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()){
                        List<String> ids = doc.toObject(UserDataClass.class).Favorites;
                        List<ItemsModel> list = new ArrayList<>();
                        List<Task> tasks = new ArrayList<>();
                        for(String id : ids){
                            Log.i("INFO","load item " + id);
                            if (!Objects.equals(id, "")) {
                                tasks.add(firebaseFirestore.collection("Items").document(id).get());
                            }

                        }
                        Task mergetask = Tasks.whenAllSuccess(tasks).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                            @Override
                            public void onSuccess(List<Object> objects) {
                                Log.i("INFO","merging");
                                for(Object ob : objects){
                                    ItemsModel model = ((DocumentSnapshot) ob).toObject(ItemsModel.class);
                                    model.setIditeam(((DocumentSnapshot) ob).getId().toString());
                                    list.add(model);
                                }
                                RecommendedAdapt adapt = new RecommendedAdapt(list);
                                GridLayoutManager horizontalLayoutManager = new GridLayoutManager(Favorities_Activity.this,2);
                                recyclerView.setLayoutManager(horizontalLayoutManager);
                                recyclerView.setAdapter(adapt);
                                Log.i("INFO","loadedall");
                            }
                        });


                    }

                }
            }
        });

    }
    protected void onResume(){
        super.onResume();
        loadFavoritre();
    }
}