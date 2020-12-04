package com.example.micsounds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Checkout extends AppCompatActivity {

    EditText country;
    EditText firstName;
    EditText lastName;
    EditText address;
    EditText city;
    EditText state;
    EditText email;
    EditText phone;
    EditText cardNumber;
    EditText cardCVV;
    EditText month;
    EditText year;
    CheckoutInfo checkoutInfo;

    Button pagar;

    private ArrayList<Population> populationsList;
    private DatabaseReference myARef;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    DatabaseReference cart_user_db;

    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mAuth = FirebaseAuth.getInstance();
        populationsList = new ArrayList<>();

        myARef = FirebaseDatabase.getInstance().getReference("Users");

        country = findViewById(R.id.Country);
        firstName = findViewById(R.id.FirstName);
        lastName = findViewById(R.id.LastName);
        address = findViewById(R.id.Address);
        city = findViewById(R.id.City);
        state = findViewById(R.id.State);
        email = findViewById(R.id.Email);
        phone = findViewById(R.id.Phone);
        cardNumber = findViewById(R.id.CardNumber);
        cardCVV = findViewById(R.id.CardCVV);
        month = findViewById(R.id.Month);
        year = findViewById(R.id.Year);
        pagar = findViewById(R.id.buttonPagar);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        user_id = mAuth.getCurrentUser().getUid();

        cart_user_db = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(user_id).child("Cart");

        // Clear ArrayList
        ClearAll();

        // Get Data Method
        GetDataFromFirebase();



        pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String countryStr   = country.getText().toString();
                String firstNameStr = firstName.getText().toString();
                String lastNameStr  = lastName.getText().toString();
                String addressStr  = address.getText().toString();
                String cityStr     = city.getText().toString();
                String stateStr    = state.getText().toString();
                String emailStr    = email.getText().toString();
                String phoneInt       = phone.getText().toString();
                String cardNumberInt  = cardNumber.getText().toString();
                String cardCVVInt     = cardCVV.getText().toString();
                String monthInt       = month.getText().toString();
                String yearInt        = year.getText().toString();

                if(countryStr.isEmpty()) {
                    country.setError("Please provide all the info required");
                    country.requestFocus();
                } else if(firstNameStr.isEmpty()) {
                    firstName.setError("Please provide all the info required");
                    firstName.requestFocus();
                } else if(lastNameStr.isEmpty()) {
                    lastName.setError("Please provide all the info required");
                    lastName.requestFocus();
                } else if(addressStr.isEmpty()) {
                    address.setError("Please provide all the info required");
                    address.requestFocus();
                } else if(cityStr.isEmpty()) {
                    city.setError("Please provide all the info required");
                    city.requestFocus();
                } else if(stateStr.isEmpty()) {
                    state.setError("Please provide all the info required");
                    state.requestFocus();
                } else if(emailStr.isEmpty()) {
                    email.setError("Please provide all the info required");
                    email.requestFocus();
                } else if(phoneInt.isEmpty()) {
                    phone.setError("Please provide all the info required");
                    phone.requestFocus();
                } else if(cardNumberInt.isEmpty()) {
                    cardNumber.setError("Please provide all the info required");
                    cardNumber.requestFocus();
                } else if(cardCVVInt.isEmpty()) {
                    cardCVV.setError("Please provide all the info required");
                    cardCVV.requestFocus();
                } else if(monthInt.isEmpty()) {
                    month.setError("Please provide all the info required");
                    month.requestFocus();
                } else if(yearInt.isEmpty()) {
                    year.setError("Please provide all the info required");
                    year.requestFocus();
                } else {

                    checkoutInfo = new CheckoutInfo(
                            countryStr, firstNameStr, lastNameStr, addressStr, cityStr, stateStr,
                            emailStr, phoneInt, cardNumberInt,
                            Integer.parseInt(cardCVVInt), Integer.parseInt(monthInt),
                            Integer.parseInt(yearInt)
                    );

                    pagar(checkoutInfo);



                }



            }
        });
    }

    private void GetDataFromFirebase() {

        //String user_id = mAuth.getCurrentUser().getUid();

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
                    } else {
                        population.amount = 1;
                    }

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

    public String give$format(double num) {

        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        return "MX: " + defaultFormat.format(num);

    }

    public void pagar(CheckoutInfo checkoutInfo){

        //agregar al historial

        String nombres = "";
        int total = 0;
        String date = Calendar.getInstance().getTime().toString();

        for (int i = 0; i < populationsList.size(); i++) {

            for(int j = 0; j < populationsList.get(i).amount; j++) {
                nombres += populationsList.get(i).getName() + ", ";
                total += populationsList.get(i).getPrice();
            }



        }

        String price = give$format(total);


        OrdersInfo ordersInfo = new OrdersInfo(date, nombres, price);

        DatabaseReference orders_user_db = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(user_id).child("Orders");

        String key = orders_user_db.push().getKey();

        Map<String, Object> check = checkoutInfo.toMap();

        Map<String, Object> orders = ordersInfo.toMap();

        Map<String, Object> postValues = new HashMap<>();

        postValues.putAll(orders);

        postValues.putAll(check);

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/Users/" + user_id + "/Orders/" + key, postValues);

        mDatabase.updateChildren(childUpdates);

        /*Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");*/

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
                            populationsList.remove(0);
                        }
                        //index++;

                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        Toast.makeText(Checkout.this, "Pagado",Toast.LENGTH_SHORT).show();
        //Codigo para agrgar historial de pedido de compra al usuario


        Intent intent=new Intent(Checkout.this, Navigation.class);
        startActivity(intent);
    }

}