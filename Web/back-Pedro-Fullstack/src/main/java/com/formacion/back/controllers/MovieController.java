package com.formacion.back.controllers;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.formacion.back.entities.Movie1;
import com.formacion.back.entities.Review;
import com.formacion.back.repository.MovieRepository;
import com.formacion.back.repository.ReviewRepository;
import com.formacion.back.service.MovieService;


@CrossOrigin(origins = "http://localhost:3000")
@Controller
@RequestMapping("/movie")
public class MovieController {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ReviewRepository reviewRepository;
	
	@Autowired
	private MovieService movieService;

	@PostMapping("/add")
	public @ResponseBody String addMovie(@RequestParam String movieName, @RequestParam String directorName, @RequestParam int premiere_date) {
		
		if(movieName.isEmpty() || directorName.isEmpty())
			return "Some fields are blank, please fill them";
		if(movieRepository.findByMovieName(movieName) != null)
			return "This movie is already added";
		
		Movie1 m = new Movie1();
		
		m.setMovieName(movieName);
		m.setDirectorName(directorName);
		m.setPremiere_date(premiere_date);
		
		movieRepository.save(m);
		
		Date purchaseDate = new Date();
		DateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    outFormat.setTimeZone(TimeZone.getTimeZone("Europe/Lithuania"));
	    
		return "The movie: " + movieName + " was added with id " + m.getId() + "\nDate: " + outFormat.format(purchaseDate);
	}
	
	@PostMapping("/remove")
	public @ResponseBody String removeMovie(@RequestParam int id) {
		return movieService.remove(id);
	}
	
	@PostMapping("/modify")
	public @ResponseBody String modify(@RequestParam int id, @RequestParam String movieName, @RequestParam String directorName,
			@RequestParam int premiere_date) {
		return movieService.modify(id, movieName, directorName, premiere_date);
	}
	
	@GetMapping(path = "/{movieName}")
	public @ResponseBody Movie1 movie(@PathVariable String movieName) {
		if(movieService.movie(movieName) == null)
			return null;
			return movieService.movie(movieName);
	}
	
	@GetMapping(path = "/{author}/{movieName}/addReview")
	public @ResponseBody String addReview(@RequestParam String movieName, @RequestParam String comment,@RequestParam String author, @RequestParam int puntuation) {
		
		if(movieName.isEmpty() || comment.isEmpty() || author.isEmpty())
			return "Some fields are blank, please fill them";
		if(puntuation < 0 || puntuation > 10)
			return "The puntuation must be between 0-10";
		if(movieRepository.findByMovieName(movieName) == null)
			return "This movie is not in the database";
		
		Review r = new Review();
		
		r.setMovieName(movieName);
		r.setComment(comment);
		r.setPuntuation(puntuation);
		r.setAuthor(author);
		
		reviewRepository.save(r);
		
		Date purchaseDate = new Date();
		DateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    outFormat.setTimeZone(TimeZone.getTimeZone("Europe/Lithuania"));
		return "success\nDate: " + outFormat.format(purchaseDate);
	}
	
	@GetMapping(path = "{author}/{movieName}/review")
	public @ResponseBody String Review(@RequestParam String movieName,@RequestParam String author) {
		
		if(movieName.isEmpty() || author.isEmpty())
			return "Some fields are blank, please fill them";
		if(movieRepository.findByMovieName(movieName) == null)
			return "This movie is not in the database";
		if(reviewRepository.findReview(movieName,author) == null)
			return "There are no reviews of that movie from this author";
		Review rev = reviewRepository.findReview(movieName, author);
		
		Date purchaseDate = new Date();
		DateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    outFormat.setTimeZone(TimeZone.getTimeZone("Europe/Lithuania"));
	    
		return "Puntuation : " + rev.getPuntuation() + "\n" + "Comment : " + rev.getComment() + "\nDate: " + outFormat.format(purchaseDate);
	}

}
