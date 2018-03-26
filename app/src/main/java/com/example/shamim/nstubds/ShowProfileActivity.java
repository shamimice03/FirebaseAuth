package com.example.shamim.nstubds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ShowProfileActivity extends AppCompatActivity {


    FirebaseAuth mAuth;
    DatabaseReference mdatabase;
    TextView updateProfile,ShowFirstName,BloodGroup,ShowDept,ShowEmail,ShowPhoneNum,ShowLastName,bloodText;
    ImageView ShowProfilePic;
    ProgressDialog progressDialog;

    public String Fst_name,Lst_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);



        Toolbar toolbar = (Toolbar) findViewById(R.id.showtoolbar);
        toolbar.setTitle("Profile");
        toolbar.setTitleTextColor(getResources().getColor(R.color.greyLight));
        setSupportActionBar(toolbar);

        progressDialog = ProgressDialog.show(ShowProfileActivity.this,null,"Loading...",false,false);


        updateProfile = (TextView) findViewById(R.id.showTextView);
        bloodText = (TextView) findViewById(R.id.bloodtext);
        ShowFirstName = (TextView) findViewById(R.id.showName);
        ShowDept = (TextView) findViewById(R.id.showDept);
        ShowEmail = (TextView) findViewById(R.id.showEmail);
        ShowPhoneNum = (TextView) findViewById(R.id.showPhoneNumber);
        BloodGroup = (TextView) findViewById(R.id.showBlood);
        ShowProfilePic = (ImageView) findViewById(R.id.showImage);



        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        showProfile();

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ShowProfileActivity.this,ProfileActivity.class));
            }
        });
    }

    private void showProfile() {



        final FirebaseUser user = mAuth.getCurrentUser();
        String user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference FullName = mdatabase.child(user_id).child("fullName");
        DatabaseReference FirstNameRef = mdatabase.child(user_id).child("first_name");
        DatabaseReference ImageRef = mdatabase.child(user_id).child("image");
        DatabaseReference DeptNameRef = mdatabase.child(user_id).child("department_name");
        DatabaseReference LastNameRef = mdatabase.child(user_id).child("last_name");
        DatabaseReference bloodGroupRef = mdatabase.child(user_id).child("blood_group");
        final DatabaseReference PhoneNumRef = mdatabase.child(user_id).child("phone_number");



        progressDialog.show();

        if (user != null) {




            String email = mAuth.getCurrentUser().getEmail();
            ShowEmail.setText(email);



            ImageRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (user.getPhotoUrl() != null) {
                        Glide.with(ShowProfileActivity.this).load(user.getPhotoUrl().toString()).into(ShowProfilePic);
                        progressDialog.dismiss();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    progressDialog.dismiss();
                }
            });

            /// First Name Load
            FullName.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                     String name1 = dataSnapshot.getValue(String.class);
                     ShowFirstName.setText(name1);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            //Blood Group Load

            bloodGroupRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String bloodgroup = dataSnapshot.getValue(String.class);
                    BloodGroup.setText(bloodgroup);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            ///Dept Name Load


            DeptNameRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String dept_name = dataSnapshot.getValue(String.class);

                    ShowDept.setText(dept_name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            //Phone number Load

            PhoneNumRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String number = dataSnapshot.getValue(String.class);
                    ShowPhoneNum.setText(number);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }

    }







    @Override
    protected void onStart() {
        super.onStart();


        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }





    // Toolbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.menuLogout:

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(ShowProfileActivity.this,MainActivity.class));

                break;
            // using back button along side with menu activity
            case android.R.id.home:
                this.finish();
                return true;
        }


        return  true;
    }



}
