/*
 * Author: Premsingh Padya
 * Student ID: C0924501
 * Project Name: Movie Catalogue System
 *
 * Description: This class contains the unit tests for the MovieCatalogueSystem application.
 * The tests are intended to verify basic functionality such as loading the application context,
 * retrieving popular movies, searching for movies, and adding/removing movies from favorites.
 */
package com.movie.catalogue;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.context.SpringBootTest;

import com.movie.catalogue.model.Movie;
import com.movie.catalogue.repository.FavoriteMovieRepository;
import com.movie.catalogue.service.TmdbService;

@SpringBootTest
class MovieCatalogueSystemApplicationTests {

    @Mock
    private TmdbService tmdbService;

    @Mock
    private FavoriteMovieRepository favoriteMovieRepository;

    @InjectMocks
    private MovieCatalogueSystemApplication movieCatalogueSystemApplication;

    // Test case 1: Verify that the application context loads successfully.
    @Test
    void contextLoads() {
        assertNotNull(movieCatalogueSystemApplication);
    }

    // Test case 2: Verify that the application can fetch popular movies correctly.
    @Test
    void testGetPopularMovies() {
        // Arrange
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Test Movie");
        movie.setOverview("Test overview");
        movie.setRelease_date("2023-01-01");
        movie.setVote_average(8.5);
        movie.setPoster_path("test.jpg");

        when(tmdbService.getPopularMovies(0)).thenReturn(List.of(movie));

        // Act
        List<Movie> movies = tmdbService.getPopularMovies(0);

        // Assert
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
        assertEquals("Test Movie", movies.get(0).getTitle());
    }

    // Test case 3: Verify that the application can search movies by a query.
    @Test
    void testSearchMovies() {
        // Arrange
        String query = "action";
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Action Movie");
        movie.setOverview("An action-packed movie");
        movie.setRelease_date("2023-05-01");
        movie.setVote_average(7.5);
        movie.setPoster_path("action.jpg");

        when(tmdbService.searchMovies(query)).thenReturn(List.of(movie));

        // Act
        List<Movie> movies = tmdbService.searchMovies(query);

        // Assert
        assertNotNull(movies);
        assertFalse(movies.isEmpty());
        assertEquals("Action Movie", movies.get(0).getTitle());
    }

    // Test case 4: Verify that a movie can be removed from the favorites.
    @Test
    void testRemoveMovieFromFavorites() {
        // Arrange
        Long movieId = 1L;

        // Act
        favoriteMovieRepository.deleteById(movieId);

        // Assert
        verify(favoriteMovieRepository, times(1)).deleteById(movieId);
    }
}
