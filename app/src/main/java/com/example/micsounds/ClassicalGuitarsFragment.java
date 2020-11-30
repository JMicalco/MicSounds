package com.example.micsounds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClassicalGuitarsFragment extends Fragment {

    RecyclerView recyclerView;

    private DatabaseReference myARef;

    // Variables
    private ArrayList<Population> populationsList, copylist;
    private RecyclerAdapter recyclerAdapter;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_classical_guitars,container,false);

        recyclerView = view.findViewById(R.id.recyclerViewClassicalGuitars); //----- CHANGE INSTANCE -----

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        searchView = view.findViewById(R.id.searchView2);
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


        return view;
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
                recyclerAdapter = new RecyclerAdapter(getActivity(), populationsList);
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
