package com.prytech.gitrepo.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.prytech.gitrepo.R;

import java.util.Objects;

public class UserActivity extends AppCompatActivity {

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

    }
}
