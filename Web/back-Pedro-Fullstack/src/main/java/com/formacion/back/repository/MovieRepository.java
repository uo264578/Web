package com.formacion.back.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.formacion.back.entities.Movie1;

public interface MovieRepository extends CrudRepository<Movie1, Integer> {

	@Query("Select m from Movie1 m where LOWER(m.movieName) LIKE LOWER (?1)")
	Movie1 findByMovieName(String movieName);
	
	@Query("Select m from Movie1 m where LOWER(m.movieName) LIKE LOWER (?1)")
	Movie1 movie(String movieName);
	
	@Query("Select m from Movie1 m where LOWER(m.movieName) LIKE LOWER (?1)")
	Movie1 removeMovie(String movieName);
}