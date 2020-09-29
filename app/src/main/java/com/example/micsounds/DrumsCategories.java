package com.example.micsounds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class DrumsCategories extends AppCompatActivity implements View.OnClickListener {
    ArrayList<InstrumentCategory> listaInstrumentos;
    RecyclerView recyclerViewDrums;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drums_categories);
        recyclerViewDrums =findViewById(R.id.recycleViewDrums);
        listaInstrumentos=new ArrayList<>();
        llenarInstrumentos();
        InstrumentsAdapter instrumentsAdapter=new InstrumentsAdapter(listaInstrumentos,this);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDrums.setLayoutManager(llm);
        recyclerViewDrums.setAdapter(instrumentsAdapter);
    }

    private void llenarInstrumentos() {
        listaInstrumentos.add(new InstrumentCategory("Electric Drums",R.drawable.electricdrum));
        listaInstrumentos.add(new InstrumentCategory("Acoustic Drums", R.drawable.drum));
    }

    @Override
    public void onClick(View view) {
        int pos =recyclerViewDrums.getChildViewHolder(view).getAdapterPosition();
        Intent intentElectricDrums= new Intent(this, ElectricDrums.class);
        Intent intentAcousticDrums= new Intent(this, AcousticDrums.class);
        if (listaInstrumentos.get(pos).nombreInstrumento.equals("Electric Drums")){
            startActivity(intentElectricDrums);
        } else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Acoustic Drums")) {
            startActivity(intentAcousticDrums);
        }
    }
}