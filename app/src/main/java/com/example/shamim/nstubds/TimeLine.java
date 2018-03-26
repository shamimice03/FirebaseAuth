package com.example.shamim.nstubds;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class TimeLine extends AppCompatActivity {

    BottomNavigationView mainbottomNav;

    private HomeFragment homeFragment;
    private NotificationFragment notificationFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        Toolbar toolbar = (Toolbar) findViewById(R.id.timelinetoolbar);
        toolbar.setTitle("Post Blood Request");
        toolbar.setTitleTextColor(getResources().getColor(R.color.greyLight));
        setSupportActionBar(toolbar);

        mainbottomNav = (BottomNavigationView) findViewById(R.id.mainBottomNav);

        homeFragment = new HomeFragment();
        notificationFragment= new NotificationFragment();

        replaceFragment(homeFragment);

        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.homeButton:
                        replaceFragment(homeFragment);
                        return true;
                    case R.id.notificationButton:
                        replaceFragment(notificationFragment);
                        return  true;
                    case R.id.addPostButton:
                        startActivity(new Intent(TimeLine.this,MenuActivity.class));
                        finish();
                        return  true;

                    default:
                        return false;
                }

            }
        });

    }

    private  void replaceFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container,fragment);
        fragmentTransaction.commit();

    }
}
