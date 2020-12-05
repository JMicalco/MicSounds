package com.MicSounds.micsounds;

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
import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity { //----- CHANGE INSTANCE -----

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito); //----- CHANGE INSTANCE -----

        mAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recyclerViewCarrito); //----- CHANGE INSTANCE -----

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
        populationArrayList = new ArrayList<>();

        user_id = mAuth.getCurrentUser().getUid();
        cart_user_db = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(user_id).child("Cart");

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

                    populationArrayList.add(population);
                }

                recyclerAdapter = new CarritoAdapter(getApplicationContext(), populationArrayList);
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

    public String give$format(double num) {

        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        return "MX: " + defaultFormat.format(num);

    }

    public void checkout(View view) {

        //agregar al historial

        String nombres = "";
        int total = 0;
        String date = Calendar.getInstance().getTime().toString();




        String price = give$format(total);

        OrdersInfo ordersInfo = new OrdersInfo(date, nombres, price);

        DatabaseReference orders_user_db = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(user_id).child("Orders");

        String key = orders_user_db.push().getKey();

        Map<String, Object> postValues = ordersInfo.toMap();

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/Users/" + user_id + "/Orders/" + key, postValues);

        mDatabase.updateChildren(childUpdates);




        //borrar del carrito

        Query query = cart_user_db; //----- CHANGE INSTANCE -----

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int index = 0;

                if(index != -1) {

                    //Log.wtf("adapter",getAdapterPosition() + "");

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                        if (!snapshot.hasChildren()) {
                            continue;
                        } else {
                            snapshot.getRef().removeValue();
                            populationArrayList.remove(0);
                        }
                        //index++;

                    }

//                        recyclerAdapter = new CarritoAdapter(getApplicationContext(), populationsList);
//                        recyclerView.setAdapter(recyclerAdapter);
//                        recyclerAdapter.notifyDataSetChanged();

                    //Toast.makeText(mContext, "borrado del carrito", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        Toast.makeText(this, "Pagado!",Toast.LENGTH_SHORT).show();

        /*Intent intent = new Intent(this, Checkout.class);
        //intent.putExtra("Carrito", populationsList);
        startActivity(intent);*/
    }

}