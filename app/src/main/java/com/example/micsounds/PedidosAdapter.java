package com.example.micsounds;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.PedidosViewHolder> {
    @NonNull

    private Context mContext;
    private ArrayList<OrdersInfo> populationArrayList, copylist;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private boolean cart;

    public  PedidosAdapter(Context mContext, ArrayList<OrdersInfo> populationArrayList) {
        this.mContext = mContext;
        this.populationArrayList = populationArrayList;
        this.copylist = new ArrayList<>(populationArrayList);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        cart = false;
    }

    public PedidosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_items, parent, false);// ---------change
        return new PedidosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosViewHolder holder, int position) {
        // TextView
        holder.fecha.setText(populationArrayList.get(position).getDate());
        holder.hora.setText(populationArrayList.get(position).getHours());
        holder.items.setText(populationArrayList.get(position).getItems());
        holder.precio.setText(populationArrayList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return populationArrayList.size();
    }

    public class PedidosViewHolder extends RecyclerView.ViewHolder{

        TextView fecha,
                hora,
                items,
                precio;

        public PedidosViewHolder(@NonNull View itemView) {
            super(itemView);
            fecha=itemView.findViewById(R.id.textViewFecha);
            hora=itemView.findViewById(R.id.textViewHora);
            items=itemView.findViewById(R.id.textViewItems);
            precio=itemView.findViewById(R.id.textViewPrecio);
        }
    }
}
