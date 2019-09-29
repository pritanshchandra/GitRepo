package com.prytech.gitrepo.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.prytech.gitrepo.R;
import com.prytech.gitrepo.models.GithubRepository;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.UserRepoView> {

    private Context currentContext;
    private ArrayList<GithubRepository> githubRepository;

    public RecyclerViewAdapter(Context applicationContext, ArrayList<GithubRepository> currentRepository) {

        this.currentContext = applicationContext;
        this.githubRepository = currentRepository;
    }



    @NonNull
    @Override
    public UserRepoView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View repoList = LayoutInflater.from(currentContext)
                .inflate(R.layout.user_repos, parent, false);

        return new UserRepoView(repoList);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRepoView holder, int position) {

        GithubRepository currentRepo = githubRepository.get(position);

        holder.userRepo.setText(currentRepo.getName());
    }

    @Override
    public int getItemCount() {
        return githubRepository == null ? 0 : githubRepository.size();
    }

    static class UserRepoView extends RecyclerView.ViewHolder{

        private MaterialTextView userRepo;

        UserRepoView(@NonNull View itemView) {
            super(itemView);

            userRepo = itemView.findViewById(R.id.userRepo);
        }
    }
}
