package com.when.threemb.cinepolis;

/**
 * Created by USER on 06-11-2017.
 */

public class Album {
    private String movie_name;
    private String img_url;
    private String description,release_date;
    int movie_id;
    public Album(){}

    public Album(String movie_name, String img_url, String description, String release_date, int movie_id) {
        this.movie_name = movie_name;
        this.img_url = img_url;
        this.description = description;
        this.release_date = release_date;
        this.movie_id = movie_id;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
}
