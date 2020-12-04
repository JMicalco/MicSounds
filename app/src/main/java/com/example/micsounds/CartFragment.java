package com.example.micsounds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment {
    // Widgets
    RecyclerView recyclerView;
    SearchView searchView;
    private DatabaseReference myARef;

    // Variables
    private ArrayList<Population> populationArrayList;
    private CarritoAdapter recyclerAdapter;

    private FirebaseAuth mAuth;

    String user_id;
    DatabaseReference cart_user_db;
    Context mContext;
    private DatabaseReference mDatabase;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_carrito,container,false);
        mAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.recyclerViewCarrito); //----- CHANGE INSTANCE -----

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        /*searchView = findViewById(R.id.searchView3);
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
        });*/

        Button buttonCheCkout=view.findViewById(R.id.btnCheckOut);
        buttonCheCkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkout(view);
            }
        });

        mContext = getActivity();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Firebase
        myARef = FirebaseDatabase.getInstance().getReference("Users");

        // ArrayList:
        populationArrayList = new ArrayList<>();

        user_id = mAuth.getCurrentUser().getUid();
        cart_user_db = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(user_id).child("Cart");

        // Clear ArrayList
        ClearAll();

        // Get Data Method
        GetDataFromFirebase();
        return  view;
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

                    if(snapshot.child("amount").exists()) {
                        population.amount = Integer.parseInt(snapshot.child("amount").getValue().toString());//maybe needs a try catch
                    }



                    populationArrayList.add(population);
                }

                recyclerAdapter = new CarritoAdapter(getActivity(), populationArrayList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void ClearAll() {
        if(populationArrayList != null) {
            populationArrayList.clear();

            if(recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        }

        populationArrayList = new ArrayList<>();
    }



    public void checkout(View view) {




        /*for (int i = 0; i < populationArrayList.size(); i++) {

            Population population = populationArrayList.get(i);
            population.amount = recyclerView.getChildAdapterPosition(view).   findViewById(R.id.editTextAmount);

            populationArrayList.set(i, )

            nombres += populationArrayList.get(i).getName() + ", ";
            total += populationArrayList.get(i).getPrice();

        }*/

        // go to Checkout

        Intent intent = new Intent(getActivity(), Checkout.class);
        intent.putExtra("Carrito", populationArrayList);
        startActivity(intent);




        //Toast.makeText(getActivity(), "a!",Toast.LENGTH_SHORT).show();


    }
}
