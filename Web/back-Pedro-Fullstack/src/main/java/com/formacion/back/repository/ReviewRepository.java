package com.formacion.back.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.formacion.back.entities.Review;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

	@Query("Select r from Review r where LOWER(r.movieName) LIKE LOWER (?1)")
	Review findByMovieName(String movieName);
	
	@Query("Select r from Review r where LOWER(r.movieName) LIKE LOWER (?1) and LOWER(r.author) LIKE LOWER(?2)")
	Review findReview(String movieName,String author);
}