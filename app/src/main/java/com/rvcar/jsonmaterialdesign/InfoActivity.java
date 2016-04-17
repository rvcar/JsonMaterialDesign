package com.rvcar.jsonmaterialdesign;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);

        ImageView iv = (ImageView) findViewById(R.id.image_info);

        TextView info_genres = (TextView) findViewById(R.id.info_genres);
        TextView info_tracks = (TextView) findViewById(R.id.info_tracks);
        TextView description = (TextView) findViewById(R.id.description);

        Intent intent = getIntent();

        Picasso.with(getApplicationContext()).load(intent.getStringExtra("bigCover")).into(iv);

        collapsingToolbarLayout.setTitle(intent.getStringExtra("name"));

        info_genres.setText(intent.getStringExtra("genres"));
        info_tracks.setText(String.format("Альбомы: %s, Треки: %s", intent.getStringExtra("albums"), intent.getStringExtra("tracks")));
        description.setText(intent.getStringExtra("description"));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
