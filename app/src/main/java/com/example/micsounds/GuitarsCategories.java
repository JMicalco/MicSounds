package com.example.micsounds;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class GuitarsCategories extends AppCompatActivity implements View.OnClickListener {
    ArrayList<InstrumentCategory> listaInstrumentos;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guitars_categories);
        recyclerView=findViewById(R.id.recyclerView);
        listaInstrumentos=new ArrayList<>();
        llenarInstrumentos();
        InstrumentsAdapter instrumentsAdapter=new InstrumentsAdapter(listaInstrumentos,this);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(instrumentsAdapter);
    }

    private void llenarInstrumentos() {
        listaInstrumentos.add(new InstrumentCategory("Electric Guitars",R.drawable.electricguitarcategory));
        listaInstrumentos.add(new InstrumentCategory("Accoustic Guitars", R.drawable.accousticguitarcategory));
        listaInstrumentos.add(new InstrumentCategory("Classical Guitars", R.drawable.classicalguitarcategory));
    }

    public void enterCategory(View v) {

    }

    @Override
    public void onClick(View v) {

    }
}