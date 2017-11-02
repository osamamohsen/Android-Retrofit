package com.example.osama.retrofit.Github;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.osama.retrofit.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by osama on 11/2/2017.
 */

public class GithubRecyleAdapter extends RecyclerView.Adapter<GithubRecyleAdapter.GithubViewHolder> {

    Context view;
    List<GitHubRepo> github_list;

    public GithubRecyleAdapter(List<GitHubRepo> github_list) {
        this.github_list = github_list;
    }

    @Override
    public GithubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_github,parent,false);
        return new GithubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GithubViewHolder holder, int position) {
        holder.txt_github_name.setText(this.github_list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return github_list.size();
    }

    public class GithubViewHolder extends RecyclerView.ViewHolder {
        TextView txt_github_name;
        public GithubViewHolder(View itemView) {
            super(itemView);
            txt_github_name = (TextView) itemView.findViewById(R.id.txt_github_name);
        }
    }
}
