package com.example.micsounds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Orders extends AppCompatActivity { //----- CHANGE INSTANCE -----

    // Widgets
    RecyclerView recyclerView;
    SearchView searchView;
    private DatabaseReference myARef;

    // Variables
    private ArrayList<OrdersInfo> ordersArrayList;


    private PedidosAdapter recyclerAdapter;

    private FirebaseAuth mAuth;

    String user_id;
    DatabaseReference orders_user_db;
    Context mContext;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders); //----- CHANGE INSTANCE -----

        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerViewOrders); //----- CHANGE INSTANCE -----

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
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

        mContext = this;

        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Firebase
        myARef = FirebaseDatabase.getInstance().getReference("Users");

        // ArrayList:
        ordersArrayList = new ArrayList<>();

        user_id = mAuth.getCurrentUser().getUid();
        orders_user_db = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(user_id).child("Orders");

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
        DatabaseReference myRef  = myBRef.child("Orders"); //----- CHANGE INSTANCE -----
        Query query = myRef; //----- CHANGE INSTANCE -----

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if (!snapshot.hasChildren()) {
                        continue;
                    }

                    OrdersInfo ordersInfo= new OrdersInfo();
                    ordersInfo.setItems(snapshot.child("items").getValue().toString());
                    ordersInfo.setDate(snapshot.child("date").getValue().toString());
                    ordersInfo.setPrice(snapshot.child("price").getValue().toString());



                    ordersArrayList.add(ordersInfo);
                }

                recyclerAdapter = new PedidosAdapter(getApplicationContext(), ordersArrayList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void ClearAll() {
        if (ordersArrayList != null) {
            ordersArrayList.clear();

            if (recyclerAdapter != null) {
                recyclerAdapter.notifyDataSetChanged();
            }
        }

        ordersArrayList = new ArrayList<>();
    }



}