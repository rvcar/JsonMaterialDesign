package com.rvcar.jsonmaterialdesign;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements ItemClickSupport.OnItemClickListener {


    private final String URL = "http://cache-default01h.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";

    ProgressBar pbar;
    ArrayList<Artist> artistsList = new ArrayList<>();
    AdapterRec adapter;
    Artist artist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));
        toolbar.setTitle(R.string.singers);
        setSupportActionBar(toolbar);

        pbar = (ProgressBar) findViewById(R.id.progressBar2);
        final RecyclerView rvArtists = (RecyclerView) findViewById(R.id.recycler1);

        FloatingActionButton myFAB = (FloatingActionButton) findViewById(R.id.fabButton);


        myFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvArtists.scrollToPosition(0);
            }
        });


        ItemClickSupport.addTo(rvArtists).setOnItemClickListener(this);


        adapter = new AdapterRec(artistsList);

         
        rvArtists.setAdapter(adapter);
        rvArtists.setHasFixedSize(true);
        rvArtists.setLayoutManager(new LinearLayoutManager(this));


        BackgroundTask bt = new BackgroundTask();
        bt.execute();

    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

        artist = artistsList.get(position);

        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("bigCover", artist.cover.getBig());
        intent.putExtra("name", artist.name);
        intent.putExtra("genres", artist.genres.toUpperCase());
        intent.putExtra("tracks", artist.tracks);
        intent.putExtra("albums", artist.albums);
        intent.putExtra("description", artist.description);

        startActivity(intent);

    }

    private class BackgroundTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pbar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(URL)
                    .build();

            Response response = null;
            try {
                response = client.newCall(request).execute();

            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            try {
                setJson(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void setJson(String data) throws JSONException {


            JSONObject jsonObject;
            String linkurl, editedgenres;

            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                editedgenres = (jsonObject.getString("genres")).replaceAll("[^a-z]", " ").trim();

                try {
                    linkurl = jsonObject.getString("link");
                } catch (JSONException e) {
                    linkurl = null;
                }

                artist = new Artist(jsonObject.getString("id"), jsonObject.getString("name"),
                        editedgenres, jsonObject.getString("tracks"), jsonObject.getString("albums"),
                        linkurl, jsonObject.getString("description"));
                artist.cover.setSmall(jsonObject.getJSONObject("cover").getString("small"));
                artist.cover.setBig(jsonObject.getJSONObject("cover").getString("big"));

                artistsList.add(artist);

            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });

            pbar.setVisibility(View.GONE);
        }
    }
}
