package com.prytech.gitrepo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.prytech.gitrepo.R;

import java.util.Objects;

public class UserActivity extends AppCompatActivity {

    private long backPressedDuration;
    private TextInputLayout userName;
    private MaterialButton fetchUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userName = findViewById(R.id.userName);
        fetchUserData = findViewById(R.id.fetchButton);

        fetchUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGithubActivity();
            }
        });
    }

    public void startGithubActivity() {
        String githubUserName = Objects.requireNonNull(userName.getEditText()).getText().toString().trim();
        Intent githubActivity = new Intent(UserActivity.this, GithubActivity.class);
        githubActivity.putExtra("Username", githubUserName);
        startActivity(githubActivity);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }

    @Override
    public void onBackPressed() {

        if (backPressedDuration + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finishAffinity();
        }
        else {
            Toast.makeText(this, "Press Back Again to Exit", Toast.LENGTH_SHORT).show();
        }
        backPressedDuration = System.currentTimeMillis();
    }
}
