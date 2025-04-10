/*
 * Author: Premsingh Padya
 * Student ID: C0924501
 * Project Name: Movie Catalogue System
 *
 * Description: This is the Movie model class for the Movie Catalogue System, 
 * representing a movie fetched from The Movie Database (TMDb) API. 
 * It includes fields such as the movie's id, title, overview, release date, 
 * vote average, and poster path. The class provides getters and setters for 
 * each field, allowing easy manipulation of movie data within the system.
 */
package com.movie.catalogue.model;

public class Movie {
    private Long id;
    private String title;
    private String overview;
    private String release_date;
    private Double vote_average;
    private String poster_path;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
}
