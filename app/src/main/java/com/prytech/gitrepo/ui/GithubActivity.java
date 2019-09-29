package com.prytech.gitrepo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prytech.gitrepo.R;
import com.prytech.gitrepo.models.GithubRepository;
import com.prytech.gitrepo.services.GithubService;
import com.prytech.gitrepo.ui.adapters.RecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.internal.EverythingIsNonNull;

public class GithubActivity extends AppCompatActivity {

    private RecyclerView githubRepoRV;
    private ImageView userImg;
    RecyclerViewAdapter recyclerViewAdapter;
    static int pageNum;
    String UserName;
    ArrayList<GithubRepository> githubRepositories = new ArrayList<>();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onSupportNavigateUp(){
        this.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.about_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.AboutApp) {
            Intent aboutApp = new Intent(GithubActivity.this, AboutApp.class);
            startActivity(aboutApp);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        pageNum = 1;
        UserName = getIntent().getStringExtra("Username");
        Objects.requireNonNull(getSupportActionBar()).setTitle("User Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for(int pageIterator = 1; pageIterator <= 5; pageIterator++) {
            fetchRepositories();
        }
    }

    private void fetchRepositories() {
        final String imageUrl = "https://www.github.com/"+UserName+".png";

        userImg = findViewById(R.id.userImage);
        githubRepoRV = findViewById(R.id.recyclerView);
        githubRepoRV.setVisibility(View.GONE);
        githubRepoRV.setLayoutManager(new LinearLayoutManager(this));
        githubRepoRV.setHasFixedSize(true);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = retrofitBuilder.build();
        GithubService githubService = retrofit.create(GithubService.class);
        Call<ArrayList<GithubRepository>> githubRepoCall = githubService.userRepos(UserName, pageNum+"" );

        githubRepoCall.enqueue(new Callback<ArrayList<GithubRepository>>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<ArrayList<GithubRepository>> call, Response<ArrayList<GithubRepository>> response) {

                ArrayList<GithubRepository> repos = response.body();

                assert repos != null;
                githubRepositories.addAll(repos);

                recyclerViewAdapter = new RecyclerViewAdapter(GithubActivity.this, githubRepositories);
                githubRepoRV.setAdapter(recyclerViewAdapter);
                Picasso.get().load(imageUrl).into(userImg, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        githubRepoRV.setVisibility(View.VISIBLE);
                        findViewById(R.id.progressBar).setVisibility(View.GONE);
                    }
                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(GithubActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            @EverythingIsNonNull
            public void onFailure(Call<ArrayList<GithubRepository>> call, Throwable t) {
                Toast.makeText(GithubActivity.this, "Error Occurred While Fetching Data " +t, Toast.LENGTH_SHORT).show();

            }
        });
        pageNum++;
    }

}
