/*
 * Author: Premsingh Padya
 * Student ID: C0924501
 * Project Name: Movie Catalogue System
 *
 * Description: This is the FavoriteMovie model class for the Movie Catalogue System, 
 * which represents a movie marked as a user's favorite. It includes fields such as 
 * the movie's id, title, overview, release date, vote average, and poster path. 
 * The class is mapped to a database table using Jakarta Persistence annotations, 
 * and is structured to ensure compatibility with an in-memory H2 database for storing favorites.
 * The model uses Lombok annotations to automatically generate getter/setter methods 
 * and constructors.
 */
package com.movie.catalogue.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteMovie {
    @Id
    private Long id;
    
    private String title;
    
    @Column(length = 1000)  // Increased length for the overview
    private String overview;
    
    @Column(name = "release_date")  // Column name for release_date
    private LocalDate releaseDate;  // Use LocalDate for dates to avoid string issues
    
    private Double voteAverage;  // Renamed to follow camelCase convention
    
    @Column(length = 255)  // Increased length for poster_path
    private String posterPath;  // Renamed to follow camelCase convention
}
