package com.example.shamim.nstubds;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Config;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.security.auth.Subject;

/**
 * Created by Shamim on 18-Mar-18.
 */

public class SendMail extends AsyncTask<Void,Void,Void> {


    private Context context;
    private Session session;
    private String subject;
    private String textMessage;
    private ProgressDialog progressDialog;

    public SendMail(Context context, String subject, String textMessage) {
        this.context = context;
        this.subject = subject;
        this.textMessage = textMessage;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        Toast.makeText(context, "Feedback Sent", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {

        Properties pros = new Properties();
        pros.put("mail.smtp.host","smtp.gmail.com");
        pros.put("mail.smtp.socketFactory.port","465");
        pros.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        pros.put("mail.smtp.auth","true");
        pros.put("mail.smtp.port","465");

        session = Session.getDefaultInstance(pros, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("nstubdsapp@gmail.com","shamimice03");
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("nstubdsapp@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress("shamimice03@gmail.com"));
            message.setSubject(subject);
            message.setContent(textMessage,"text/html; charset=utf-8");

            Transport.send(message);

        }catch (MessagingException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


        return null;
    }
}
