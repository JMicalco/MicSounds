package com.example.micsounds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

public class MainActivity extends AppCompatActivity {


    EditText user;
    EditText password;
    Button login, register;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        //user = findViewById(R.id.editTextTextEmailAddress2);
        // password = findViewById(R.id.editTextTextPassword2);
        login = findViewById(R.id.button3);
        register = findViewById(R.id.button);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = mAuth.getCurrentUser();
                if (mUser != null) {
                    Toast.makeText(MainActivity.this, "you are logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Navegation.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "please login or sign up", Toast.LENGTH_SHORT).show();
                }
            }
        };


    }

    public void goToLogin(View v) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    public void goToSignUp(View v) {
        Intent intent = new Intent(MainActivity.this, SigninActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void Login(View view) {
        //Intent intent=new Intent(this, Navegation.class);
        // startActivity(intent);
    }

}