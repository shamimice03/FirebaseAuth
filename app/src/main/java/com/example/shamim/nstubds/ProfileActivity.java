package com.example.shamim.nstubds;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;


    ImageView imageView;
    EditText displayName,FirstName,LastName,DateOfBirth,PhoneNumber;
    Button button;
    Uri uriProfileImage;
    ProgressBar progressbar,progressbarButton;
    ProgressDialog progressDialog;
    String profileImageUrl=null;
    TextView bloodError,deptError;
    Spinner DeptSpinner,BloodSpinner;

    public String  BirthDate = null;


    DatePickerDialog.OnDateSetListener mDateSetListener;

    FirebaseAuth mAuth;
    DatabaseReference mdatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Profile");
        toolbar.setTitleTextColor(getResources().getColor(R.color.greyLight));
        setSupportActionBar(toolbar);

        displayName = (EditText) findViewById(R.id.editTextDisplayName);
        FirstName = (EditText) findViewById(R.id.firstName);
        LastName = (EditText) findViewById(R.id.lastName);
        DateOfBirth = (EditText) findViewById(R.id.Birthday);
        PhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        imageView = (ImageView) findViewById(R.id.imageViewCamera);
        button = (Button) findViewById(R.id.buttonSave);
        progressbar = (ProgressBar) findViewById(R.id.progressBarProfile);
        bloodError = (TextView) findViewById(R.id.bloodErrorText);
        deptError = (TextView) findViewById(R.id.deptErrorText);
        DeptSpinner = (Spinner) findViewById(R.id.deptSpinner);
        BloodSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);

        DateOfBirth.setFocusable(false);
        DateOfBirth.setClickable(true);



        mAuth = FirebaseAuth.getInstance();
        mdatabase = FirebaseDatabase.getInstance().getReference().child("Users");



        loadUserInfo();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShowImageChooser();

            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SaveUserInfo();

            }
        });

        DateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateOfBirth.setText("Birthday");

                Calendar cal = Calendar.getInstance();

                int Year = cal.get(Calendar.YEAR);
                int Month = cal.get(Calendar.MONTH);
                int Day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dailog = new DatePickerDialog(
                        ProfileActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        Year,Month,Day             );

                dailog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dailog.show();

            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month+1;

                BirthDate = day +" /"+month+ " /"+year;

                DateOfBirth.setText(BirthDate);

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
    }


    private void loadUserInfo() {

        final FirebaseUser user = mAuth.getCurrentUser();
        String  user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference FirstNameRef = mdatabase.child(user_id).child("first_name");
        DatabaseReference ImageRef = mdatabase.child(user_id).child("image");
        DatabaseReference LastNameRef = mdatabase.child(user_id).child("last_name");
        DatabaseReference DeptNameRef= mdatabase.child(user_id).child("department_name");
        DatabaseReference BirthDayRef= mdatabase.child(user_id).child("birthday");
        DatabaseReference BloodGroupRef=mdatabase.child(user_id).child("blood_group");
        final DatabaseReference PhoneNumRef= mdatabase.child(user_id).child("phone_number");

        if(user != null){

            if(user.getDisplayName() != null){

                displayName.setText(user.getDisplayName());
            }

            ImageRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(user.getPhotoUrl() != null){
                    Glide.with(ProfileActivity.this).load(user.getPhotoUrl().toString()).into(imageView);}

                    profileImageUrl = dataSnapshot.getValue(String.class);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

           /// First Name Load
            FirstNameRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String First_Name = dataSnapshot.getValue(String.class);
                    FirstName.setText(First_Name);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


         ///Last Name Load

           LastNameRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String Last_Name = dataSnapshot.getValue(String.class);
                    LastName.setText(Last_Name);
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

                    String[] Dept_Name = getResources().getStringArray(R.array.DepartmentName);
                    DeptSpinner.setSelection(Arrays.asList(Dept_Name).indexOf(dept_name));


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            // Blood Group Load
            BloodGroupRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String blood_group = dataSnapshot.getValue(String.class);

                    String[] Blood_group = getResources().getStringArray(R.array.BloodGroup);
                    BloodSpinner.setSelection(Arrays.asList(Blood_group).indexOf(blood_group));


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            //BirthDay Load
            BirthDayRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String bday = dataSnapshot.getValue(String.class);
                    DateOfBirth.setText(bday);

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
                    PhoneNumber.setText(number);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }


    private void SaveUserInfo() {


        FirebaseUser user = mAuth.getCurrentUser();



        String name = displayName.getText().toString().trim();
        String Fstname = FirstName.getText().toString().trim();
        String Lstname = LastName.getText().toString().trim();
        String number = PhoneNumber.getText().toString().trim();
        String DeptName = DeptSpinner.getSelectedItem().toString();
        String Bday = DateOfBirth.getText().toString().trim();
        String Blood = BloodSpinner.getSelectedItem().toString();
        String fullName= String.format("%s %s",Fstname,Lstname);

        final String check ="Select One";

        deptError.setVisibility(View.GONE);
        bloodError.setVisibility(View.GONE);


        if(name.isEmpty()){
            displayName.setError("Name Required");
            displayName.requestFocus();
            return;
        }
        if(Fstname.isEmpty()){
            FirstName.setError("Fisrt Name Required");
            FirstName.requestFocus();
            return;
        }
        if(Lstname.isEmpty()){
            LastName.setError("Last Name Required");
            LastName.requestFocus();
            return;
        }
        if(number.isEmpty()){
            PhoneNumber.setError("Contact Number Required");
            PhoneNumber.requestFocus();
            return;
        }

        if(Bday.isEmpty()){

            DateOfBirth.setError("Birthday Required");
            DateOfBirth.requestFocus();
            return;
        }
        if(DeptName.equals(check)){
            deptError.setVisibility(View.VISIBLE);
            return;
        }

        if(Blood.equals(check)){
            bloodError.setVisibility(View.VISIBLE);
            return;
        }





        deptError.setVisibility(View.GONE);
        bloodError.setVisibility(View.GONE);

        progressDialog = ProgressDialog.show(ProfileActivity.this,"Saving Data","Please wait...",false,false);


        String  user_id = mAuth.getCurrentUser().getUid();
        DatabaseReference current_user = mdatabase.child(user_id);

        users userModel = new users(Fstname,Blood,profileImageUrl,Lstname,DeptName,Bday,number,fullName,user_id);

        current_user.setValue(userModel);

        /*
        current_user.child("first_name").setValue(Fstname);
        current_user.child("last_name").setValue(Lstname);
        current_user.child("department_name").setValue(DeptName);
        current_user.child("birthday").setValue(Bday);
        current_user.child("image").setValue(profileImageUrl);
        current_user.child("phone_number").setValue(number);
        current_user.child("blood_group").setValue(Blood);
        */

        if(user != null && profileImageUrl != null ) {

            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();
            user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    progressDialog.dismiss();
                    if(task.isSuccessful()){
                        Toast.makeText(ProfileActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ProfileActivity.this,MenuActivity.class));
                        finish();
                    }
                }
            });

        }
        else{
            progressDialog.dismiss();
            Toast.makeText(ProfileActivity.this, "Please Upload a Profile picture", Toast.LENGTH_LONG).show();
        }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CHOOSE_IMAGE && resultCode==RESULT_OK && data != null && data.getData() != null){

            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                imageView.setImageBitmap(bitmap);

                UploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void UploadImageToFirebaseStorage() {

        progressbar.setVisibility(View.VISIBLE);

        String  user_id = mAuth.getCurrentUser().getUid();

        StorageReference profileImageRef =
                FirebaseStorage.getInstance().getReference("ProfilePics/"+ user_id+  ".jpg");  //Image name will be user id :)

        if(uriProfileImage != null){
            profileImageRef.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressbar.setVisibility(View.GONE);

                           profileImageUrl = taskSnapshot.getDownloadUrl().toString();

                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressbar.setVisibility(View.GONE);
                            Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


        }
    }


    private void ShowImageChooser(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }
}
