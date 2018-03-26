package com.example.shamim.nstubds;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    ProgressDialog progressDialog;
    TextInputLayout textInputLayout;
    TextInputEditText editTextPassword,editTextRePassword;
    EditText editTextEmail;
    private FirebaseAuth mAuth;
    public String Email,Password,Repassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmailSignUp);
        editTextPassword = (TextInputEditText) findViewById(R.id.editTextpasswordSignUp);
        editTextRePassword = (TextInputEditText) findViewById(R.id.editTextRepasswordSignUp);



        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.buttonSignUp:
                RegisterUser();
                 break;

            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    public void RegisterUser() {

        Email = editTextEmail.getText().toString().trim();
        Password = editTextPassword.getText().toString().trim();
        Repassword = editTextRePassword.getText().toString().trim();



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

        if(Password.equals(Repassword)){

            progressDialog = ProgressDialog.show(SignUpActivity.this,null ,"Registering...",false,false);

            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    progressDialog.dismiss();

                    if(task.isSuccessful()){
                        //Toast.makeText(SignUpActivity.this, "User Registered Successfully ", Toast.LENGTH_SHORT).show();


                        sendEmailVerification();

                        //finish();
                        // Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        // startActivity(intent);
                    }
                    else{

                        Toast.makeText(SignUpActivity.this, task.getException().getMessage() , Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                        intent.putExtra("1","check");
                        intent.putExtra("email",Email);
                        intent.putExtra("password",Password);
                        startActivity(intent);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }

                }
            });

        }else {

            editTextRePassword.setError("Password didn't match");
            editTextRePassword.requestFocus();
            return;
        }





    }

    private void sendEmailVerification() {

        final FirebaseUser user = mAuth.getCurrentUser();

        if(user!=null){

            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(SignUpActivity.this, "Email verification send ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                        intent.putExtra("1","check");
                        intent.putExtra("email",Email);
                        intent.putExtra("password",Password);
                        startActivity(intent);
                        FirebaseAuth.getInstance().signOut();
                        finish();
                    }

                }
            });


        }


    }
}
