package com.example.shamim.nstubds;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchForDonors extends AppCompatActivity {


    ImageView buttonSearch;
    Spinner BloodGroupSpinner;
    RecyclerView showSearchResult;

    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_donors);


        Toolbar toolbar = (Toolbar) findViewById(R.id.searchtoolbar);
        toolbar.setTitle("Search For Donors");
        toolbar.setTitleTextColor(getResources().getColor(R.color.greyLight));
        setSupportActionBar(toolbar);


        buttonSearch = (ImageView) findViewById(R.id.searchButton);
        showSearchResult = (RecyclerView) findViewById(R.id.searchRecyclerView);
        BloodGroupSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);


     /// adding divided into RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
       DividerItemDecoration itemDecoration = new DividerItemDecoration(this,layoutManager.getOrientation());
        showSearchResult.setHasFixedSize(true);
        showSearchResult.setLayoutManager(layoutManager);
        showSearchResult.addItemDecoration(itemDecoration);
        /////




        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = BloodGroupSpinner.getSelectedItem().toString();
                firebaseUserSearch(searchText);
            }
        });
    }

    private void firebaseUserSearch(String searchText) {

        Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();

        Query firebaseSearchQuery = mdatabase.orderByChild("blood_group").startAt(searchText).endAt(searchText+"\uf8ff");



        FirebaseRecyclerAdapter<users, usersviewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<users, usersviewHolder>
                (
                        users.class,
                        R.layout.list_layout,
                        usersviewHolder.class,
                        firebaseSearchQuery

                ) {
            @Override
            protected void populateViewHolder(usersviewHolder viewHolder, final users model, int position) {


                viewHolder.setDetails(getApplicationContext(),model.getFirst_name(),model.getLast_name(),model.getBlood_group(),model.getImage()
                ,model.getDepartment_name(),model.getPhone_number());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(SearchForDonors.this,SendNotification.class);
                        intent.putExtra("userId",model.getUserId());
                        startActivity(intent);
                    }
                });
            }





        };

        showSearchResult.setAdapter(firebaseRecyclerAdapter);
    }


    public static class usersviewHolder extends RecyclerView.ViewHolder{

         View mView;
        public usersviewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }


        public void setDetails(Context Ctx,String FstName, String LstName, String bloodGrp, String userImage,String Dept, String PhnNum){

            TextView bld_group = (TextView) mView.findViewById(R.id.textViewBlood);
            TextView Name= (TextView) mView.findViewById(R.id.textViewName);
            TextView PhoneNum = (TextView) mView.findViewById(R.id.textViewNum);
            TextView Department = (TextView) mView.findViewById(R.id.textViewDept);
            ImageView Image = (ImageView) mView.findViewById(R.id.showImage);

            String userName = String.format("%s %s",FstName,LstName);

            Name.setText(userName);
            bld_group.setText(bloodGrp);
            PhoneNum.setText(PhnNum);
            Department.setText(Dept);
            Glide.with(Ctx).load(userImage).into(Image);

        }
    }
}
