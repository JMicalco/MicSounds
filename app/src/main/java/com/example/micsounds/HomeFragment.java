package com.example.micsounds;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {
    ArrayList<InstrumentCategory> listaInstrumentos;
    RecyclerView recyclerView;
    Button fav, logout;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_navegation,container,false);
        recyclerView=view.findViewById(R.id.recyclerViewInstruments);
        listaInstrumentos=new ArrayList<>();
        llenarInstrumentos();
        InstrumentsAdapter instrumentsAdapter=new InstrumentsAdapter(listaInstrumentos, this);
        LinearLayoutManager llm=new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(instrumentsAdapter);

        //fav = view.findViewById(R.id.button2);
        //logout = view.findViewById(R.id.button5);
        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
            }
        });*/
        return view;
    }
    private void llenarInstrumentos() {
        listaInstrumentos.add(new InstrumentCategory("Guitars",R.drawable.guitars));
        listaInstrumentos.add(new InstrumentCategory("Bass", R.drawable.bass));
        listaInstrumentos.add(new InstrumentCategory("Drums", R.drawable.drum));
        listaInstrumentos.add(new InstrumentCategory("Keyboards", R.drawable.keyboard));
    }

    @Override
    public void onClick(View view) {
        int pos =recyclerView.getChildViewHolder(view).getAdapterPosition();
        if (listaInstrumentos.get(pos).nombreInstrumento.equals("Guitars")){
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new GuitarCategoriesFragment()).addToBackStack(null).commit();
        } else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Drums")) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new DrumsCategoriesFragment()).addToBackStack(null).commit();
        } else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Bass")) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new BassFragment()).addToBackStack(null).commit();
        } else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Keyboards")) {
            getFragmentManager().beginTransaction().replace(R.id.fragment_container, new KeyboardsFragment()).addToBackStack(null).commit();
        }
    }

}
