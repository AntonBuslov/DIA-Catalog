package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Register_Activity extends AppCompatActivity {

    EditText emailText, passwordText, confirmPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        confirmPasswordText = findViewById(R.id.confirmPasswordText);
    }

    private boolean doesPasswordSame(String password, String password2){
        return password.equals(password2);
    }

    private boolean CheckPassword(String password){
        if(password.length() < 8){
            passwordText.setError("Password must be 8 characters or longer");
            return false;
        }
        boolean hasSpecialCharacter = false;
        boolean hasCapitalCharacter = false;
        boolean hasNumbersCharacter = false;
        boolean hasSpaceCharacter = false;

        for (char ch : password.toCharArray()){
            if(Character.isUpperCase(ch)){
                hasCapitalCharacter = true;
            }
            if(Character.isDigit(ch)){
                hasNumbersCharacter = true;
            }
            if(Character.isSpaceChar(ch)){
                hasSpaceCharacter = true;
            }
            if("!@#$%^&*()".indexOf(ch) != -1){
                hasSpecialCharacter = true;
            }

        }

        if(!hasCapitalCharacter){
            passwordText.setError("Password must contain capital letters");
            return false;
        }
        if(!hasNumbersCharacter){
            passwordText.setError("Password must contain numbers");
            return false;
        }
        if(!hasSpecialCharacter){
            passwordText.setError("\"Password must contain special characters");
            return false;
        }
        if(hasSpaceCharacter){
            passwordText.setError("Password mustn't contain spaces");
            return false;
        }
        return true;

    }


    public void onRegisterClick(View view){

        if(!CheckPassword(passwordText.getText().toString())){
            return;
        }
        if(!doesPasswordSame(passwordText.getText().toString(), confirmPasswordText.getText().toString())){
            confirmPasswordText.setError("passwords must be the same");
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText.getText().toString()).matches()){
            emailText.setError("email is not correct");
            return;
        }

        CreateFirebaseAccount(emailText.getText().toString(),passwordText.getText().toString());
    }

    private void CreateFirebaseAccount(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        Toast.makeText(Register_Activity.this,"Іефке", Toast.LENGTH_SHORT).show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Register_Activity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register_Activity.this,"Registration sucsesfful", Toast.LENGTH_SHORT).show();
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            finish();
                        }else{
                            Toast.makeText(Register_Activity.this,task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    public void LoginClic(View view){
        startActivity( new Intent( Register_Activity.this, Login_Activity.class));
        finish();
    }
}
