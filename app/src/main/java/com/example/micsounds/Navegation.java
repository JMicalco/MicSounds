package com.example.micsounds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class Navegation extends AppCompatActivity implements View.OnClickListener {

    ArrayList<InstrumentCategory> listaInstrumentos;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegation);
        recyclerView=findViewById(R.id.recyclerViewInstruments);
        listaInstrumentos=new ArrayList<>();
        llenarInstrumentos();
        InstrumentsAdapter instrumentsAdapter=new InstrumentsAdapter(listaInstrumentos, this);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(instrumentsAdapter);
    }

    private void llenarInstrumentos() {
        listaInstrumentos.add(new InstrumentCategory("Guitars",R.drawable.guitars));
        listaInstrumentos.add(new InstrumentCategory("Bass", R.drawable.bass));
        listaInstrumentos.add(new InstrumentCategory("Drums", R.drawable.drum));
        listaInstrumentos.add(new InstrumentCategory("Keyboards", R.drawable.keyboard));
    }

    @Override
    public void onClick(View v) {
        int pos =recyclerView.getChildViewHolder(v).getAdapterPosition();
        Intent intentGuitars= new Intent(this, GuitarsCategories.class);
        Intent intentDrums= new Intent(this, DrumsCategories.class);
        Intent intentBass= new Intent(this, Bass.class);
        Intent intentKeyboards=new Intent(this, Keyboards.class);
        if (listaInstrumentos.get(pos).nombreInstrumento.equals("Guitars")){
            startActivity(intentGuitars);
        } else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Drums")) {
            startActivity(intentDrums);
        } else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Bass")) {
            startActivity(intentBass);
        } else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Keyboards")) {
            startActivity(intentBass);
        }
    }
}