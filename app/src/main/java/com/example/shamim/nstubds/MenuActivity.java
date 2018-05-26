package com.example.shamim.nstubds;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    GridLayout Mygrid;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Toolbar toolbar;
    private FirebaseFirestore firebaseFirestore;
    private  FirebaseAuth mAuth;
    String UserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Mygrid = (GridLayout) findViewById(R.id.myGrid);
        setSingleEvent(Mygrid);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();

        setUpToolbar();

        navigationView = (NavigationView) findViewById(R.id.navView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.Settings:
                        Toast.makeText(MenuActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.Rate:
                        Toast.makeText(MenuActivity.this, "Rate", Toast.LENGTH_SHORT).show();
                        break;



                    case R.id.logOut:
                        Toast.makeText(MenuActivity.this, "Logging Out ", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MenuActivity.this,MainActivity.class));
                        finish();
                        break;
                }

                return false;
            }
        });

    }

    private void  setUpToolbar(){

        drawerLayout = (DrawerLayout) findViewById(R.id.draw_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.showtoolbarMenu);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    private void setSingleEvent(GridLayout mygrid) {

        for (int i=0; i<mygrid.getChildCount(); i++){

            CardView cardView = (CardView) mygrid.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {

                    if(finalI == 0){
                        startActivity(new Intent(MenuActivity.this,ShowProfileActivity.class));
                    }
                    else  if(finalI == 1){
                        startActivity(new Intent(MenuActivity.this,SearchForDonors.class));
                    }
                    else  if(finalI == 2){
                        startActivity(new Intent(MenuActivity.this,PostForBlood.class));
                    }
                    else  if(finalI == 3){
                        startActivity(new Intent(MenuActivity.this,TipsForDonors.class));
                    }
                    else if(finalI == 4){
                        startActivity(new Intent(MenuActivity.this,AboutMe.class));
                    }
                    else if(finalI == 5){
                        startActivity(new Intent(MenuActivity.this,Feedback.class));
                    }
                }


            });
        }
    }
}
