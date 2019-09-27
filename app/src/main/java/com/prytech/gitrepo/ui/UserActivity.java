package com.prytech.gitrepo.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.prytech.gitrepo.R;

import java.util.Objects;

public class UserActivity extends AppCompatActivity {

    private long backPressedDuration;
    private TextInputLayout userName;
    private MaterialButton fetchUserData;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.AboutApp :
                Intent aboutApp = new Intent(this, AboutApp.class);
                startActivity(aboutApp);
                return true;

            case R.id.exitApp :
                new MaterialAlertDialogBuilder(UserActivity.this)
                        .setMessage("Do you want to Exit App ?")
                        .setCancelable(false)
                        .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userName = findViewById(R.id.userName);
        fetchUserData = findViewById(R.id.fetchButton);
        ConstraintLayout constraintLayout = findViewById(R.id.parentLayout);

        fetchUserData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                startGithubActivity();
            }
        });

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                userName.clearFocus();
                userName.setError(null);
            }
        });
    }

    public void startGithubActivity() {
        String githubUserName = Objects.requireNonNull(userName.getEditText()).getText().toString().trim();

        if(githubUserName.isEmpty())
        {
            userName.setError("Username Required");
            Snackbar errorSnackbar = Snackbar.make(findViewById(android.R.id.content),
                    "Please Enter Github Username to Proceed", Snackbar.LENGTH_SHORT);
            errorSnackbar.setAction("Okay", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userName.requestFocus();
                }
            });
            errorSnackbar.show();
        }

        else {
            userName.clearFocus();
            Intent githubActivity = new Intent(UserActivity.this, GithubActivity.class);
            githubActivity.putExtra("Username", githubUserName);
            startActivity(githubActivity);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager inputMethodManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
