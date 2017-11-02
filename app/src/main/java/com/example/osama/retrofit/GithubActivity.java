package com.example.osama.retrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.osama.retrofit.Contact.RecycleAdapter;
import com.example.osama.retrofit.Github.GitHubRepo;
import com.example.osama.retrofit.Github.GithubClient;
import com.example.osama.retrofit.Github.GithubRecyleAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubActivity extends AppCompatActivity {

    RecyclerView rv_github;
    Button btn_github_next;
    GithubRecyleAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<GitHubRepo> github_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github);

        btn_github_next = (Button) findViewById(R.id.btn_github_next);
        btn_github_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),UserRegisterActivity.class);
                startActivity(intent);
            }
        });
        rv_github = (RecyclerView) findViewById(R.id.rv_github);
        layoutManager = new LinearLayoutManager(this);
        rv_github.setLayoutManager(layoutManager);
        rv_github.setHasFixedSize(true);

        Log.d("activity","Github");
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        GithubClient githubClient = retrofit.create(GithubClient.class);
        Call<List<GitHubRepo>> call = githubClient.reposForUser("fs-opensource");
        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                Log.d("activity","success");
                Log.d("respo",response.body().toString());
                github_list=  response.body();
                adapter = new GithubRecyleAdapter(github_list);
                rv_github.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                Log.d("activity","Errror Github");
                Toast.makeText(GithubActivity.this, "Error Wrong Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
