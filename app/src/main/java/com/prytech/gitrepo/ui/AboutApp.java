package com.prytech.gitrepo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.prytech.gitrepo.R;

import java.util.Objects;

public class AboutApp extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp(){
        this.finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        Objects.requireNonNull(getSupportActionBar()).setTitle("About App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
