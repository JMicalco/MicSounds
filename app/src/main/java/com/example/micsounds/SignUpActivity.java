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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    EditText user;
    EditText password;
    Button register;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mAuth = FirebaseAuth.getInstance();
        user = findViewById(R.id.editTextTextPersonName3);
        password = findViewById(R.id.editTextTextPassword2);
        register = findViewById(R.id.button6);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = mAuth.getCurrentUser();
                if(mUser != null){
                    Toast.makeText(SignUpActivity.this, "you are logged in", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUpActivity.this, Navigation.class);
                    startActivity(intent);
                } else{
                    Toast.makeText(SignUpActivity.this, "please sign up", Toast.LENGTH_SHORT).show();
                }
            }
        };

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = user.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    user.setError("Please enter email");
                    user.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("enter password");
                    password.requestFocus();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this, "Sign Up error", Toast.LENGTH_SHORT).show();

                            } else {
                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                                DatabaseReference favorites_user_db = current_user_db.child("Favorites");

                                DatabaseReference cart_user_db = current_user_db.child("Cart");



                                Map favorites = new HashMap();
                                favorites.put("empty", "No favorites");

                                Map cart = new HashMap();
                                cart.put("empty", "Cart empty");

                                favorites_user_db.setValue(favorites);
                                cart_user_db.setValue(cart);

                                Intent intent = new Intent(SignUpActivity.this, Navigation.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "ERROR OCURRED!", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

}