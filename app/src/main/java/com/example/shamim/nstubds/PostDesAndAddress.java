package com.example.shamim.nstubds;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class PostDesAndAddress extends AppCompatActivity {

    TextView getplace;
    EditText editDescription;
    Button postButton;
    int PLACE_PICKER_REQUEST=1;
    private FirebaseFirestore firebaseFirestore;
    public String PlaceAddress;
    public String docId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_des_and_address);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        final String docId = intent.getStringExtra("DocumentID");


        Toolbar toolbar = (Toolbar) findViewById(R.id.descriptionToolbar);
        toolbar.setTitle("Post for Blood");
        toolbar.setTitleTextColor(getResources().getColor(R.color.greyLight));
        setSupportActionBar(toolbar);

        getplace = (TextView) findViewById(R.id.address);
        editDescription = (EditText) findViewById(R.id.description);
        postButton = (Button) findViewById(R.id.Post);


        getplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent;
                try {
                    intent = builder.build(PostDesAndAddress.this);
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });



        /* POst for Blood */

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String DesForBlood = editDescription.getText().toString();

                if(!TextUtils.isEmpty(DesForBlood) && !TextUtils.isEmpty(PlaceAddress)){

                    Map<String, Object> PostMap = new HashMap<>();

                    PostMap.put("description",DesForBlood);
                    PostMap.put("location",PlaceAddress);


                    firebaseFirestore.collection("Posts").document(docId).set(PostMap, SetOptions.merge())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(PostDesAndAddress.this, "Posting....", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(PostDesAndAddress.this,TimeLine.class));
                            finish();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PostDesAndAddress.this, "Failed to Post", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else{
                    Toast.makeText(PostDesAndAddress.this, "Something Went Wrong !!! ", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                PlaceAddress = String.format("Place: %s.\nLocation: %s.", place.getName(),place.getAddress());
                getplace.setText(PlaceAddress);
            }
        }
    }
}
