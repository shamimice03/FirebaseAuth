package com.example.shamim.nstubds;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private ImageView imageView;
   // private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView = (ImageView) findViewById(R.id.splashLogo);
        //imageView =(ImageView) findViewById(R.id.splashimage);

        Animation myanimantion = AnimationUtils.loadAnimation(this,R.anim.mytransition);
        imageView.startAnimation(myanimantion);
        //imageView.startAnimation(myanimantion);

        final Intent i = new Intent(this,MainActivity.class);

        Thread timer = new Thread(){

            public  void run(){
                try{
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                   startActivity(i);
                    finish();
                }
            }

        };
        timer.start();
    }
}
