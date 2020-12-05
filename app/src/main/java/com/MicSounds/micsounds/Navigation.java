package com.MicSounds.micsounds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Navigation extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {



    ArrayList<InstrumentCategory> listaInstrumentos;
    RecyclerView recyclerView;
    Button fav, logout;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DrawerLayout drawerLayout;
    FirebaseUser user_id;
    private DatabaseReference mDatabase;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_navegation);
        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar=findViewById(R.id.toolbarv);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user_id = mAuth.getCurrentUser();
        try {
            updateDrawer();
        } catch (StorageException e) {
            Log.w("Storage", "Hey");
        } catch (IOException e){
            Log.w("IO", "Hey");
        }

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } /*else if(getSupportFragmentManager().findFragmentById(R.id.fragment_container)!=null) {
            getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentById(R.id.fragment_container)).commit();
        }*/ else {
            super.onBackPressed();
        }
    }

    public void gotoFav(View v){
        Intent intent=new Intent(Navigation.this, Favorites.class);
        startActivity(intent);
    }
    public void goToCarrito(View v){
        Intent intent=new Intent(Navigation.this, Cart.class);
        startActivity(intent);
    }

    public void goToOrders(View v){
        Intent intent = new Intent(Navigation.this, Orders.class);
        startActivity(intent);
    }



    @Override
    public void onClick(View v) {
        int pos =recyclerView.getChildViewHolder(v).getAdapterPosition();
        Intent intentGuitars= new Intent(this, GuitarsCategories.class);
        Intent intentDrums= new Intent(this, DrumsCategories.class);
        Intent intentBass= new Intent(this, Bass.class);
        Intent intentKeyboards=new Intent(this, Keyboards.class);
        if (listaInstrumentos.get(pos).nombreInstrumento.equals("Guitars")){
            startActivity(intentGuitars);
        } else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Drums")) {
            startActivity(intentDrums);
        } else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Bass")) {
            startActivity(intentBass);
        } else if (listaInstrumentos.get(pos).nombreInstrumento.equals("Keyboards")) {
            startActivity(intentKeyboards);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                break;
            case R.id.nav_favorites:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FavoritesFragment()).commit();
                break;
            case R.id.nav_cart:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
                break;
            case R.id.nav_orders:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OrdersFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void  updateDrawer() throws StorageException, IOException, FileNotFoundException {
        NavigationView navigationView=findViewById(R.id.nav_view);
        View headerView=navigationView.getHeaderView(0);
        TextView userMail= headerView.findViewById(R.id.user_mail);
        TextView userName= headerView.findViewById(R.id.user_Name);
        ImageView userImage=headerView.findViewById(R.id.user_Image);
        userMail.setText(user_id.getEmail());
        if(user_id.getDisplayName()!=null){
            userName.setText(user_id.getDisplayName());
        }

        String user_idd=FirebaseAuth.getInstance().getCurrentUser().getUid();
        String url="https://firebasestorage.googleapis.com/v0/b/micsounds-mobile.appspot.com/o/profileImages%2F"+user_idd+".jpeg",
                urlEmpty="https://firebasestorage.googleapis.com/v0/b/micsounds-mobile.appspot.com/o/profileImages%2F.jpeg";
        if(url==urlEmpty){userImage.setImageResource(R.drawable.bass);
        } else {
            StorageReference storageReference=FirebaseStorage.getInstance().getReferenceFromUrl("gs://com.MicSounds.com.MicSounds.com.MicSounds.micsounds-mobile.appspot.com/profileImages/"+user_idd+".jpeg");
            Task storageTask=storageReference.getDownloadUrl();
            Log.w("heyy", url+"?alt=media&token="+storageTask);
            GlideApp.with(this)
                    .load(url+"?alt=media&token="+storageTask)
                    .into(userImage);
        }


    }
}