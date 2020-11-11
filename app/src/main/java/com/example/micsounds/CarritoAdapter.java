package com.example.micsounds;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<Population> populationArrayList, copylist;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private boolean cart;

    public CarritoAdapter (Context mContext, ArrayList<Population> populationArrayList) {
        this.mContext = mContext;
        this.populationArrayList = populationArrayList;
        this.copylist = new ArrayList<>(populationArrayList);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    public String give$format(double num) {

        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        return "MX: " + defaultFormat.format(num);

    }

    @NonNull
    @Override
    public CarritoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carrito_item, parent, false);




        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TextView
        holder.textView.setText(populationArrayList.get(position).getName());

        // price
        holder.textView3.setText(give$format(populationArrayList.get(position).getPrice()));

        // ImageView : Glide Library
        // Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView);

        Glide.with(mContext)
                .load(populationArrayList.get(position).getImageUrl())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return populationArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return guitarFilter;
    }



    private Filter guitarFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Population> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(copylist);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Population item: copylist){
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            populationArrayList.clear();
            populationArrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView textView;
        TextView textView3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewCarrito);
            textView  = itemView.findViewById(R.id.textViewC1);
            textView3 = itemView.findViewById(R.id.textViewC2);
        }



        @Override
        public void onClick(View view) {

        }
    }

}
