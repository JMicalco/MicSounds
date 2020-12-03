package com.example.micsounds;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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

    String user_id;
    DatabaseReference cart_user_db;

    public CarritoAdapter (Context mContext, ArrayList<Population> populationArrayList) {
        this.mContext = mContext;
        this.populationArrayList = populationArrayList;
        this.copylist = new ArrayList<>(populationArrayList);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user_id = mAuth.getCurrentUser().getUid();
        cart_user_db = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(user_id).child("Cart");
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
        Button eliminar;
        EditText amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewCarrito);
            textView = itemView.findViewById(R.id.textViewC1);
            textView3 = itemView.findViewById(R.id.textViewC2);
            eliminar = itemView.findViewById(R.id.btnEliminar);
            amount = itemView.findViewById(R.id.editTextAmount);

            eliminar.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            if (view.getId() == eliminar.getId()) {

                Query query = cart_user_db; //----- CHANGE INSTANCE -----

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                        int index = getAdapterPosition();

                        if(index != -1) {

                            //Log.wtf("adapter",getAdapterPosition() + "");

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                                if (!snapshot.hasChildren()) {
                                    continue;
                                }


                                if (snapshot.child("name").getValue().
                                        equals(populationArrayList.get(index).
                                                getName())) {
                                    snapshot.getRef().removeValue();
                                    populationArrayList.remove(index);
                                    break;
                                }

                                //index++;

                            }

//                        recyclerAdapter = new CarritoAdapter(getApplicationContext(), populationsList);
//                        recyclerView.setAdapter(recyclerAdapter);
//                        recyclerAdapter.notifyDataSetChanged();

                            Toast.makeText(mContext, "borrado del carrito", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });




                //cart_user_db.removeValue();

            }
        }
    }
}
