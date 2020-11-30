package com.example.micsounds;

import android.content.Intent;
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

public class DrumsCategoriesFragment extends Fragment implements View.OnClickListener {
    ArrayList<InstrumentCategory> listaInstrumentos;
    RecyclerView recyclerViewDrums;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_drums_categories,container,false);
        recyclerViewDrums =view.findViewById(R.id.recycleViewDrums);
        listaInstrumentos=new ArrayList<>();
        llenarInstrumentos();
        InstrumentsAdapter instrumentsAdapter=new InstrumentsAdapter(listaInstrumentos,this);
        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewDrums.setLayoutManager(llm);
        recyclerViewDrums.setAdapter(instrumentsAdapter);
        return view;
    }

    private void llenarInstrumentos() {
        listaInstrumentos.add(new InstrumentCategory("Electric Drums",R.drawable.electricdrum));
        listaInstrumentos.add(new InstrumentCategory("Acoustic Drums", R.drawable.drum));
    }

    public void onClick(View view) {
        int pos =recyclerViewDrums.getChildViewHolder(view).getAdapterPosition();
        if (listaInstrumentos.get(pos).nombreInstrumento.equals("Electric Drums")){
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new ElectricalDrumsFragment()).addToBackStack(null).commit();
        } else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Acoustic Drums")) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new AcousticDrumsFragment()).addToBackStack(null).commit();
        }
    }
}
