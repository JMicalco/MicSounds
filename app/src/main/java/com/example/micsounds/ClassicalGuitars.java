package com.example.micsounds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClassicalGuitars extends AppCompatActivity  { //----- CHANGE INSTANCE -----

    // Widgets
    RecyclerView recyclerView;

    private DatabaseReference myARef;

    // Variables
    private ArrayList<Population> populationsList, copylist;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classical_guitars); //----- CHANGE INSTANCE -----

        recyclerView = findViewById(R.id.recyclerViewClassicalGuitars); //----- CHANGE INSTANCE -----

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchView = findViewById(R.id.searchView2);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        DatabaseReference myBRef = myARef.child("Population");
        DatabaseReference myRef  = myBRef.child("Guitars"); //----- CHANGE INSTANCE -----
        Query query = myRef.child("Classic"); //----- CHANGE INSTANCE -----

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
                copylist = new ArrayList<>(populationsList);
                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), populationsList);
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

    SearchView searchView;


}