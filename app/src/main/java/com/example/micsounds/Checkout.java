package com.example.micsounds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Checkout extends AppCompatActivity {

    private ArrayList<Population> populationsList;
    private DatabaseReference myARef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mAuth = FirebaseAuth.getInstance();
        populationsList = new ArrayList<>();

        myARef = FirebaseDatabase.getInstance().getReference("Users");

        // Clear ArrayList
        ClearAll();

        // Get Data Method
        GetDataFromFirebase();
    }

    private void GetDataFromFirebase() {

        String user_id = mAuth.getCurrentUser().getUid();

        //Aqui se pone el instrumento
        // Firebase
        DatabaseReference myBRef = myARef.child(user_id);
        DatabaseReference myRef  = myBRef.child("Cart"); //----- CHANGE INSTANCE -----
        Query query = myRef; //----- CHANGE INSTANCE -----

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (!snapshot.hasChildren()) {
                        continue;
                    }

                    Population population = new Population();
                    population.setImageUrl(snapshot.child("image").getValue().toString());
                    population.setName(snapshot.child("name").getValue().toString());
                    population.setPrice(Integer.parseInt(snapshot.child("price").getValue().toString()));

                    populationsList.add(population);
                }

                /*recyclerAdapter = new CarritoAdapter(getApplicationContext(), populationsList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void ClearAll() {
        if(populationsList != null) {
            populationsList.clear();

            /*if(recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }*/
        }

        populationsList = new ArrayList<>();
    }

    public void pagar(View view){
        Toast.makeText(Checkout.this, "Pagado",Toast.LENGTH_SHORT).show();
        //Codigo para agrgar historial de pedido de compra al usuario


        Intent intent=new Intent(Checkout.this, Navigation.class);
        startActivity(intent);
    }

}