package com.MicSounds.micsounds;

import android.content.Context;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrdersFragment extends Fragment {
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_orders,container,false);

        mAuth = FirebaseAuth.getInstance();

        recyclerView = view.findViewById(R.id.recyclerViewOrders); //----- CHANGE INSTANCE -----

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

        mContext = getActivity();

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
        return view;
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



                    if(snapshot.child("name").exists() && snapshot.child("shipment").exists()) {
                        ordersInfo.setName(snapshot.child("name").getValue().toString());
                        ordersInfo.setShipment(snapshot.child("shipment").getValue().toString());
                    }




                    ordersArrayList.add(ordersInfo);
                }

                recyclerAdapter = new PedidosAdapter(getActivity(), ordersArrayList);
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
