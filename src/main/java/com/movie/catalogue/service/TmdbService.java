/*
 * Author: Premsingh Padya
 * Student ID: C0924501
 * Project Name: Movie Catalogue System
 *
 * Description: This is the TmdbService class for the Movie Catalogue System. 
 * It interacts with The Movie Database (TMDb) API to fetch data about popular movies, 
 * movie details, and perform movie searches. The service uses `RestTemplate` to make API calls, 
 * processes the response, and returns the relevant movie data.
 * 
 * Methods:
 * - getPopularMovies(int page): Fetches a list of popular movies.
 * - getMovieDetails(Long id): Fetches detailed information about a specific movie.
 * - searchMovies(String query): Searches for movies by a given query.
 */
package com.movie.catalogue.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.movie.catalogue.model.Movie;
import com.movie.catalogue.model.MovieListResponse;

@Service
public class TmdbService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    // Get popular movies from TMDB
    public List<Movie> getPopularMovies(int page) {
        String url = apiUrl + "/movie/popular?api_key=" + apiKey;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response == null || !response.containsKey("results")) {
            return Collections.emptyList();
        }

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
        List<Movie> movies = new ArrayList<>();

        for (Map<String, Object> result : results) {
            Movie movie = new Movie();
            movie.setId(((Number) result.get("id")).longValue());
            movie.setTitle((String) result.get("title"));
            movie.setOverview((String) result.get("overview"));
            movie.setPoster_path((String) result.get("poster_path"));
            movie.setRelease_date((String) result.get("release_date"));
            movie.setVote_average(((Number) result.get("vote_average")).doubleValue());
            movies.add(movie);
        }

        return movies;
    }

    // Get details of a specific movie
    public Movie getMovieDetails(Long id) {
        String url = apiUrl + "/movie/" + id + "?api_key=" + apiKey;
        return restTemplate.getForObject(url, Movie.class);
    }

    // Search movies by query
    public List<Movie> searchMovies(String query) {
        String url = apiUrl + "/search/movie?api_key=" + apiKey + "&query=" + query;
        ResponseEntity<MovieListResponse> response = restTemplate.getForEntity(url, MovieListResponse.class);

        if (response.getBody() != null && response.getBody().getResults() != null) {
            return response.getBody().getResults();
        }

        return Collections.emptyList();
    }
}
