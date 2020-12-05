package com.MicSounds.micsounds;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GuitarCategoriesFragment extends Fragment implements View.OnClickListener {
    ArrayList<InstrumentCategory> listaInstrumentos;
    RecyclerView recyclerViewGuitars;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_guitars_categories,container,false);
        recyclerViewGuitars =view.findViewById(R.id.recyclerViewGuitars);
        listaInstrumentos=new ArrayList<>();
        llenarInstrumentos();
        InstrumentsAdapter instrumentsAdapter=new InstrumentsAdapter(listaInstrumentos,this);
        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewGuitars.setLayoutManager(llm);
        recyclerViewGuitars.setAdapter(instrumentsAdapter);
        return view;
    }

    @Override
    public void onClick(View view) {
        int pos =recyclerViewGuitars.getChildViewHolder(view).getAdapterPosition();
        if (listaInstrumentos.get(pos).nombreInstrumento.equals("Electric Guitars")){
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ElectricGuitarsFragment()).addToBackStack(null).commit();
            //startActivity(intentElectricGuitars);
        } else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Acoustic Guitars")) {
            //startActivity(intentAcousticGuitars);
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AcousticGuitarsFragment()).addToBackStack(null).commit();
        }else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Classical Guitars")) {
            //startActivity(intentClassicalGuitars);
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ClassicalGuitarsFragment()).addToBackStack(null).commit();
        }
    }

    private void llenarInstrumentos() {
        listaInstrumentos.add(new InstrumentCategory("Electric Guitars",R.drawable.electricguitarcategory));
        listaInstrumentos.add(new InstrumentCategory("Acoustic Guitars", R.drawable.accousticguitarcategory));
        listaInstrumentos.add(new InstrumentCategory("Classical Guitars", R.drawable.classicalguitarcategory));
    }
}
