/*
 * Author: Premsingh Padya
 * Student ID: C0924501
 * Project Name: Movie Catalogue System
 *
 * Description: This is the FavoriteMovieRepository interface for the Movie Catalogue System. 
 * It extends the `JpaRepository` interface from Spring Data JPA, providing basic CRUD operations 
 * (Create, Read, Update, Delete) for the `FavoriteMovie` entity. This repository interface allows 
 * interaction with the in-memory database (e.g., H2) to store and retrieve favorite movies marked by the user.
 */
package com.movie.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movie.catalogue.model.FavoriteMovie;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {
}
