package com.example.micsounds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    EditText user;
    EditText password;
    Button login;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        user = findViewById(R.id.editTextTextEmailAddress2);
        password = findViewById(R.id.editTextTextPassword2);
        login = findViewById(R.id.button3);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = mAuth.getCurrentUser();
                if(mUser != null){
                    Toast.makeText(MainActivity.this, "you are logged in", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(MainActivity.this, Navegation.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(MainActivity.this, "please login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    user.setError("Please enter email");
                    user.requestFocus();
                }else if(pwd.isEmpty()){
                    password.setError("enter password");
                    password.requestFocus();
                }else if(!(email.isEmpty() && pwd.isEmpty())){
                    mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Login error", Toast.LENGTH_SHORT).show();

                            } else{
                                Intent intent=new Intent(MainActivity.this, Navegation.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else{
                    Toast.makeText(MainActivity.this, "ERROR OCURRED!", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void Login(View view){
        //Intent intent=new Intent(this, Navegation.class);
       // startActivity(intent);
    }


}