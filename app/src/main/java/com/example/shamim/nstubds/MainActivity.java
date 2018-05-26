package com.example.shamim.nstubds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;
    EditText editTextEmail,editTextPassword;
    public String check ="0";
    public  String Email,Password;
    ProgressDialog progressDialog;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseFirestore = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword= (EditText) findViewById(R.id.editTextpassword);

        Email = getIntent().getStringExtra("email");
        Password = getIntent().getStringExtra("password");

        String ch = getIntent().getStringExtra("check");


            editTextEmail.setText(Email);
            editTextPassword.setText(Password);



        findViewById(R.id.textViewSignUp).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.textViewSignUp:
                finish();
                startActivity(new Intent(this, SignUpActivity.class));
                break;

            case R.id.buttonLogin :
                userLogin();
                break;
        }

    }


    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){

            final FirebaseUser user = mAuth.getCurrentUser();

            if(user.isEmailVerified()){
                finish();
                startActivity(new Intent(this,MenuActivity.class));
            }

        }

    }

    private void userLogin() {

        String Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();

        if(Email.isEmpty()){
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(Password.isEmpty()){
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }

        if(Password.length()<6){

            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressDialog = ProgressDialog.show(MainActivity.this,null,"Logging in...",false,false);


         mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {
                 progressDialog.dismiss();

                 if(task.isSuccessful()){
                    // finish();
                     checkEmailVerification();
                   //  Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                     //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   //  startActivity(intent);

                 }
                 else{

                     Toast.makeText(MainActivity.this, task.getException().getMessage() , Toast.LENGTH_LONG).show();
                 }
             }
         });
    }

    private void checkEmailVerification() {

        FirebaseUser user = mAuth.getCurrentUser();

        if(user.isEmailVerified()){
            finish();
            if (user.getDisplayName()==null) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        }
        else{
            Toast.makeText(MainActivity.this, "Please verify your Email", Toast.LENGTH_LONG).show();
        }
    }
}
