package com.prytech.gitrepo.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prytech.gitrepo.R;
import com.prytech.gitrepo.models.GithubRepository;
import com.prytech.gitrepo.services.GithubService;
import com.prytech.gitrepo.ui.adapters.RecyclerViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubActivity extends AppCompatActivity {

    private RecyclerView githubRepoRV;
    private ImageView userImg;
    RecyclerViewAdapter recyclerViewAdapter;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);
        String UserName = getIntent().getStringExtra("Username");
        final  String imageUrl = "https://www.github.com/"+UserName+".png";

        Objects.requireNonNull(getSupportActionBar()).setTitle("User Data");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        Call<List<GithubRepository>> githubRepoCall = githubService.userRepos(UserName);

        githubRepoCall.enqueue(new Callback<List<GithubRepository>>() {
            @Override
            public void onResponse(Call<List<GithubRepository>> call, Response<List<GithubRepository>> response) {

                List<GithubRepository> repos = response.body();
                recyclerViewAdapter = new RecyclerViewAdapter(GithubActivity.this, repos);
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
            public void onFailure(Call<List<GithubRepository>> call, Throwable t) {
                Toast.makeText(GithubActivity.this, "Error Occured While Fetching Data " +t, Toast.LENGTH_SHORT).show();

            }
        });



    }

}
