package com.rvcar.jsonmaterialdesign;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterRec extends RecyclerView.Adapter<AdapterRec.ViewHolder> {

    private List<Artist> thisArtists;
    Context context;
    Artist artist;

    public AdapterRec(List<Artist> artists) {
        thisArtists = artists;
    }


    @Override
    public AdapterRec.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View artistView = inflater.inflate(R.layout.itemsimple, parent, false);
        ViewHolder viewHolder = new ViewHolder(artistView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        artist = thisArtists.get(position);

        ImageView iv = holder.avatar;
        Picasso.with(context).load(artist.cover.getSmall()).into(iv);

        TextView tvArtistName = holder.name;
        tvArtistName.setText(artist.name);

        TextView tvGenre = holder.genres;
        tvGenre.setText(artist.genres.toUpperCase());

        TextView tvSongs = holder.songs;
        tvSongs.setText(String.format("Альбомы: %s, Треки: %s", artist.albums, artist.tracks));

    }

    @Override
    public int getItemCount() {
        return thisArtists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView avatar;
        public TextView name;
        public TextView genres;
        public TextView songs;

        public ViewHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.avatar);
            name = (TextView) itemView.findViewById(R.id.author_name);
            genres = (TextView) itemView.findViewById(R.id.genres);
            songs = (TextView) itemView.findViewById(R.id.songs);
        }

    }
}
