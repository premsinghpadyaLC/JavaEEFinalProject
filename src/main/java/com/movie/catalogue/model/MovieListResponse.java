/*
 * Author: Premsingh Padya
 * Student ID: C0924501
 * Project Name: Movie Catalogue System
 *
 * Description: This is the MovieListResponse model class for the Movie Catalogue System, 
 * which represents the response structure when fetching a list of movies from The Movie Database (TMDb) API. 
 * It contains a list of `Movie` objects, which are populated with movie data retrieved from the API. 
 * The class provides getter and setter methods for accessing and modifying the list of movies.
 */
package com.movie.catalogue.model;

import java.util.List;

public class MovieListResponse {
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
