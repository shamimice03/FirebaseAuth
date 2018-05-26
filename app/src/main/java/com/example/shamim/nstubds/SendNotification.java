package com.example.shamim.nstubds;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendNotification extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;
    String userId;
    String CurrentId;

    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        userId = getIntent().getStringExtra("userId");

        textView = (TextView) findViewById(R.id.userid);
        editText = (EditText) findViewById(R.id.notification);
        button = (Button) findViewById(R.id.send);

        textView.setText(userId);

        firebaseFirestore = FirebaseFirestore.getInstance();
        CurrentId = FirebaseAuth.getInstance().getUid();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Message = editText.getText().toString();

                if(!TextUtils.isEmpty(Message)){

                    Map<String, Object> Notification = new HashMap<>();

                    Notification.put("message",Message);
                    Notification.put("from",CurrentId);

                    firebaseFirestore.collection("Users/"+userId+"/Notification").add(Notification).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                            Toast.makeText(SendNotification.this, "Notification Sent", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(SendNotification.this, "Error "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }
}
