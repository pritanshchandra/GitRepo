package com.prytech.gitrepo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.prytech.gitrepo.R;

import java.util.Objects;

public class AboutApp extends AppCompatActivity {

    private MaterialButton rateApp;
    private MaterialButton viewSourceCode;
    private ExtendedFloatingActionButton twitterBtn;

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

        rateApp = findViewById(R.id.rateApp);
        viewSourceCode = findViewById(R.id.githubButton);
        twitterBtn = findViewById(R.id.twitterButton);

        Objects.requireNonNull(getSupportActionBar()).setTitle("About App");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        rateApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewSourceCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGithub();
            }
        });

        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openTwitter();
            }
        });

    }

    private void openTwitter()  {

        String profileUrl = "https://twitter.com/chandrapritansh";
        Intent launchTwitter;
        try {
            getApplicationContext().getPackageManager().getPackageInfo("com.twitter.android", 0);
            launchTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl));
            startActivity(launchTwitter);
        }
        catch (Exception twitterHandler) {
            Toast.makeText(this, "Twitter app not found, Opening in Browser", Toast.LENGTH_SHORT).show();
            launchTwitter = new Intent(Intent.ACTION_VIEW, Uri.parse(profileUrl));
            startActivity(launchTwitter);
        }
    }

    private void openGithub() {

        String repoUrl = "https://github.com/pritanshchandra/GitRepo";
        Intent openGithub = new Intent(Intent.ACTION_VIEW);
        openGithub.setData(Uri.parse(repoUrl));
        startActivity(openGithub);

    }
}
