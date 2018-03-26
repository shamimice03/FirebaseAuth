package com.example.shamim.nstubds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    GridLayout Mygrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Mygrid = (GridLayout) findViewById(R.id.myGrid);
        setSingleEvent(Mygrid);


        Toolbar toolbar = (Toolbar) findViewById(R.id.showtoolbarMenu);
        //toolbar.setTitle("NSTU BDS");
       // toolbar.setTitleTextColor(getResources().getColor(R.color.greyLight));
        setSupportActionBar(toolbar);
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
