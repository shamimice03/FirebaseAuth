package com.example.shamim.nstubds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class PostForBlood extends AppCompatActivity {

    Button PostGive,SeePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_for_blood);


        Toolbar toolbar = (Toolbar) findViewById(R.id.posttoolbar);
        toolbar.setTitle("Post for Blood");
        toolbar.setTitleTextColor(getResources().getColor(R.color.greyLight));
        setSupportActionBar(toolbar);

        PostGive = (Button) findViewById(R.id.postGive);
        SeePost = (Button) findViewById(R.id.seePost);

        PostGive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostForBlood.this,PostGive.class));
            }
        });

        SeePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(PostForBlood.this,TimeLine.class));
            }
        });

    }
}
