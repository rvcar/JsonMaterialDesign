package com.rvcar.jsonmaterialdesign;

public class Artist {

    String id;
    String name;
    String genres;
    String tracks;
    String albums;
    String link;
    String description;
    Cover cover;

    public Artist(String id, String name, String genres, String tracks, String albums, String link,
                  String description) {

        this.id = id;
        this.name = name;
        this.genres = genres;
        this.tracks = tracks;
        this.albums = albums;
        this.link = link;
        this.description = description;

        cover = new Cover();

    }


}
