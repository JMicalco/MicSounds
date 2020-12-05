package com.MicSounds.micsounds;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InstrumentsAdapter extends RecyclerView.Adapter<InstrumentsAdapter.ViewHolderInstrumentos> {

   protected ArrayList<InstrumentCategory> listaInstrumentos, copyList;
   protected View.OnClickListener listener;

    public InstrumentsAdapter(ArrayList<InstrumentCategory> listaInstrumentos, View.OnClickListener listener) {
        this.listaInstrumentos = listaInstrumentos;
        this.copyList = new ArrayList<>(listaInstrumentos);
        this.listener=listener;
    }

    @NonNull
    @Override
    public ViewHolderInstrumentos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.instruments_category,null,false);
        view.setOnClickListener(listener);
        ViewHolderInstrumentos vh=new ViewHolderInstrumentos(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderInstrumentos holder, int position) {
        holder.titulo.setText(listaInstrumentos.get(position).nombreInstrumento);
        holder.imagen.setImageResource(listaInstrumentos.get(position).foto);
    }

    @Override
    public int getItemCount() {
        return listaInstrumentos.size();
    }



    public class ViewHolderInstrumentos extends RecyclerView.ViewHolder {
        TextView titulo;
        ImageView imagen;
        public ViewHolderInstrumentos(@NonNull View itemView) {
            super(itemView);
            titulo=itemView.findViewById(R.id.textView4);
            imagen= itemView.findViewById(R.id.imageView);
        }

    };


}
