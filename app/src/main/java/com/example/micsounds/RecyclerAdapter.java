package com.example.micsounds;

import android.content.Context;
import android.content.Intent;
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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<Population> populationArrayList, copylist;

    public RecyclerAdapter(Context mContext, ArrayList<Population> populationArrayList) {
        this.mContext = mContext;
        this.populationArrayList = populationArrayList;
        this.copylist = new ArrayList<>(populationArrayList);
    }

    public String give$format(double num) {

        NumberFormat defaultFormat = NumberFormat.getCurrencyInstance();
        return "MX: " + defaultFormat.format(num);

    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.population_item, parent, false);

        Button btnCarrito= view.findViewById(R.id.btnCarrito2);
        btnCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Carrito", Toast.LENGTH_SHORT).show();
                //Log.e("hola","hola");
            }
        });
        Button btnFav= view.findViewById(R.id.btnFav);
        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Favoritos", Toast.LENGTH_SHORT).show();
            }
        });
        Button btnShare= view.findViewById(R.id.btnCompartir);
        final String shareView= "info para compartir";
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "Compartir", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/pain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"ITEM");
                intent.putExtra(Intent.EXTRA_TEXT,shareView);
                mContext.startActivity(intent.createChooser(intent,"Compartir"));
            }
        });
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        TextView textView3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView  = itemView.findViewById(R.id.textView_2);
            textView3 = itemView.findViewById(R.id.textView_3);
        }
    }

}
