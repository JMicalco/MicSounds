package com.example.micsounds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Keyboards extends AppCompatActivity { //----- CHANGE INSTANCE -----

    // Widgets
    RecyclerView recyclerView;
    SearchView searchView;

    private DatabaseReference myARef;

    // Variables
    private ArrayList<Population> populationsList;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboards); //----- CHANGE INSTANCE -----

        recyclerView = findViewById(R.id.recyclerViewKeyboards); //----- CHANGE INSTANCE -----

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchView = findViewById(R.id.searchView7);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        // Firebase
        myARef = FirebaseDatabase.getInstance().getReference("Global");

        // ArrayList:
        populationsList = new ArrayList<>();

        // Clear ArrayList
        ClearAll();

        // Get Data Method
        GetDataFromFirebase();
    }

    private void GetDataFromFirebase() {

        //Aqui se pone el instrumento
        // Firebase
        DatabaseReference myRef = myARef.child("Population");
        final Query query = myRef.child("Keyboards"); //----- CHANGE INSTANCE -----

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Population population = new Population();

                    population.setImageUrl(snapshot.child("image").getValue().toString());
                    population.setName(snapshot.child("name").getValue().toString());
                    population.setPrice(Integer.parseInt(snapshot.child("price").getValue().toString()));

                    populationsList.add(population);
                }

                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), populationsList, query.toString());
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void ClearAll() {
        if(populationsList != null) {
            populationsList.clear();

            if(recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        }

        populationsList = new ArrayList<>();
    }
}