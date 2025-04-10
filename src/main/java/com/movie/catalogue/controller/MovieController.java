/*
 * Author: Premsingh Padya
 * Student ID: C0924501
 * Project Name: Movie Catalogue System
 *
 * Description: This is the MovieController class for the Movie Catalogue System, built using 
 * Java Spring Boot. It manages the views and actions related to the movie list, movie details, 
 * searching, and handling favorite movies. It integrates with The Movie Database (TMDb) API 
 * to fetch real-time movie data and uses H2 as an in-memory database to store user favorites.
 * The controller contains methods to handle:
 * - Displaying a list of popular movies
 * - Showing movie details
 * - Adding and removing movies from favorites
 * - Searching for movies
 * - Displaying the user's favorite movies
 */
package com.movie.catalogue.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.movie.catalogue.model.FavoriteMovie;
import com.movie.catalogue.model.Movie;
import com.movie.catalogue.repository.FavoriteMovieRepository;
import com.movie.catalogue.service.TmdbService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final TmdbService tmdbService;
    private final FavoriteMovieRepository repo;

    private static final Logger logger = LoggerFactory.getLogger(MovieController.class);

    // Home page displaying popular movies
    @GetMapping("/")
    public String home(Model model) {
        try {
            List<Movie> movies = tmdbService.getPopularMovies(0);
            model.addAttribute("movies", movies);
            return "home";
        } catch (Exception e) {
            logger.error("Error fetching popular movies", e);
            model.addAttribute("error", "Error fetching popular movies. Please try again later.");
            return "error";
        }
    }

    // Movie details page
    @GetMapping("/movie/{id}")
    public String movieDetails(@PathVariable Long id, Model model) {
        try {
            Movie movie = tmdbService.getMovieDetails(id);
            if (movie == null) {
                model.addAttribute("error", "Movie details could not be retrieved. Please try again later.");
                return "error";
            }
            boolean isFavorited = repo.existsById(id);
            model.addAttribute("movie", movie);
            model.addAttribute("isFavorited", isFavorited);
            return "details";
        } catch (Exception e) {
            logger.error("Error fetching movie details", e);
            model.addAttribute("error", "Error fetching movie details. Please try again later.");
            return "error";
        }
    }

    // Add movie to favorites
    @PostMapping("/favorite/{id}")
    public String addToFavorites(@PathVariable Long id, Model model) {
        try {
            // Fetch full movie details from TMDb service
            Movie movie = tmdbService.getMovieDetails(id);

            if (movie == null) {
                model.addAttribute("error", "Movie details could not be retrieved. Please try again later.");
                return "error";
            }

            // Parse release_date to LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE; // Adjust format if needed
            LocalDate releaseDate = LocalDate.parse(movie.getRelease_date(), formatter);

            // Check if the movie already exists in favorites
            if (repo.existsById(id)) {
                model.addAttribute("error", "This movie is already in your favorites.");
                return "redirect:/movie/" + id;
            }

            // Save to favorites repository
            FavoriteMovie fav = new FavoriteMovie(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getOverview(),
                    releaseDate,  // Pass the LocalDate
                    movie.getVote_average(),
                    movie.getPoster_path()
            );
            repo.save(fav);

            logger.info("Movie with ID {} added to favorites", id);
            return "redirect:/movie/" + id;
        } catch (Exception e) {
            logger.error("Error adding movie to favorites", e);
            model.addAttribute("error", "There was an error adding the movie to favorites.");
            return "error";
        }
    }

    // Remove movie from favorites
    @PostMapping("/unfavorite/{id}")
    public String removeFromFavorites(@PathVariable Long id, Model model) {
        try {
            // Remove movie from favorites repository
            repo.deleteById(id);

            // Check if there are any favorite movies left
            List<FavoriteMovie> favoriteMovies = repo.findAll();

            if (favoriteMovies.isEmpty()) {
                model.addAttribute("message", "No favorite movies left.");
                return "redirect:/";  // Redirect to home page
            }

            return "redirect:/favorites"; // Show the updated favorites list
        } catch (Exception e) {
            logger.error("Error removing movie from favorites", e);
            model.addAttribute("error", "There was an error removing the movie from favorites.");
            return "error";
        }
    }

    // Search movies
    @GetMapping("/search")
    public String searchMovies(@RequestParam("query") String query, Model model) {
        if (query.isEmpty()) {
            model.addAttribute("error", "Search query cannot be empty.");
            return "home";
        }

        try {
            List<Movie> results = tmdbService.searchMovies(query);
            if (results.isEmpty()) {
                model.addAttribute("message", "No movies found.");
            }
            model.addAttribute("movies", results);
            model.addAttribute("searchQuery", query);
            return "home";
        } catch (Exception e) {
            logger.error("Error searching for movies", e);
            model.addAttribute("error", "There was an error searching for movies.");
            return "error";
        }
    }

    // Display list of favorite movies with pagination
    @GetMapping("/favorites")
    public String favorites(@RequestParam(defaultValue = "0") int page, Model model) {
        try {
            Pageable pageable = PageRequest.of(page, 10); // Show 10 favorites per page
            Page<FavoriteMovie> favoriteMovies = repo.findAll(pageable);
            model.addAttribute("favorites", favoriteMovies.getContent());
            model.addAttribute("totalPages", favoriteMovies.getTotalPages());
            return "favorites";
        } catch (Exception e) {
            logger.error("Error fetching favorite movies", e);
            model.addAttribute("error", "There was an error fetching your favorite movies.");
            return "error";
        }
    }
}
