package com.example.shamim.nstubds;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Feedback extends AppCompatActivity {

    EditText Feedbackdesc;
    Button SendFeed;
    Session session= null;
    Context context = null;
    String userName;

    private FirebaseAuth mAuth;

   String subject,textmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Toolbar toolbar = (Toolbar) findViewById(R.id.feedbooktoolbar);
        toolbar.setTitle("Feedback");
        toolbar.setTitleTextColor(getResources().getColor(R.color.greyLight));
        setSupportActionBar(toolbar);

        context = this;

        mAuth = FirebaseAuth.getInstance();

        userName = mAuth.getCurrentUser().getEmail();

        Feedbackdesc = (EditText) findViewById(R.id.feedbackdesc);
        SendFeed = (Button) findViewById(R.id.sendFeedbook);

        SendFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String newline="\n";
                subject="FeedBack";
                textmessage = String.format("%s Sender : ~~%s~~ ",Feedbackdesc.getText().toString(),userName);

                SendMail sm = new SendMail(Feedback.this,subject,textmessage);
                sm.execute();

            }
        });
    }




}
