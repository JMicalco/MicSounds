package com.example.micsounds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Checkout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
    }

    public void pagar(View view){
        Toast.makeText(Checkout.this, "Pagado",Toast.LENGTH_SHORT).show();
        //Codigo para agrgar historial de pedido de compra al usuario


        Intent intent=new Intent(Checkout.this, Navigation.class);
        startActivity(intent);
    }

}