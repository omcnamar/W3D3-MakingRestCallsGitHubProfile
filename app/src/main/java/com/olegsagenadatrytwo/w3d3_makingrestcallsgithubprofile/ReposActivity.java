package com.olegsagenadatrytwo.w3d3_makingrestcallsgithubprofile;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.olegsagenadatrytwo.w3d3_makingrestcallsgithubprofile.model.Repo;

import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReposActivity extends AppCompatActivity {

    private ArrayList<Repo> repos;
    private IntentFilter intentFilter;
    private RecyclerView rvRecyclerVeiwRepos;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.ItemAnimator itemAnimator;
    private ReposAdapter adapter;

    private URLConnection urlConnection;
    private Repo repo[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);

        Intent in = getIntent();
        String url = in.getStringExtra("url");
        final OkHttpClient okHttpClient;
        final Request request;
        okHttpClient = new OkHttpClient();
        request = new Request.Builder()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                repo = gson.fromJson(response.body().string(), Repo[].class);
                repos = new ArrayList<Repo>();
                for(int i = 0; i < repo.length; i++){
                    repos.add(repo[i]);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ReposAdapter(repos);
                        rvRecyclerVeiwRepos.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        rvRecyclerVeiwRepos = (RecyclerView) findViewById(R.id.rvRecyclerViewRepos);
        layoutManager = new LinearLayoutManager(this);
        itemAnimator = new DefaultItemAnimator();
        rvRecyclerVeiwRepos.setLayoutManager(layoutManager);
        rvRecyclerVeiwRepos.setItemAnimator(itemAnimator);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
