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
    private ArrayList<OrdersInfo> ordersArrayList, copylist;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    public  PedidosAdapter(Context mContext, ArrayList<OrdersInfo> ordersArrayList) {
        this.mContext = mContext;
        this.ordersArrayList = ordersArrayList;
        this.copylist = new ArrayList<>(ordersArrayList);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public PedidosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_items, parent, false);// ---------change
        return new PedidosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosViewHolder holder, int position) {
        // TextView
        holder.fecha.setText(ordersArrayList.get(position).getDate());

        holder.items.setText(ordersArrayList.get(position).getItems());
        holder.precio.setText(ordersArrayList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return ordersArrayList.size();
    }

    public class PedidosViewHolder extends RecyclerView.ViewHolder{

        TextView fecha,
                items,
                precio;

        public PedidosViewHolder(@NonNull View itemView) {
            super(itemView);
            fecha=itemView.findViewById(R.id.textViewFecha);

            items=itemView.findViewById(R.id.textViewItems);
            precio=itemView.findViewById(R.id.textViewPrecio);
        }
    }
}
