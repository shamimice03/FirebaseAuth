package com.example.shamim.nstubds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.rilixtech.Country;
import com.rilixtech.CountryCodePicker;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PostGive extends AppCompatActivity {

    CountryCodePicker Ccp;
    EditText editText,bloodunit,FirstName,LastName;
    Button submit,sendCode;
    ProgressDialog progressDialog;
    Spinner BloodGrp;
    public String PhoneNumber;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String current_userId;


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_give);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        current_userId = firebaseAuth.getCurrentUser().getUid();



        Ccp = (CountryCodePicker) findViewById(R.id.ccp);
        editText = (EditText) findViewById(R.id.phone_number_edtt);
        FirstName = (EditText) findViewById(R.id.PostFirstName);
        LastName = (EditText) findViewById(R.id.PostLastName);
        bloodunit = (EditText) findViewById(R.id.bloodUnit);
        submit = (Button) findViewById(R.id.submitPost);
        sendCode = (Button) findViewById(R.id.sendVerificationCode);
        BloodGrp = (Spinner) findViewById(R.id.bloodSelect);


        Toolbar toolbar = (Toolbar) findViewById(R.id.postGivetoolbar);
        toolbar.setTitle("Post for Blood");
        toolbar.setTitleTextColor(getResources().getColor(R.color.greyLight));
        setSupportActionBar(toolbar);

        submit.setVisibility(View.GONE);


        Ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                String ss =    Ccp.getSelectedCountryCodeWithPlus();
                editText.setText(ss);
            }
        });

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PhoneNumber = editText.getText().toString();
                progressDialog = new ProgressDialog(PostGive.this);
                progressDialog.setTitle("Verification Code");
                progressDialog.setMessage("Sending verification to device.\n Please wait...");
                progressDialog.show();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        PhoneNumber,
                        60,
                        TimeUnit.SECONDS,
                        PostGive.this,
                        mCallbacks
                );
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                submit.setVisibility(View.VISIBLE);
                sendCode.setVisibility(View.GONE);
                editText.setEnabled(false);
                Ccp.setEnabled(false);
                progressDialog.dismiss();

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {



            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("onCodeSent:" , verificationId);
                progressDialog.dismiss();


            }

        };


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FirebaseUser user = firebaseAuth.getCurrentUser();

                String userName = user.getDisplayName();

                String bloodgrp = BloodGrp.getSelectedItem().toString();
                String firstName = FirstName.getText().toString();
                String lastName = LastName.getText().toString();
                String unitOfBlood = bloodunit.getText().toString();

                if(!TextUtils.isEmpty(bloodgrp) && !TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(unitOfBlood) &&!TextUtils.isEmpty(PhoneNumber)
                        ){

                    progressDialog.setTitle("Submitting Data");
                    progressDialog.show();

                    Map<String, Object> PostMap = new HashMap<>();

                    PostMap.put("bloodGroup",bloodgrp);
                    PostMap.put("unit",unitOfBlood);
                    PostMap.put("firstName",firstName);
                    PostMap.put("lastName",lastName);
                    PostMap.put("phoneNumber",PhoneNumber);
                    PostMap.put("userId",current_userId);
                    PostMap.put("userName",userName);
                    PostMap.put("timestamp",FieldValue.serverTimestamp());





                    firebaseFirestore.collection("Posts")
                            .add(PostMap)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {

                                    progressDialog.dismiss();


                                    String myId = documentReference.getId();

                                   // Toast.makeText(PostGive.this, myId , Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(PostGive.this, PostDesAndAddress.class).putExtra("DocumentID",myId));
                                    finish();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(PostGive.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                else{
                    Toast.makeText(PostGive.this, "Something went wrong !!!! ", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                }


            }
        });

    }


}