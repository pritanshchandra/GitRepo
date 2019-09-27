package com.prytech.gitrepo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.prytech.gitrepo.R;

import java.util.Objects;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //noinspection deprecation
        getWindow().setStatusBarColor(getResources().getColor(R.color.darkGrey));
        Objects.requireNonNull(getSupportActionBar()).hide();

        Handler SplashScreenHandler = new Handler();
        SplashScreenHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent launchUserActivity = new Intent(SplashScreen.this, UserActivity.class);
                startActivity(launchUserActivity);
                finish();
            }
        }, 400);

    }
}
