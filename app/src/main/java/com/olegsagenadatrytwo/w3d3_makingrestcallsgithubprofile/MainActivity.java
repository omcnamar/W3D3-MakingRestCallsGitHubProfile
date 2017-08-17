package com.olegsagenadatrytwo.w3d3_makingrestcallsgithubprofile;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.olegsagenadatrytwo.w3d3_makingrestcallsgithubprofile.model.AccountInfo;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static String URL = "https://api.github.com/users/";
    private static final String TAG = "MainActivity";
    private AccountInfo accountInfo;

    private TextView name;
    private TextView userName;
    private TextView createdAt;
    private TextView type;

    private boolean userSelected = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Profile");
        accountInfo = new AccountInfo();

        name = (TextView) findViewById(R.id.tvName);
        userName = (TextView) findViewById(R.id.tvUserName);
        type = (TextView) findViewById(R.id.tvType);
        createdAt = (TextView) findViewById(R.id.tvCreatedAt);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);

        SearchView searchView = (SearchView)menu.findItem(R.id.menu_item_search).getActionView();
        SearchManager searchManager =(SearchManager) getSystemService(this.SEARCH_SERVICE);

        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
        }
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextChange(String newText) {
                Log.i(TAG,"onQueryTextChange");
                return true;
            }
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: ");
                makeRequest(query);
                return true;
            }
        };
        if (searchView != null) {
            searchView.setOnQueryTextListener(queryTextListener);
        }
        return true;
    }

    public void updateUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                name.setText(accountInfo.getName());
                userName.setText(accountInfo.getLogin());
                createdAt.setText(accountInfo.getCreatedAt());
                type.setText(accountInfo.getType());
            }
        });
    }
    public void makeRequest(String query){
        Log.d(TAG, "makeRequest: ");
        URL = "https://api.github.com/users/" + query;
        final OkHttpClient okHttpClient;
        final Request request;

        okHttpClient = new OkHttpClient();
        request = new Request.Builder()
                .url(URL)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                accountInfo = gson.fromJson(response.body().string(), AccountInfo.class);
                userSelected = true;
                updateUI();
            }
        });
    }

    public void goToRepos(View view) {
        if(userSelected) {
            Intent reposActivity = new Intent(this, ReposActivity.class);
            reposActivity.putExtra("url", URL + "/repos");
            startActivity(reposActivity);
        }else{
            Toast.makeText(this, "No User Selected", Toast.LENGTH_SHORT).show();
        }
    }
}
