package com.android.sergiomarquez.raisingsheep1;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;



public class SplashInicioActivity extends AppCompatActivity{

    private ImageView logo;
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_inicio);

        actionBar = getSupportActionBar();
        actionBar.hide();

        logo = (ImageView) findViewById(R.id.imageViewLogo);

        Animation myanimation = AnimationUtils.loadAnimation(this, R.anim.mytransitions);
        logo.startAnimation(myanimation);



        Thread tiempo = new Thread() {
            public void run() {

                try {

                    sleep(3000);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                } finally {

                    Intent activity_principal = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(activity_principal);
                    finish();
                }
            }
        };

        tiempo.start();
    }

}
